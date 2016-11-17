package org.hatis.parser.expressions.math;

import org.hatis.parser.Context;

/**
 * Created by Andrey on 18.06.2015.
 */
public class MathExpression extends CalculatedExpression{
    public MathExpression() {
        super(2, 2);
    }

    @Override
    public Object process(Context context) {
        String argument1 = arguments[1];
        String argument2 = arguments[2];

        switch (argument1){
            case "floor": return Math.floor(getCalculatedValue(context, argument2));
            case "ceil": return Math.ceil(getCalculatedValue(context, argument2));
            case "round": return Math.round(getCalculatedValue(context, argument2));
            case "abs": return Math.abs(getCalculatedValue(context, argument2));
            case "sqrt": return Math.sqrt(getCalculatedValue(context, argument2));
            case "exp": return Math.exp(getCalculatedValue(context, argument2));
            case "log": return Math.log(getCalculatedValue(context, argument2));
            case "log10": return Math.log10(getCalculatedValue(context, argument2));
            case "cbrt": return Math.cbrt(getCalculatedValue(context, argument2));
        }

        return 0;
    }
}
