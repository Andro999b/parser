package org.hatis.parser.expressions;

import org.hatis.parser.Context;
import org.hatis.parser.CoreParser;
import org.hatis.parser.arguments.*;
import org.hatis.parser.data.CoreParserArguments;
import org.hatis.parser.data.CoreParserConfig;
import org.hatis.parser.data.exceptions.ParserException;
import org.hatis.parser.data.exceptions.SyntaxException;

/**
 * Абстрактный класс выражения шаблона
 *
 * @author andrey
 *
 */
public abstract class Expression {
    private final int minArguments;
    private final int maxArguments;
    protected int id = 0;//Порядковый номер выражения
    protected String[] arguments;
    protected String name;
    protected int position;
    protected boolean inner;

    public Expression() {
        this(0, Integer.MAX_VALUE);
    }

    public Expression(int minArguments, int maxArguments) {
        this.minArguments = minArguments;
        this.maxArguments = maxArguments;
    }

    /**
     * @param arguments - части выражения
     * @param position - номер токена
     * @param id - номер инстанса
     */
    public void prepare(String[] arguments, int position, int id) {
        this.arguments = arguments;
        this.position = position;
        this.id = id;
        this.name = arguments[0];

        int length = arguments.length - 1;
        if(length < minArguments)
            throw getSyntaxError("Минимальное число аргументов: " + minArguments, null);

        if(length > maxArguments)
            throw getSyntaxError("Максимальное число аргументов: " + minArguments, null);
    }

    public boolean isInner() {
        return inner;
    }

    public void setInner(boolean inner) {
        this.inner = inner;
    }

    public String getCommandName() {
        return name;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] parts) {
        this.arguments = parts;
    }

    /*
     * Явлестя выражение условием
     */
    public boolean isCondition() {
        return false;
    }

    /*
     * Является выражение циклом
     */
    public boolean isCycle() {
        return false;
    }

    /*
     * Номер токена
     *
     * @return порядковый номер команды
     */
    public int getPosition() {
        return position;
    }

    /*
     * Выводить результат команды или нет
     */
    public boolean isPrintable() {
        return true;
    }

    /*
     * Блочное выражение или нет
     */
    public boolean isBlock() {
        return false;
    }

    /**
     * Обработать выражение
     *
     * @param context контекст парсера
     * @return результат команды
     * @throws ParserException
     */
    public Object process(Context context) {
        throw getSyntaxError("Необрабатываемое выражение", context);
    }

    /**
     * call when output is disabled
     */
    public void processDisabledOutput(Context context) {
        if(isBlock())
            context.beginBlock(this);
    }

    @Override
    public boolean equals(Object obj) {
        return ((Expression) obj).id == id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
        return hash;
    }

    protected SyntaxException getSyntaxError(String msg, Context context) {
        return new SyntaxException(msg, this, context);
    }

    protected SyntaxException getSyntaxError(String msg, Throwable t, Context context) {
        return new SyntaxException(msg, t, this, context);
    }

    protected Integer getIntArgumentValue(Context context, String argument) {
        return getArgumentValue(context, argument, NumberGetter.INSTANCE).intValue();
    }

    protected Double getDoubleArgumentValue(Context context, String argument) {
        return getArgumentValue(context, argument, NumberGetter.INSTANCE).doubleValue();
    }

    protected Number getNumberArgumentValue(Context context, String argument) {
        return getArgumentValue(context, argument, NumberGetter.INSTANCE);
    }

    protected Boolean getBooleanArgumentValue(Context context, String argument) {
        return getArgumentValue(context, argument, BooleanGetter.INSTANCE);
    }

    protected String getStringArgumentValue(Context context, String argument) {
        return getArgumentValue(context, argument, StringGetter.INSTANCE);
    }

    protected Object getArgumentValue(Context context, String argument) {
        return getArgumentValue(context, argument, ObjectGetter.INSTANCE);
    }

    protected Integer getIntArgumentValue(Context context, int argumentPos) {
        return getArgumentValue(context, arguments[argumentPos], NumberGetter.INSTANCE).intValue();
    }

    protected Double getDoubleArgumentValue(Context context, int argumentPos) {
        return getArgumentValue(context, arguments[argumentPos], NumberGetter.INSTANCE).doubleValue();
    }

    protected Number getNumberArgumentValue(Context context, int argumentPos) {
        return getArgumentValue(context, arguments[argumentPos], NumberGetter.INSTANCE);
    }

    protected Boolean getBooleanArgumentValue(Context context, int argumentPos) {
        return getArgumentValue(context, arguments[argumentPos], BooleanGetter.INSTANCE);
    }

    protected String getStringArgumentValue(Context context, int argumentPos) {
        return getArgumentValue(context, arguments[argumentPos], StringGetter.INSTANCE);
    }

    protected Object getArgumentValue(Context context, int argumentPos) {
        return getArgumentValue(context, arguments[argumentPos], ObjectGetter.INSTANCE);
    }

    protected <T> T getArgumentValue(Context context, String argument, ArgumentGetter<T> getter) {
        return getter.get(context, argument);
    }

    public void printDebug(Context context, StringBuilder sb) {
        printArgumentsDebug(context, sb, 1);
    }

    protected void printArgumentsDebug(Context context, StringBuilder sb, int from) {
        for (int i = from; i < arguments.length; i++) {
            sb.append("   ").append(arguments[i]);

            try {
                Object val = getArgumentValue(context, arguments[i]);
                if (val != null) {
                    sb.append(" (").append(val.getClass().getCanonicalName()).append(") ");
                    sb.append(val).append("\n");
                } else {
                    sb.append(" null\n");
                }
            } catch (ParserException e) {
                sb.append(" Невозможно вычислить \n");
            } catch (Exception e) {
                sb.append(" Невозможно вычислить ").append(e.getMessage()).append("\n");
            }
        }
    }

    public CoreParserArguments getParserArguments(Context context){
        return context.getParserArguments();
    }

    public CoreParser getParser(Context context){
        return context.getParser();
    }

    public CoreParserConfig getParserConfig(Context context){
        return getParser(context).getConfig();
    }
}
