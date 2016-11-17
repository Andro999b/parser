package org.hatis.parser.expressions.cycles.chain;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.cycles.IteratorExpression;
import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by andro on 04.12.15.
 */
public class JoinExpression extends IteratorExpression {

    public JoinExpression() {
        super(2);
    }

    @Override
    public Object process(Context context) {
        ICycleIterator<?> iterator = getIteratorFromContext(context);
        Collection<Object> out = new ArrayList<>();

        iterator.forEachRemaining(out::add);

        String separator = ",";

        if(arguments.length > 2){
            separator = getStringArgumentValue(context, 2);
        }

        return StringUtils.join(out, separator);
    }
}
