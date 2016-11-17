package org.hatis.parser.expressions.cycles.chain;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;
import org.hatis.parser.expressions.cycles.iterators.SortByKeyIterator;

public class SortByKeyExpression extends SortExpression {
    @Override
    public ICycleIterator<?> getSortIterator(Context context) {
        boolean reverse = false;

        if(arguments.length > 2)
            reverse = getBooleanArgumentValue(context, 2);

        return new SortByKeyIterator(getIteratorFromContext(context), reverse);
    }
}
