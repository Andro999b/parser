package org.hatis.parser.expressions;

import org.hatis.parser.Context;

public class OutExpression extends SingleArgumentExpression {
    @Override
    public Object process(Context context) {
        return getArgumentValue(context, arguments[1]);
    }
}
