package org.hatis.parser.data;

import org.hatis.parser.Context;
import org.hatis.parser.analyzers.TokensAnalyzer;

/**
 * Created by andrey on 17.05.16.
 */
public interface ParserModule {
    default void modifyAnalyzer(TokensAnalyzer analyzer){};
    default void modifyContext(Context context){};
}
