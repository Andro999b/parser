package org.hatis.parser.expressions.cycles.iterators;


public class DummyIterator implements ICycleIterator<Object>{
    @Override
    public String key() {
        return null;
    }

    @Override
    public String item() {
        return null;
    }

    @Override
    public int index() {
        return 0;
    }

    @Override
    public IteratorState getState() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Object next() {
        return null;
    }

    @Override
    public void remove() {

    }
}
