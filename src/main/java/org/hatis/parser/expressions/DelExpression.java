package org.hatis.parser.expressions;

import org.hatis.parser.Context;

public class DelExpression extends SingleArgumentExpression{
    @Override
    public boolean isPrintable() {
        return false;
    }

    @Override
    public Object process(Context context) {
        context.delVar(arguments[1]);
        return null;
    }
}
