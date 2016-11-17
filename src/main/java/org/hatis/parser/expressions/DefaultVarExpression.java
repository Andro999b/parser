package org.hatis.parser.expressions;


import org.hatis.parser.Context;

public class DefaultVarExpression extends VarExpression {
    @Override
    protected Object setVariable(Context context) {
        String varName = getVarName();
        Object variable;

        if(context.hasVar(varName)) {
            variable = context.getVar(varName);
        } else {
            variable = getVariableValue(context);
            context.setVar(varName, variable);
        }

        return variable;
    }
}
