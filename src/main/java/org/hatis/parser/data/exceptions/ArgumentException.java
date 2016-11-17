package org.hatis.parser.data.exceptions;

import org.hatis.parser.Context;

/**
 * Created by Andrey on 01.06.2015.
 */
public class ArgumentException extends SyntaxException {

    private static final long serialVersionUID = 1L;

    public ArgumentException(String msg, Context context, String argument) {
        super("Ошибка аргумента: " + argument + ": " + msg, context);
    }

    public ArgumentException(String msg, Throwable t, Context context, String argument) {
        super("Ошибка аргумента: " + argument + ": " + msg, t, context);
    }
}
