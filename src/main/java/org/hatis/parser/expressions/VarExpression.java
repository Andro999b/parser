package org.hatis.parser.expressions;

import org.hatis.parser.Context;
import org.hatis.parser.analyzers.TokensAnalyzer;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class VarExpression extends Expression {

    private String varName;

    public VarExpression() {
        super(2, Integer.MAX_VALUE);
    }

    protected VarExpression(int minArgumentsAddition,int maxArgumentsAddition) {
        super(minArgumentsAddition + 1, maxArgumentsAddition + 1);
    }

    @Override
    public boolean isPrintable() {
        return false;
    }

    @Override
    public Object process(Context context) {
         String argument1 = arguments[1];
        if (!argument1.matches("[a-z]+[a-z0-9_]*"))
            throw getSyntaxError("Неверное имя переменной", context);

        varName = argument1;
        TokensAnalyzer tokenAnalyzer = getParser(context).getTokenAnalyzer();
        if (tokenAnalyzer.isExpression(context.toVarExpressionName(varName)))
            throw getSyntaxError("Имя $" + argument1 + " зарезирвировано", context);

        return setVariable(context);
    }

    protected Object setVariable(Context context) {
        Object variable = getVariableValue(context);
        context.setVarInCurrentScope(varName, variable);
        return variable;
    }

    protected String getVarName(){
        return varName;
    }

    protected Object getVariableValue(Context context) {
        String argument2 = arguments[2];
        TokensAnalyzer tokenAnalyzer = getParser(context).getTokenAnalyzer();

        Object variable;
        if (tokenAnalyzer.isExpression(argument2)) {
            String[] newParts = Arrays.copyOfRange(arguments, 2, arguments.length);

            variable = tokenAnalyzer.analyzeInnerExpression(context, newParts);
            if (variable == null) {
                throw getSyntaxError("Выражение " + StringUtils.join(newParts, ":") + " не возращяет значения", context);
            }
        } else {
            variable = getArgumentValue(context, argument2);
        }
        return variable;
    }

    @Override
    public void printDebug(Context context, StringBuilder sb) {
        printArgumentsDebug(context, sb, 2);
    }
}
