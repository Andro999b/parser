package org.hatis.parser.arguments;

import org.hatis.parser.Context;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Andrey on 01.06.2015.
 */
public class BooleanGetter implements ArgumentGetter<Boolean> {
    public static final BooleanGetter INSTANCE = new BooleanGetter();

    @Override
    public Boolean get(Context context, String argument) {
        Object o = ObjectGetter.INSTANCE.get(context, argument);

        if(o instanceof Boolean)
            return (Boolean)o;

        if(o instanceof Number){
            return ((Number)o).intValue() != 0;
        }

        if(o instanceof String){
            return !((String)o).isEmpty();
        }

        if(o instanceof Collection){
            return !((Collection)o).isEmpty();
        }

        if(o instanceof Map){
            return !((Map)o).isEmpty();
        }

        if(o instanceof Object[]){
            return ((Object[])o).length != 0;
        }

        return o != null;
    }
}
