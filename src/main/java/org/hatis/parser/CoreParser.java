package org.hatis.parser;

import org.hatis.parser.analyzers.CoreAnalyzer;
import org.hatis.parser.analyzers.TokensAnalyzer;
import org.hatis.parser.data.CoreParserArguments;
import org.hatis.parser.data.CoreParserConfig;
import org.hatis.parser.data.DataAccessor;
import org.hatis.parser.data.exceptions.InternalException;
import org.hatis.parser.data.exceptions.ParserException;
import org.hatis.parser.data.exceptions.SyntaxException;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Andrey on 01.06.2015.
 */
public class CoreParser {
    protected CoreParserConfig config;

    public CoreParser() {
        this(new CoreParserConfig(new CoreAnalyzer()));
    }

    public CoreParser(CoreParserConfig config) {
        this.config = config;
    }

    public CoreParser(CoreParser parser) {
        this(parser.config);
    }

    public String parseTokens(Tokens tokens, CoreParserArguments arguments) {
        if(tokens.isEmpty())
            return StringUtils.EMPTY;

        Context context = new Context(arguments, this, tokens);
        return parseContext(context);
    }

    /**
     * Функция просто парсит заданый шаблон для заданных аргументов
     *
     * @param template
     * @param arguments
     * @return
     * @throws ParserException
     */
    public String parseTemplate(String template, CoreParserArguments arguments) {
        Tokens tokens = new Tokens(template);
        if(tokens.isEmpty())
            return StringUtils.EMPTY;

        Context context = new Context(arguments, this, tokens);
        return parseContext(context);
    }

    public String parseTemplate(String template, DataAccessor dataAccessor) {
        Tokens tokens = new Tokens(template);
        if(tokens.isEmpty())
            return StringUtils.EMPTY;

        Context context = new Context(new CoreParserArguments(dataAccessor), this, tokens);
        return parseContext(context);
    }

    protected void setUpContext(Context context) {
        config.getModules().forEach(module -> module.modifyContext(context));
    }

    public String parseContext(Context context) {
        String result;
        StringBuilder sb = new StringBuilder();
        Object buffer;

        setUpContext(context);

        //Скопируем значения по умолчанию
        TokensAnalyzer analyzer = config.getAnalyzer();

        try {
            while (context.getTokens().hasNext()) {
                buffer = analyzer.analyze(context);
                if (analyzer.isPrintLastExpression()) {
                    sb.append(buffer);
                }
            }

            if (context.getParserArguments().isTrimWhiteSpaces()) {
                result = normalizeString(sb);
            } else {
                result = sb.toString();
            }

        } catch (ParserException pe) {
            throw pe;
        } catch (Exception e) {
            throw new InternalException("Internal Parser Exception!", e, context);
        }

        if (context.lastBlock() != null || context.cycleIterator() != null) {
            throw new SyntaxException("Неожиданое окончание шаблона", context);
        }

        return result;
    }

    private String normalizeString(StringBuilder sb) {
        StringBuilder out = new StringBuilder();

        //replace repeat of spaces and new lines
        int len = sb.length();
        boolean spaceFirst = false;

        for (int i = 0; i < len; i++) {
            char ch = sb.charAt(i);

            switch (ch){
                case ' ':
                case '\n':
                case '\t':
                case '\r':
                    if(spaceFirst)
                        continue;
                    else{
                        out.append(' ');
                        spaceFirst = true;
                    }
                    break;
                default: {
                    out.append(ch);
                    spaceFirst = false;
                }
            }
        }

        //trim
        if (out.length() > 0){
            if(out.charAt(0) == ' '){
                out.deleteCharAt(0);
            }

            if(out.length() > 0){
                if(out.charAt(out.length() - 1) == ' '){
                    out.deleteCharAt(out.length() - 1);
                }
            }
        }

        return out.toString();
    }

    public TokensAnalyzer getTokenAnalyzer() {
        return config.getAnalyzer();
    }

    public CoreParserConfig getConfig() {
        return config;
    }
}
