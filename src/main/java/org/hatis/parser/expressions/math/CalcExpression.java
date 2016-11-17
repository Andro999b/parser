package org.hatis.parser.expressions.math;

import org.hatis.parser.Context;

/**
 * Created by Andrey on 17.06.2015.
 */
public class CalcExpression extends CalculatedExpression {
    public CalcExpression() {
        super(1, 1);
    }

    @Override
    public Object process(Context context) {
        return getCalculatedValue(context, arguments[1]);
    }
}
