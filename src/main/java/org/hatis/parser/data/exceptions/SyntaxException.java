package org.hatis.parser.data.exceptions;

import org.hatis.parser.Context;
import org.hatis.parser.data.DebugInformation;
import org.hatis.parser.expressions.Expression;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * Created by Andrey on 01.06.2015.
 */
public class SyntaxException extends ParserException {
    public static final int TOKENS_COUNT_OFFSET = 10;

    private static final long serialVersionUID = 1L;
    private Expression exp;

    public SyntaxException(String msg, Context context) {
        super(msg, context);
    }

    public SyntaxException(String msg, Throwable t, Context context) {
        super(msg, t, context);
    }

    public SyntaxException(String msg, Expression exp, Context context) {
        super("Ощибка синтаксиса в выражении {" + StringUtils.join(exp.getArguments(), ":") + "}: " + msg, context);
        this.exp = exp;
    }

    public SyntaxException(String msg, Throwable t, Expression exp, Context context) {
        super("Ощибка синтаксиса в выражении {" + StringUtils.join(exp.getArguments(), ":") + "}: " + msg, t, context);
        this.exp = exp;
    }


    public String getExceptionDetails() {
        return getExceptionDetails(TOKENS_COUNT_OFFSET);
    }

    public String getExceptionDetails(int tokensCountOffset) {
        StringBuilder sb = new StringBuilder();
        sb.append("Сообщение: ").append(getMessage()).append("\n");

        if (exp != null) {
            sb.append("\n");
            sb.append("Класс выражения: ").append(exp.getClass().getCanonicalName()).append("\n");
            sb.append("Значения агрументов: ").append("\n");
            exp.printDebug(context, sb);
            sb.append("\n");
        }

        sb = new DebugInformation(context).getDetails(tokensCountOffset, sb);

        return sb.toString();
    }
}
