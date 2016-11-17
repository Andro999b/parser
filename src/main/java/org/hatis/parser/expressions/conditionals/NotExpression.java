package org.hatis.parser.expressions.conditionals;

import org.hatis.parser.Context;

/**
 * Created by Andrey on 16.06.2015.
 */
public class NotExpression extends ConditionExpression {
    public NotExpression() {
        super(1, 1);
    }

    @Override
    protected boolean compare(Context context) {
        return !getBooleanArgumentValue(context, arguments[1]);
    }
}
