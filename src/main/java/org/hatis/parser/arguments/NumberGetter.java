package org.hatis.parser.arguments;

import org.hatis.parser.Context;
import org.hatis.parser.data.exceptions.ArgumentException;

import java.text.ParseException;

/**
 * Created by Andrey on 01.06.2015.
 */
public class NumberGetter implements ArgumentGetter<Number> {
    public static final NumberGetter INSTANCE = new NumberGetter();

    @Override
    public Number get(Context context, String argument) {
        Object o = ObjectGetter.INSTANCE.get(context, argument);

        if(o == null)
            return 0;

        if(o instanceof Number){
            return (Number) o;
        }

        if(o instanceof Boolean){
            return (Boolean) o ? 1 : 0;
        }

        try {
            String source = o.toString();
            if(source.isEmpty())
                return 0;

            return context.getParserConfig().getInputNumberFormat().parse(source);
        } catch (ParseException e) {
            throw new ArgumentException("Не верный числовой формат: " + o.toString(), context, argument);
        }
    }
}
