package org.hatis.parser.arguments;

import org.hatis.parser.Context;

/**
 * Created by Andrey on 01.06.2015.
 */
public interface ArgumentGetter<T> {
    T get(Context context, String argument);
}
