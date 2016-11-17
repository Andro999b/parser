package org.hatis.parser.expressions;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.cycles.CycleExpression;
import org.hatis.parser.expressions.cycles.IteratorExpression;
import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;

/**
 * {$end} - закрытие блоков
 * @author andrey
 */
public class EndExpression extends NoArgumentsExpression {

    @Override
    public boolean isPrintable() {
        return false;
    }

    @Override
    public void processDisabledOutput(Context context) {
        process(context);
    }

    @Override
    public Object process(Context context) {
		Expression lastBlock = context.lastBlock();
		
		if(lastBlock == null)
			throw getSyntaxError("Все блоки уже закрыты", context);
		
		if(lastBlock.isCycle()){
            CycleExpression cycle = (CycleExpression)lastBlock;

            if(!cycle.isInterrupted()){
                if(context.isOutputEnable()){
                    ICycleIterator<?> itr = context.cycleIterator();

                    if(itr.hasNext()){
                        itr.next();
                        context.jumpToBlock(cycle);
                        IteratorExpression.setIteratorVars(context, itr);
                    }else{
                        context.closeBlock();

                        itr = context.cycleIterator();
                        if(itr != null){
                            IteratorExpression.setIteratorVars(context, itr);
                        }
                    }

                    return null;
                }
            }
        }
		
		context.closeBlock();
		
		return null;
	}
}
