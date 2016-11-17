package org.hatis.parser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Not thread safe!
 * Need full rewrite
 * @author andrey
 */
public class Tokens {
    private List<String> tokens = new ArrayList<String>();
    private int cursor = 0;
    private String current;
    private StringBuilder buffer = new StringBuilder();
    private Map<String, String[]> argumentsCache = new HashMap<String, String[]>();

    public Tokens(Tokens t) {
        this.tokens = new ArrayList<>(t.tokens);
    }

    public Tokens(String template) {
        if (StringUtils.isEmpty(template)) {//empty template
            return;
        }

        if (template.length() < 4) {//expression contain at list 4 character. For example "{$p}"
            tokens.add(template);
            return;
        }

        readText(tokens, template, 0);
    }

    public boolean isEmpty() {
        return tokens.isEmpty();
    }

    public boolean hasNext() {
        return cursor < tokens.size();
    }

    public String current() {
        return current;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int c) {
        cursor = c;
    }

    public String next() {
        current = tokens.get(cursor);
        cursor++;
        return current;
    }

    public String[] getTokenArguments(String token) {
        if (argumentsCache.containsKey(token))
            return argumentsCache.get(token);

        ArrayList<String> out = new ArrayList<String>();
        readArgument(out, token, 0);
        String[] result = out.toArray(new String[out.size()]);

        argumentsCache.put(token, result);

        return result;
    }

    public List<String> getTokensList() {
        return tokens;
    }

    private int readArgument(List<String> out, String template, int offset) {
        int length = template.length();

        int counter = offset;
        int lastTextStart = offset;
        int innerExpressions = 0;

        boolean startString = false;

        for (; counter < length - 2; ) {
            if (isString(template, counter))
                startString = !startString;

            if (startString) continue;

            if (isArgumentsSeparator(template, counter)) {
                if (innerExpressions == 0) {
                    writeSubstringAndAddToken(out, template, lastTextStart, counter);
                    counter++;
                    lastTextStart = counter;
                } else {
                    counter++;
                }
            } else if (isBeginExpression(template, counter)) {//is expression begin
                innerExpressions++;
                counter += 2;
            } else if (isEndExpression(template, counter)) {
                innerExpressions--;
                counter++;
            } else {
                counter++;
            }
        }

        //read short tail
        for (; counter < length; ) {
            if (isArgumentsSeparator(template, counter)) {
                if (innerExpressions == 0) {
                    writeSubstringAndAddToken(out, template, lastTextStart, counter);
                    counter++;
                    lastTextStart = counter;
                } else {
                    counter++;
                }
            } else if (isEndExpression(template, counter)) {
                innerExpressions--;
                counter++;
            } else {
                counter++;
            }
        }
        //add rest of template
        writeSubstringAndAddToken(out, template, lastTextStart, length);

        return counter;
    }

    private int readText(List<String> out, String template, int offset) {
        int length = template.length();

        int counter = offset;
        int lastTextStart = offset;

        for (; counter < length - 2; ) {
            if (isBeginExpression(template, counter)) {//is expression begin
                writeSubstringAndAddToken(out, template, lastTextStart, counter);

                int expEnd = readExpression(out, template, counter);
                lastTextStart = expEnd;//remember where expression end
                if (expEnd == counter) {//not any character reed, so it is not an expression
                    counter++;
                } else {
                    counter = expEnd;
                }
            } else {
                counter++;
            }
        }

        //add rest of template
        writeSubstringAndAddToken(out, template, lastTextStart, length);

        return counter;
    }

    private int readExpression(List<String> out, String template, int offset) {
        buffer.append(template.charAt(offset + 1));//add $ - character

        int length = template.length();

        int expressionStart = offset + 2;//start after $ - character
        int counter = expressionStart;
        int level = 0;

        boolean startString = false;

        for (; counter < length - 2; counter++) {
            if (isString(template, counter))
                startString = !startString;

            if (startString) continue;

            if (isBeginExpression(template, counter)) {
                level++;
                counter++;
            } else if (isEndExpression(template, counter)) {
                if (level == 0) {
                    writeSubstringAndAddToken(out, template, expressionStart, counter);
                    counter++;
                    return counter;
                }
                level--;
            }
        }

        for (; counter < length; counter++) {
            if (isString(template, counter))
                startString = !startString;

            if (startString) continue;

            if (isEndExpression(template, counter)) {
                if (level == 0) {
                    writeSubstringAndAddToken(out, template, expressionStart, counter);
                    counter++;
                    return counter;
                }
                level--;
            }
        }

        //this is not an expression
        return offset;
    }

    private void writeSubstringAndAddToken(List<String> out, String template, int start, int end) {
        buffer.append(template.substring(start, end));//add text token
        addToken(buffer, out);
        buffer.setLength(0);//clean StringBuilder
    }

    public boolean isBeginExpression(String template, int i) {
        return template.charAt(i) == '{' && template.charAt(i + 1) == '$';
    }

    private boolean isEndExpression(String template, int i) {
        return template.charAt(i) == '}';
    }

    private boolean isArgumentsSeparator(String template, int i) {
        return template.charAt(i) == ':';
    }

    private boolean isString(String template, int i) {
        return template.charAt(i) == '\'';
    }

    private void addToken(StringBuilder buffer, List<String> out) {
        if (buffer.length() > 0)
            out.add(buffer.toString());
    }
}
