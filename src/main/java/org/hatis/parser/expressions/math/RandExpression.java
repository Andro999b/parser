package org.hatis.parser.expressions.math;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.NoArgumentsExpression;

/**
 * Created by Andrey on 18.06.2015.
 */
public class RandExpression extends NoArgumentsExpression {
    @Override
    public Object process(Context context) {
        return Math.random();
    }
}
