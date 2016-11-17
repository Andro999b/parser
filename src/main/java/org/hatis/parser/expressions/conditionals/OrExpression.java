package org.hatis.parser.expressions.conditionals;

import org.hatis.parser.Context;

/**
 * Created by Andrey on 16.06.2015.
 */
public class OrExpression extends ConditionExpression {
    public OrExpression() {
        super(2, Integer.MAX_VALUE);
    }

    @Override
    public boolean compare(Context context) {
        boolean res = false;
        for(int i = 1; i < arguments.length; i++){
            res = getBooleanArgumentValue(context, arguments[i]);
            if(res) break;
        }

        return res;
    }
}
