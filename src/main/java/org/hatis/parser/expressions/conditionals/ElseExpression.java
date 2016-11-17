package org.hatis.parser.expressions.conditionals;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.Expression;

public class ElseExpression extends ConditionExpression {
    @Override
    public void prepare(String[] arguments, int position, int id) {
        super.prepare(arguments, position, id);
        res = true;
    }

    @Override
	public Object process(Context context) {
		Expression lastBlock = context.lastBlock();
		
		if(lastBlock == null || !lastBlock.isCondition()){
			throw getSyntaxError("Ожидался блок условия перед блоком else", context);
		}

		ConditionExpression condition = (ConditionExpression) lastBlock;

        boolean lastConditionBlockOutput = context.isDisableOutput(lastBlock);

        context.closeBlock();
        context.beginBlock(this);

        if(lastConditionBlockOutput || context.isOutputEnable()) {
            if (condition.compareResult()) {
                context.disableOutput(this);
                return null;
            }

            if (arguments.length > 1) {
                res = compare(context);
                if (!res) {
                    context.disableOutput(this);
                }
            }
        }

		return null;
	}

	@Override
	public void processDisabledOutput(Context context) {
        process(context);
	}

	@Override
    protected boolean compare(Context context) {
        return getBooleanArgumentValue(context, arguments[1]);
    }
}
