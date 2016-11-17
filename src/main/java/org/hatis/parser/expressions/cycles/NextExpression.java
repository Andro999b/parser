package org.hatis.parser.expressions.cycles;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.Expression;
import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;

public class NextExpression extends Expression {
    @Override
    public boolean isPrintable() {
        return false;
    }

    @Override
    public Object process(Context context) {
        CycleExpression cycle = context.findLastCycleBlock();

        if(cycle == null)
            throw getSyntaxError("Ожидался цикл", context);

        ICycleIterator<?> itr = cycle.cycleIterator();

        if(itr.hasNext()){
            itr.next();
            IteratorExpression.setIteratorVars(context, itr);

            context.jumpToBlock(cycle);
        }else{
            context.disableOutput(cycle);
        }

        return null;
    }
}
