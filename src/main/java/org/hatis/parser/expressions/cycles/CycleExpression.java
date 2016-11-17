package org.hatis.parser.expressions.cycles;

import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;
/**
 * Абстрактный класс циклов
 * @author andrey
 *
 */
public abstract class CycleExpression extends IteratorExpression {

    public CycleExpression() {
    }

    public CycleExpression(int maxArguments) {
        super(maxArguments);
    }

    protected ICycleIterator<?> itr;

    public boolean isInterrupted() {
        return interrupted;
    }

    public void interrupt() {
        this.interrupted = true;
    }

    protected boolean interrupted = false;

    @Override
    public boolean isPrintable() {
        return false;
    }

    @Override
    public boolean isBlock() {
        return true;
    }
	
	@Override
	public boolean isCycle() {
		return true;
	}

    @Override
    public void prepare(String[] arguments, int position, int id) {
        itr = null;
        super.prepare(arguments, position, id);
    }

	public ICycleIterator<?> cycleIterator(){
		return itr;
	}
    
    
}
