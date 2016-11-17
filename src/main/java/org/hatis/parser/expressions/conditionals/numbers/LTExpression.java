package org.hatis.parser.expressions.conditionals.numbers;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.conditionals.ConditionExpression;

/**
 * Created by Andrey on 16.06.2015.
 */
public class LTExpression extends ConditionExpression {
    public LTExpression() {
        super(2, 2);
    }

    @Override
    public boolean compare(Context context) {
        Double first = getDoubleArgumentValue(context, arguments[1]);
        Double second = getDoubleArgumentValue(context, arguments[2]);

        return first < second;
    }
}
