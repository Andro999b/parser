package org.hatis.parser.expressions.cycles.iterators;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapIterator implements ICycleIterator<Object> {
    private Iterator<Entry<Object, Object>> itr;
    private Entry<Object, Object> value;
    private int index = -1;
    private int size;

    public MapIterator(Map<Object, Object> map) {
        itr = map.entrySet().iterator();
        this.size = map.size();
    }

    @Override
    public String key() {
        return value.getKey().toString();
    }

    @Override
    public Object item() {
        return value.getValue();
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public IteratorState getState() {
        IteratorState state = new IteratorState();
        state.key = key();
        state.item = item();

        return state;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean hasNext() {
        return itr.hasNext();
    }

    @Override
    public Object next() {
        value = itr.next();
        index++;

        return value.getValue();
    }

    @Override
    public void remove() {
        //nothing
    }
}
