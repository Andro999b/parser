package org.hatis.parser.expressions.collections;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.VarExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 18.06.2015.
 */
public class ArrExpression extends VarExpression {
    @Override
    protected Object getVariableValue(Context context) {
        List<Object> arr = new ArrayList<>(arguments.length - 1);

        for(int i = 2; i < arguments.length; i++){
            arr.add(getArgumentValue(context, arguments[i]));
        }

        return arr;
    }
}
