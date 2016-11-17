package org.hatis.parser.analyzers;

import org.hatis.parser.Context;
import org.hatis.parser.data.DebugInformation;
import org.hatis.parser.data.exceptions.SyntaxException;
import org.hatis.parser.expressions.Expression;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class TokensAnalyzer {

    protected Map<String, ExpressionSupplier> suppliers = new HashMap<String, ExpressionSupplier>();
    private boolean printUnknown;
    private boolean printLastExpression;

    public TokensAnalyzer() {
        this(false);
    }

    /**
     * @param printUnknown Выводить выражения не распознаные анализатором, или игнорировать
     */
    public TokensAnalyzer(boolean printUnknown) {
        this.printUnknown = printUnknown;
    }

    /**
     * Создать выражение или получить из пула
     *
     * @param name - имя команды
     */
    private Expression getExpression(Context context, String name)  {
        try {
            return  suppliers.get(name).get();
        } catch (Exception e) {
            throw new SyntaxException("Corrupted expression class for: " + name + ". " + e.getMessage(), e, context);
        }
    }


    /*
     * Анализ текушего контекста
     *
     * @param context контекст парсера
     * @throws ParserException
     */
    public Object analyze(Context context) {
        String token = context.getTokens().next();

        if(token.startsWith("$")) {
            String[] parts = context.getTokens().getTokenArguments(token);
            return analyzeExpression(context, parts);
        } else {
            printLastExpression = context.isOutputEnable();
            return token;
        }
    }

    /*
     * Добавить выражение в парсер
     *
     * @param name имя пвыражения
     * @param exp класс обработчик выражения
     */
    public void addExpression(String name, ExpressionSupplier exp) {
        suppliers.put("$" + name, exp);
    }

    /*
     * Удалить обработчик выражения
     */
    public void removeExpression(String name) {
        suppliers.remove("$" + name);
    }

    /*
     * Проанализировать части команды в текушем контексте
     *
     * @param context контекст парсера
     * @param parts токен разбитый на выражения
     * @throws ParserException
     */
    public Object analyzeExpression(Context context, String[] parts) {
        String exprName = parts[0];

        boolean outputWasEnabled = context.isOutputEnable();

        if("$debug".equals(exprName)){
            printLastExpression = true;
            return DebugInformation.print(context);
        }

        context.expressionsCounter++;

        if (suppliers.containsKey(exprName)) {
            Expression expression = getExpression(context, exprName);

            expression.setInner(false);
            expression.prepare(parts, context.getTokens().getCursor(), context.expressionsCounter);

            if(context.isOutputEnable()) {
                Object result = expression.process(context);

                //important this flag must be set before return statement
                printLastExpression =
                        result != null &&
                        outputWasEnabled &&
                        expression.isPrintable();
                return result;
            }else{
                expression.processDisabledOutput(context);
            }

            return null;
        } else if(context.hasVarExpression(exprName)){
            printLastExpression = outputWasEnabled;
            return context.getVarExpression(exprName);
        } else {
            printLastExpression = outputWasEnabled;
            if (printUnknown) {
                return "{" + StringUtils.join(parts, ":") + "}";
            } else {
                return "";
            }
        }
    }

    public Object analyzeInnerExpression(Context context, String[] parts) {
        String exprName = parts[0];

        context.expressionsCounter++;

        if (suppliers.containsKey(exprName)) {
            Expression expression = getExpression(context, exprName);

            expression.setInner(true);
            expression.prepare(parts, context.getTokens().getCursor(), context.expressionsCounter);

            if(context.isOutputEnable()) {
                return expression.process(context);
            }else{
                expression.processDisabledOutput(context);
            }

            return null;
        }

        return context.getVarExpression(exprName);
    }

    /*
     * выводить нераспознаные выражения
     */
    public void setPrintUnknown(boolean printUnknown) {
        this.printUnknown = printUnknown;
    }


    public boolean isPrintLastExpression() {
        return printLastExpression;
    }

    /*
         * Является ли команда известным выражением
         */
    public boolean isExpression(String name) {
        return suppliers.containsKey(name);
    }
}
