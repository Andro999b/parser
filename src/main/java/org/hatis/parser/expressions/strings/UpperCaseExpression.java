package org.hatis.parser.expressions.strings;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.SingleArgumentExpression;

/**
 * Created by andro on 02.09.15.
 */
public class UpperCaseExpression extends SingleArgumentExpression {
    @Override
    public Object process(Context context) {
        return getStringArgumentValue(context, arguments[1]).toUpperCase();
    }
}
