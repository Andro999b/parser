package org.hatis.parser.expressions.collections;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.VarExpression;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Andrey on 18.06.2015.
 */
public class HashMapExpression extends VarExpression {
    public HashMapExpression() {
        super(1, 2);
    }

    @Override
    protected Object getVariableValue(Context context) {
        Map<String, Object> map;

        Object var = context.getVar(getVarName());
        if(var != null){
            try{
                map = (Map<String, Object>)var;
            }catch (ClassCastException e){
                map = new LinkedHashMap<>();
            }
        } else {
            map = new LinkedHashMap<>();
        }

        if(arguments.length > 2) {
            String key = getStringArgumentValue(context, arguments[2]);
            Object value = getArgumentValue(context, arguments[3]);

            map.put(key, value);
        }

        return map;
    }
}
