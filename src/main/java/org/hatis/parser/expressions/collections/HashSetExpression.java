package org.hatis.parser.expressions.collections;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.VarExpression;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Andrey on 18.06.2015.
 */
public class HashSetExpression extends VarExpression {
    @Override
    protected Object getVariableValue(Context context) {
        Set<Object> set = new LinkedHashSet<>(arguments.length - 1);

        for(int i = 2; i < arguments.length; i++){
            set.add(getArgumentValue(context, arguments[i]));
        }

        return set;
    }
}
