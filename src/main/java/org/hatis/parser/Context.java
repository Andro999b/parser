package org.hatis.parser;

import org.hatis.parser.data.ContextVariable;
import org.hatis.parser.data.CoreParserArguments;
import org.hatis.parser.data.CoreParserConfig;
import org.hatis.parser.data.Debuggable;
import org.hatis.parser.data.exceptions.SyntaxException;
import org.hatis.parser.expressions.Expression;
import org.hatis.parser.expressions.cycles.CycleExpression;
import org.hatis.parser.expressions.cycles.iterators.DummyIterator;
import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.Map.Entry;

/**
 * Контекст текущего состояния парсера
 *
 * @author andrey
 */
public class Context implements Debuggable {

    private Context parent;
    private Context root;

    private CoreParser parser;
    private CoreParserArguments parserArguments;

    public int expressionsCounter = 0;//Счетчик выражений
    private Tokens tokens;//Токены шаблона

    private Expression outputDisableExpression;//Выражение заблокировавшее вывод
    private Deque<Map<String, Object>> variablesScopes = new ArrayDeque<>();
    private Deque<Expression> blocks = new ArrayDeque<>();//Блочные выражения
    private Deque<ICycleIterator<?>> iterators = new ArrayDeque<>();//Стек итераторов циклов
    private Map<String, Object> utils = new HashMap<>();

    public Context(CoreParserArguments arguments, Context parentContext, Tokens tokens) {
        this.parser = parentContext.parser;
        this.parserArguments = arguments;
        this.tokens = tokens;
        this.parent = parentContext;
        this.utils = parentContext.utils;

        if(parentContext.root == null){
            this.root = parentContext;
        }else{
            this.root = parentContext.root;
        }

        openVarScope();
    }

    public Context(Context parentContext, Tokens tokens) {
        this(new CoreParserArguments(parentContext.parserArguments), parentContext, tokens);
    }

    public Context(CoreParserArguments arguments, CoreParser parser, Tokens tokens){
        this.tokens = tokens;
        this.parser = parser;
        this.parserArguments = arguments;
        openVarScope();
        arguments.getVariables().forEach(this::setVarInCurrentScope);
    }

    /*
     * Получить перменную
     *
     * @param name имя переменной
     * @return значение
     */
    public Object getVar(String name) {
        return getVarExpression(toVarExpressionName(name));
    }

    public Object getVarExpression(String name) {
        Object var = null;

        for (Map<String, Object> variablesScope : variablesScopes) {
            if(variablesScope.containsKey(name)) {
                var = variablesScope.get(name);
                break;
            }
        }

        if (var instanceof ContextVariable) {
            return ((ContextVariable) var).getValue(this);
        }

        return var;
    }

    /**
     * Установить переменную
     *
     * @param name имя переменной
     * @param value значение
     */
    public void setVarInCurrentScope(String name, Object value) {
        name = toVarExpressionName(name);

        variablesScopes.peek().put(name, value);
    }

    public void setVar(String name, Object value) {
        name = toVarExpressionName(name);

        for (Map<String, Object> variablesScope : variablesScopes) {
            if(variablesScope.containsKey(name)){
                variablesScope.put(name, value);
                return;
            }
        }
        variablesScopes.getLast().put(name, value);
    }

    public boolean hasVar(String name) {
        return hasVarExpression(toVarExpressionName(name));
    }

    public boolean hasVarExpression(String name){
        for (Map<String, Object> variablesScope : variablesScopes) {
            if(variablesScope.containsKey(name))
                return true;
        }

        return false;
    }
    /**
     * Удалить перменную
     *
     * @param name имя переменной
     */
    public void delVar(String name) {
        name = toVarExpressionName(name);

        for (Map<String, Object> variablesScope : variablesScopes) {
            if(variablesScope.containsKey(name)) {
                variablesScope.remove(name);
                return;
            }
        }
    }

    public String toVarExpressionName(String name) {
        return "$" + name;
    }

    /**
     * Текущий итератор цикла
     *
     * @return Итератор цикла
     */
    public ICycleIterator<?> cycleIterator() {
        if (iterators.isEmpty()) {
            return null;
        }

        return iterators.peek();
    }

    /**
     * Открыть новый блок
     *
     * @param expr Выражение открывающее новый блок
     */
    public void beginBlock(Expression expr) {
        if(expr.isInner()) return;

        openVarScope();
        blocks.push(expr);

        if (expr.isCycle()) {
            ICycleIterator<?> itr = ((CycleExpression) expr).cycleIterator();

            if (itr != null) {
                iterators.push(itr);
            } else {
                iterators.push(new DummyIterator());
            }
        }
    }

