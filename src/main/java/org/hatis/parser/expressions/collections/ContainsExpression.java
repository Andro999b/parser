package org.hatis.parser.expressions.collections;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.conditionals.ConditionExpression;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Andrey on 16.06.2015.
 */
public class ContainsExpression extends ConditionExpression {

    public ContainsExpression() {
        super(2, 2);
    }

    @Override
    public boolean compare(Context context) {
        Object collection = getArgumentValue(context, arguments[1]);
        if(collection == null)
            return false;

        Object value = getArgumentValue(context, arguments[2]);

        if (collection instanceof Collection) {
            return ((Collection) collection).contains(value);
        }

        if (collection instanceof Object[]) {
            return ArrayUtils.contains((Object[]) collection, value);
        }

        if (collection instanceof Map) {
            return ((Map) collection).containsValue(value);
        }

        throw getSyntaxError("Ожидалась коллекция или массив", context);
    }
}
