package org.hatis.parser.expressions.cycles.iterators;

import java.util.Comparator;

public class SortByKeyIterator extends SortIterator {
    public SortByKeyIterator(ICycleIterator<?> sourceItr, boolean reverse) {
        super(sourceItr, reverse);
    }

    @Override
    protected Comparator<IteratorState> getComparator() {
        return (o1, o2) -> o1.key.compareTo(o2.key);
    }
}
