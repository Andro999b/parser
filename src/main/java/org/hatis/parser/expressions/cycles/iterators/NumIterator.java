package org.hatis.parser.expressions.cycles.iterators;

public class NumIterator implements ICycleIterator<Integer> {
	private int from;
    private int step;
    private int count;
	private int dir;
    private int size;
	private int index = -1;
	
	public NumIterator(int from, int count, int step) {
        this.size = count;
        this.from = from;
        this.step = step;
        this.dir = count > 0 ? 1 : -1;
		this.from = from - (dir * step);
		this.count = dir * count;
	}

	@Override
	public boolean hasNext() {
		return count > 0;
	}

	@Override
	public Integer next() {
		index++;
		from += dir * step;
		count--;
		return from;
	}

	@Override
	public void remove() {
		//nothing
	}

	@Override
	public String key() {
		return String.valueOf(from);
	}

	@Override
	public Object item() {
		return from;
	}

	@Override
	public int index() {
		return index;
	}

	@Override
	public IteratorState getState() {
		IteratorState state = new IteratorState();
		state.key = key();
		state.item = (Comparable)item();
		
		return state;
	}

    @Override
    public int size() {
        return size;
    }

}
