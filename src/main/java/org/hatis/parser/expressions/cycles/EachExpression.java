package org.hatis.parser.expressions.cycles;

import org.hatis.parser.Context;

/**
 * @author andrey
 */
public class EachExpression extends CycleExpression {

    public EachExpression() {
        super(1);
    }

    @Override
    public Object process(Context context) {
        itr = getIteratorFromContext(context);

        context.beginBlock(this);

        if (itr.hasNext()) {
            itr.next();
            setIteratorVars(context, itr);
        } else {
            context.disableOutput(this);
        }

        return null;
    }
}
