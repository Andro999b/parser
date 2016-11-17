package org.hatis.parser.arguments;

import org.hatis.parser.Context;

/**
 * Created by Andrey on 01.06.2015.
 */
public class StringGetter implements ArgumentGetter<String> {
    public static final StringGetter INSTANCE = new StringGetter();

    @Override
    public String get(Context context, String argument) {
        Object o = ObjectGetter.INSTANCE.get(context, argument);

        if(o == null)
            return "";

        return o.toString();
    }
}
