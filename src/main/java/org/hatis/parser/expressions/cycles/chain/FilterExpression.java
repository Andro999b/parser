package org.hatis.parser.expressions.cycles.chain;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.cycles.IteratorExpression;
import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;
import org.hatis.parser.expressions.cycles.iterators.IteratorState;
import org.hatis.parser.expressions.cycles.iterators.StatesIterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andro on 04.12.15.
 */
public class FilterExpression extends IteratorExpression {
    public FilterExpression() {
        super(2);
    }

    @Override
    public Object process(Context context) {
        ICycleIterator<?> iterator = getIteratorFromContext(context);

        context.openVarScope();

        List<IteratorState> states = new ArrayList<>(iterator.size());
        while (iterator.hasNext()){
            iterator.next();
            setIteratorVars(context, iterator);

            boolean pass = getBooleanArgumentValue(context, 2);

            if(pass)
                states.add(iterator.getState());
        }

        context.closeVarScope();

        return new StatesIterator(states);
    }
}
