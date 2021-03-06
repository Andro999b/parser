package org.hatis.parser.expressions.type;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.SingleArgumentExpression;

/**
 * Created by Andrey on 12.06.2015.
 */
public class ToStringExpression extends SingleArgumentExpression {
    @Override
    public Object process(Context context) {
        return getStringArgumentValue(context, arguments[1]);
    }
}
