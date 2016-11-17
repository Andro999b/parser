package org.hatis.parser.data.exceptions;

import org.hatis.parser.Context;

public abstract class ParserException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    protected Context context;

    public ParserException(String msg) {
        super(msg);
    }

    public ParserException(String msg, Throwable t) {
        super(msg, t);
    }
    
    public ParserException(String msg, Throwable t, Context context) {
        super(msg, t);
        this.context = context;
    }

    public ParserException(String msg, Context context) {
        super(msg);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

}
