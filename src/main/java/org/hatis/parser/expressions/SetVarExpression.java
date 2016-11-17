package org.hatis.parser.expressions;


import org.hatis.parser.Context;

public class SetVarExpression extends VarExpression {
    @Override
    protected Object setVariable(Context context) {
        Object variable = getVariableValue(context);
        context.setVar(getVarName(), variable);
        return variable;
    }
}
