package org.hatis.parser.expressions.conditionals;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.Expression;

/**
 * Created by Andrey on 16.06.2015.
 */
public abstract class ConditionExpression extends Expression {
    protected boolean res;

    public ConditionExpression() {
    }

    public ConditionExpression(int minArguments, int maxArguments) {
        super(minArguments, maxArguments);
    }

    @Override
    public void prepare(String[] arguments, int position, int id) {
        res = false;
        super.prepare(arguments, position, id);
    }

    @Override
    public Object process(Context context) {
        res = compare(context);
        if (!res) {
            context.disableOutput(this);
        }

        context.beginBlock(this);

        return res;
    }

    protected abstract boolean compare(Context context);

    @Override
    public boolean isCondition() {
        return true;
    }

    @Override
    public boolean isPrintable() {
        return false;
    }

    @Override
    public boolean isBlock() {
        return true;
    }

    public boolean compareResult() {
        return res;
    }
}
