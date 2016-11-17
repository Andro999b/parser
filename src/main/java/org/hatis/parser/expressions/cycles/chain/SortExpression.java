package org.hatis.parser.expressions.cycles.chain;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.cycles.CycleExpression;
import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;
import org.hatis.parser.expressions.cycles.iterators.SortIterator;

public class SortExpression extends CycleExpression {
    public SortExpression() {
        super(2);
    }

    protected ICycleIterator<?> getSortIterator(Context context) {
        boolean reverse = false;

        if(arguments.length > 2)
            reverse = getBooleanArgumentValue(context, 2);

        return new SortIterator(getIteratorFromContext(context), reverse);
    }

    @Override
    public Object process(Context context) {
        itr = getSortIterator(context);

        if(!isInner()) {
            context.beginBlock(this);

            if (itr.hasNext()) {
                itr.next();
                setIteratorVars(context, itr);
            } else {
                context.disableOutput(this);
            }
        }

        return itr;
    }
}
