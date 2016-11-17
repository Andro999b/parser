package org.hatis.parser.expressions.strings;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.Expression;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andro on 03.12.15.
 */
public class ConcatExpression extends Expression {
    public ConcatExpression() {
        super(1, 2);
    }

    @Override
    public Object process(Context context) {
        List<String> arr = new ArrayList<>(arguments.length - 1);

        for(int i = 1; i < arguments.length; i++){
            arr.add(getStringArgumentValue(context, arguments[i]));
        }

        return StringUtils.join(arr, "");
    }
}
