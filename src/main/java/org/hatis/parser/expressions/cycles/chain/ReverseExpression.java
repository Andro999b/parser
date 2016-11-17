package org.hatis.parser.expressions.cycles.chain;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.cycles.IteratorExpression;
import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;
import org.hatis.parser.expressions.cycles.iterators.IteratorState;
import org.hatis.parser.expressions.cycles.iterators.StatesIterator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by andro on 04.12.15.
 */
public class ReverseExpression extends IteratorExpression {
    @Override
    public Object process(Context context) {
        ICycleIterator<?> iterator = getIteratorFromContext(context);

        List<IteratorState> states = new ArrayList<>(iterator.size());
        while (iterator.hasNext()){
            iterator.next();
            states.add(iterator.getState());
        }

        Collections.reverse(states);

        return new StatesIterator(states);
    }
}
