package org.hatis.parser.expressions.cycles.iterators;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortIterator extends StatesIterator{
    private ICycleIterator<?> sourceItr;

    public SortIterator(ICycleIterator<?> sourceItr, boolean reverse) {
        this.sourceItr = sourceItr;
        List<IteratorState> states = new ArrayList<IteratorState>();

        while (sourceItr.hasNext()) {
            sourceItr.next();
            states.add(sourceItr.getState());
        }


        Comparator<IteratorState> comparator = getComparator();

        if(reverse)
            comparator = Collections.reverseOrder(comparator);

        Collections.sort(states, comparator);

        setStates(states);
    }

    protected Comparator<IteratorState> getComparator() {
        return (o1, o2) -> ObjectUtils.compare((Comparable) o1.item, (Comparable) o2.item);
    }

    public ICycleIterator<?> getSourceItr() {
        return sourceItr;
    }
}
