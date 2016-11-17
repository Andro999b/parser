package org.hatis.parser.expressions.strings;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.Expression;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by andro on 03.12.15.
 */
public class SplitExpression extends Expression {
    public SplitExpression() {
        super(1, 2);
    }

    @Override
    public Object process(Context context) {
        String source = getStringArgumentValue(context, 1);
        String separator = ",";

        if(arguments.length > 2){
            separator = getStringArgumentValue(context, 2);
        }

        return StringUtils.split(source, separator);
    }
}
