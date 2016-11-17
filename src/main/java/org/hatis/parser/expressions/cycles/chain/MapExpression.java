package org.hatis.parser.expressions.cycles.chain;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.cycles.IteratorExpression;
import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andro on 04.12.15.
 */
public class MapExpression extends IteratorExpression {
    public MapExpression() {
        super(2, 3);
    }

    @Override
    public Object process(Context context) {
        ICycleIterator<?> iterator = getIteratorFromContext(context);
        Object result;

        context.openVarScope();
        if(arguments.length > 3){
            Map<String, Object> out = new LinkedHashMap<>(iterator.size());

            while (iterator.hasNext()){
                iterator.next();
                setIteratorVars(context, iterator);

                String mappedKey = getStringArgumentValue(context, 2);
                Object mappedValue = getArgumentValue(context, 3);

                out.put(mappedKey, mappedValue);
            }
            result = out;
        } else {
            List<Object> out = new ArrayList<>(iterator.size());

            while (iterator.hasNext()){
                iterator.next();
                setIteratorVars(context, iterator);

                String mappedValue = getStringArgumentValue(context, 2);

                out.add(mappedValue);
            }
            result = out;
        }

        context.closeVarScope();
        return result;
    }
}
