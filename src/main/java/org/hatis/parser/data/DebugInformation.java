package org.hatis.parser.data;

import org.hatis.parser.Context;

import java.util.List;

/**
 * Created by andro on 01.07.15.
 */
public class DebugInformation {
    public static final int TOKENS_COUNT_OFFSET = 10;
    private final Context context;

    public DebugInformation(Context context) {
        this.context = context;
    }

    public StringBuilder getDetails() {
        return getDetails(TOKENS_COUNT_OFFSET, new StringBuilder());
    }

    public StringBuilder getDetails(int tokensCountOffset) {
        return getDetails(tokensCountOffset, new StringBuilder());
    }

    public StringBuilder getDetails(int tokensCountOffset, StringBuilder sb) {
        if (context != null) {
            DataAccessor dataObject = context.getParserArguments().getDataObject();

            if (dataObject instanceof Debuggable) {
                ((Debuggable) dataObject).printDebug(sb);
            }

            List<String> tokensList = context.getTokens().getTokensList();

            sb.append("Последние конструкции: ").append("\n\n");

            int end = context.getTokens().getCursor() + tokensCountOffset;
            int start = context.getTokens().getCursor() - tokensCountOffset;

            if (start < 0) {
                start = 0;
            }

            if (end > tokensList.size()) {
                end = tokensList.size();
            }

            for (; start < end; start++) {
                String token = tokensList.get(start);

                if (token.startsWith("$")) {
                    sb.append("{").append(token).append("}");
                } else {
                    sb.append(token);
                }
            }

            sb.append("\n\n");

            context.printDebug(sb);
        }

        return sb;
    }

    public static String print(Context context){
        return new DebugInformation(context).getDetails().toString();
    }
}
