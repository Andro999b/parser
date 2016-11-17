package org.hatis.parser.expressions.cycles.chain;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.cycles.CycleExpression;
import org.hatis.parser.expressions.cycles.IteratorExpression;
import org.hatis.parser.expressions.cycles.iterators.NumIterator;

public class RangeExpression extends CycleExpression {

    public RangeExpression() {
        super(3);
    }

    @Override
    public Object process(Context context) {
        int from = 0;
        int count = 0;
        int step = 1;

        if (arguments.length > 2) {
            from = getIntArgumentValue(context, arguments[1]);
            count = getIntArgumentValue(context, arguments[2]);

            if (arguments.length > 3) {
                step = getIntArgumentValue(context, arguments[3]);
            }
        } else if (arguments.length > 1) {
            count = getIntArgumentValue(context, arguments[1]);
        }

        itr = new NumIterator(from, count, step);

        if(!isInner()) {
            context.beginBlock(this);

            if (itr.hasNext()) {
                itr.next();
                IteratorExpression.setIteratorVars(context, itr);
            } else {
                context.disableOutput(this);
            }
        }

        return itr;
    }
}
