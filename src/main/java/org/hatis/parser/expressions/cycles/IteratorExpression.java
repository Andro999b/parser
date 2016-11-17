package org.hatis.parser.expressions.cycles;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.Expression;
import org.hatis.parser.expressions.cycles.iterators.ArrayIterator;
import org.hatis.parser.expressions.cycles.iterators.DummyIterator;
import org.hatis.parser.expressions.cycles.iterators.ICycleIterator;
import org.hatis.parser.expressions.cycles.iterators.MapIterator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Created by andro on 04.12.15.
 */
public abstract class IteratorExpression extends Expression {
    public IteratorExpression() {
        super(1, 1);
    }

    public IteratorExpression(int maxArguments) {
        super(1, maxArguments);
    }

    public IteratorExpression(int minArguments, int maxArguments) {
        super(minArguments < 1 ? 1 : minArguments, maxArguments);
    }

    @SuppressWarnings("unchecked")
    protected ICycleIterator<?> createObjectValueIterator(Context context, String source) {
        Object argumentValue = getArgumentValue(context, source);

        if (argumentValue == null) {
            return new DummyIterator();
        }

        if (argumentValue instanceof Map) {
            return new MapIterator((Map) argumentValue);
        } else if (argumentValue instanceof Collection) {
            return new ArrayIterator((Collection) argumentValue);
        } else if (argumentValue instanceof Object[]) {
            return new ArrayIterator(Arrays.asList((Object[]) argumentValue));
        } else if(argumentValue instanceof ICycleIterator) {
            return (ICycleIterator) argumentValue;
        }

        throw getSyntaxError("Объект не является коллекцией элементов: " + argumentValue, context);
    }

    protected ICycleIterator<?> getIteratorFromContext(Context context) {
        String source = arguments[1];

        if (!source.isEmpty()) {
            return createObjectValueIterator(context, source);
        }

        return new DummyIterator();
    }

    public static void setIteratorVars(Context context, ICycleIterator<?> itr) {
        context.setVarInCurrentScope("total", itr.size());
        context.setVarInCurrentScope("last", !itr.hasNext());
        context.setVarInCurrentScope("index", itr.index());
        context.setVarInCurrentScope("key", itr.key());
        context.setVarInCurrentScope("item", itr.item());
        context.setVarInCurrentScope("val", itr.item());
    }
}
