package org.hatis.parser.expressions.type;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.SingleArgumentExpression;

public class TypeExpression extends SingleArgumentExpression {
    @Override
    public Object process(Context context) {
        Object val = getArgumentValue(context, arguments[1]);
        if (val != null)
            return val.getClass().getCanonicalName();
        return "null";
    }
}
