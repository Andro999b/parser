package org.hatis.parser.expressions.cycles.iterators;

import java.util.Collection;
import java.util.Iterator;
/**
 * Итератор по значениям массива
 * @author andrey
 *
 */
public class ArrayIterator implements ICycleIterator<Object> {
	private Iterator<Object> itr;
	private int size;
	private Object value;
	private int index = -1;

    public ArrayIterator(Collection<Object> collection) {
		this.itr = collection.iterator();
		this.size = collection.size();
	}

	
	@Override
	public boolean hasNext() {
		return itr.hasNext();
	}

	@Override
	public Object next() {
		value = null;
		
		if(itr.hasNext()){
			value = itr.next();
		}else{
			value = "";
		}
		
		index++;
		
		return value;
	}

	@Override
	public void remove() {

	}

	@Override
	public String key() {
		return "";
	}

	@Override
	public String item() {
		return String.valueOf(value);
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
}