    /*
     * Найти последний блок цикла
     */
    public CycleExpression findLastCycleBlock() {
        Iterator<Expression> itr = blocks.descendingIterator();
        Expression exp;

        while (itr.hasNext()) {
            exp = itr.next();

            if (exp.isCycle()) {
                return (CycleExpression) exp;
            }
        }

        return null;
    }

    /**
     * Переход к началу заданаго блока
     *
     * @param block Блочная команда
     */
    public void jumpToBlock(Expression block) {
        Expression exp;

        while (blocks.size() > 0) {
            exp = blocks.peek();

            if (exp.equals(block)) {
                tokens.setCursor(exp.getPosition());
                return;
            } else {
                closeVarScope();
                blocks.pop();
            }
        }

        throw new SyntaxException("Переход на неизвестный блок", this);
    }

    public void openVarScope() {
        variablesScopes.push(new HashMap<>());
    }

    public void closeVarScope() {
        if(variablesScopes.size() > 1) //Некоректоное состояние
            variablesScopes.pop();
    }

    /**
     * Последний блок
     *
     * @return Выражение последним открывшее блок
     */
    public Expression lastBlock() {
        if (blocks.isEmpty()) {
            return null;
        }

        return blocks.peek();
    }

    /**
     * Закрыть текущий блок
     */
    public void closeBlock() {
        closeVarScope();

        Expression expr = blocks.pop();

        tryEnableOutput(expr);

        if (expr.isCycle()) {
            iterators.pop();
        }
    }

    /**
     * Разрешен вывод результатов выражений или нет
     *
     * @return флаг
     */
    public boolean isOutputEnable() {
        return outputDisableExpression == null;
    }

    /**
     * Попытка разблокировать водт текущим выражением
     *
     * @param expr Команда заблокироваршая вывод
     */
    public boolean tryEnableOutput(Expression expr) {
        if (outputDisableExpression != null) {
            if (expr.equals(outputDisableExpression)) {
                outputDisableExpression = null;
                return true;
            }
        }

        return false;
    }

    /*
     * Разрешен вывод парсера или нет
     *
     * @param expr Проверить блокирует ли выражение ввод
     */
    public boolean isDisableOutput(Expression expr) {
        return outputDisableExpression != null && expr.equals(outputDisableExpression);
    }

    /**
     * Заблокировать вывод парсера текущим выражением
     *
     * @param expr Выражение блокирующее вывод
     */
    public void disableOutput(Expression expr) {
        if (outputDisableExpression == null && !expr.isInner()) {
            outputDisableExpression = expr;
        }
    }

    public void printDebug(StringBuilder sb) {
        if (!blocks.isEmpty()) {
            sb.append("Стек блочных выражений:\n");
            for (Expression block : blocks) {
                sb.append("    {")
                        .append(StringUtils.join(block.getArguments(), ":"))
                        .append("}\n");
            }
        }

        sb.append("Переменные:\n");
        for (Map<String, Object> variablesScope : variablesScopes) {
            sb.append(">>scope start\n");
            for (Entry<String, Object> e : variablesScope.entrySet()) {
                Object var = e.getValue();

                sb.append("    ").append(e.getKey());


                if (var != null) {
                    String className = var.getClass().getCanonicalName();

                    sb.append(" (")
                            .append(className)
                            .append("): ");

                    if (var instanceof Debuggable) {
                        ((Debuggable)var).printDebug(sb);
                    }else if (var instanceof ContextVariable) {
                        try {
                            var = ((ContextVariable) var).getValue(this);
                        } catch (Exception ex) {
                            var = "Невозможно вычислить (" + ex + ")";
                        }

                        sb.append(var);
                    }else{
                        sb.append(var.toString());
                    }

                    sb.append("\n");
                } else {
                    sb.append(": null\n");
                }
            }
            sb.append(">>scope end\n");
        }

        if (parent != null) {
            sb.append("Родительский контекст\n");
            parent.printDebug(sb);
        }
    }

    public Context getParent() {
        return parent;
    }

    public Context getRoot() {
        return root;
    }

    public CoreParser getParser() {
        return parser;
    }

    public CoreParserConfig getParserConfig(){
        return parser.getConfig();
    }

    public CoreParserArguments getParserArguments() {
        return parserArguments;
    }

    public Tokens getTokens() {
        return tokens;
    }

    public void addUtil(String name, Object util) {
        utils.put(name, util);
    }

    @SuppressWarnings("unchecked")
    public <T> T getUtil(String name) {
        return (T) utils.get(name);
    }
}
