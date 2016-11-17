package org.hatis.parser.expressions.collections;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.SingleArgumentExpression;

import java.util.Collection;
import java.util.Map;

public class SizeExpression extends SingleArgumentExpression {

    private Integer getArgumentValueSize(Context context, String from) {
        Integer result;
        Object value = getArgumentValue(context, from);
        if(value instanceof Collection){
            result = ((Collection)value).size();
        } else if(value instanceof Map){
            result = ((Map)value).size();
        } else if(value instanceof String){
            result = ((String) value).length();
        } else if(value instanceof Object[]){
            result = ((Object[]) value).length;
        } else{
            throw getSyntaxError("Ожидалась коллекция данных: " + from, context);
        }
        return result;
    }

    @Override
    public Object process(Context context) {
        return getArgumentValueSize(context, arguments[1]);
    }
    
    @Override
    protected void printArgumentsDebug(Context context, StringBuilder sb, int from) {
        super.printArgumentsDebug(context, sb, 2);
    }
}
