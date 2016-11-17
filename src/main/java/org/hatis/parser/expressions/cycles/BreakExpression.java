package org.hatis.parser.expressions.cycles;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.Expression;

public class BreakExpression extends Expression {
    @Override
    public boolean isPrintable() {
        return false;
    }

    @Override
    public Object process(Context context) {
        CycleExpression cycle = context.findLastCycleBlock();

        if(cycle == null)
            throw getSyntaxError("Ожидался цикл", context);

        cycle.interrupt();
        context.disableOutput(cycle);

        return null;
    }
}
