package org.hatis.parser.expressions.cycles.iterators;

import java.util.Iterator;
import java.util.List;

/**
 * Created by andro on 04.12.15.
 */
public class StatesIterator implements ICycleIterator<Object> {

    private int index = -1;
    private IteratorState current;
    private int total;
    private Iterator<IteratorState> itr;

    StatesIterator() {}

    public StatesIterator(List<IteratorState> states) {
        setStates(states);
    }

    protected void setStates(List<IteratorState> states) {
        total = states.size();
        itr = states.iterator();
    }

    @Override
    public boolean hasNext() {
        return itr.hasNext();
    }

    @Override
    public Object next() {
        current = itr.next();
        index++;
        return current.item;
    }

    @Override
    public void remove() {
        //nothing
    }

    @Override
    public String key() {
        return current.key;
    }

    @Override
    public Object item() {
        return current.item;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public IteratorState getState() {
        return current;
    }

    @Override
    public int size() {
        return total;
    }
}