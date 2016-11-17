package org.hatis.parser.data.exceptions;

import org.hatis.parser.Context;

/**
 * Created by Andrey on 01.06.2015.
 */
public class InternalException extends ParserException {

    private static final long serialVersionUID = 1L;

    public InternalException(String msg) {
        super(msg);
    }

    public InternalException(String msg, Context context) {
        super(msg, context);
    }

    public InternalException(String msg, Throwable t, Context context) {
        super(msg, t, context);
    }
}
