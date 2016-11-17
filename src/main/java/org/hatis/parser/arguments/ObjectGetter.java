package org.hatis.parser.arguments;

import org.hatis.parser.Context;
import org.hatis.parser.data.exceptions.ArgumentException;
import org.hatis.parser.data.exceptions.SyntaxException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.*;


/**
 * Created by Andrey on 01.06.2015.
 */
public class ObjectGetter implements ArgumentGetter<Object> {
    public static final ObjectGetter INSTANCE = new ObjectGetter();

    @Override
    public Object get(Context context, String argument) {
        try {
            //recognize sub expressions
            if (argument.startsWith("{") && argument.endsWith("}")) {
                String subExpr = argument.substring(1, argument.length() - 1);
                String[] tokenArguments = context.getTokens().getTokenArguments(subExpr);
                return context.getParser().getTokenAnalyzer().analyzeInnerExpression(context, tokenArguments);
            }

            return getFromPath(context, argument);
        } catch (ArgumentException ex) {
            throw ex;
        } catch (SyntaxException ex) {
            throw new ArgumentException(ex.getMessage(), ex.getCause(), context, argument);
        }
    }

    private Object getSimpleValue(Context context, String argument) {
        if(argument.length() > 1 ) {
            if (argument.startsWith("'") && argument.endsWith("'")) {
                return argument.substring(1, argument.length() - 1);
            }

            if (argument.equals("true")) {
                return Boolean.TRUE;
            }

            if (argument.equals("false")) {
                return Boolean.FALSE;
            }

            if (argument.startsWith("$")) {
                return getFromVariable(context, argument);
            }
        }

        if (isNumber(argument)) {
            return getAsNumber(context, argument);
        }

        return getFromData(context, argument);
    }

    private boolean isNumber(String argument) {
        if (argument.charAt(0) == '-') {
            int strLen = argument.length();
            for (int i = 1; i < strLen; i++) {
                if (argument.charAt(i) == ' ') continue;
                return Character.isDigit(argument.charAt(i));
            }
        } else {
            return Character.isDigit(argument.charAt(0));
        }

        return false;
    }

    private Object getFromPath(Context context, String argument) {
        List<String> getters = splitArgument(argument, '#');
        Object targetObject = getSimpleValue(context, getters.get(0));

        int size = getters.size();
        for (int i = 1; i < size; i++) {
            targetObject = getFromCollections(context, targetObject, getters.get(i));
        }

        return targetObject;
    }

    private Object getFromCollections(Context context, Object targetObject, String getter) {
        if (targetObject instanceof Object[]) {
            return getFromArray(context, (Object[]) targetObject, getter);
        }

        if (targetObject instanceof List) {
            return getFromList(context, (List) targetObject, getter);
        }

        if (targetObject instanceof Map) {
            return getFromMap(context, (Map) targetObject, getter);
        }

        if (targetObject instanceof Collection) {
            return getFromCollection(context, (Collection) targetObject, getter);
        }

        return null;
    }

    private Object getFromMap(Context context, Map targetObject, String getter) {
        if (getter.isEmpty())
            return targetObject.size();

        Object key = getSimpleValue(context, getter);

        return targetObject.get(key);
    }

    private Object getFromList(Context context, List targetObject, String getter) {
        if (getter.isEmpty())
            return targetObject.size();

        int i = getCollectionIndex(context, getter);
        if (i < 0 || i >= targetObject.size())
            return null;

        return targetObject.get(i);
    }

    private Object getFromCollection(Context context, Collection targetObject, String getter) {
        if (getter.isEmpty())
            return targetObject.size();

        int i = getCollectionIndex(context, getter);
        if (i < 0 || i >= targetObject.size())
            return null;

        //slow O(n) iterations
        Iterator iterator = targetObject.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            if (count == i) {
                return iterator.next();
            } else {
                iterator.next();
            }
            count++;
        }

        return null;
    }

    private Object getFromArray(Context context, Object[] targetObject, String getter) {
        if (getter.isEmpty())
            return targetObject.length;

        int i = getCollectionIndex(context, getter);
        if (i < 0 || i >= targetObject.length)
            return null;

        return targetObject[i];
    }

    private int getCollectionIndex(Context context, String getter) {
        try {
            return ((Number) getSimpleValue(context, getter)).intValue();
        } catch (ClassCastException e) {
            throw new SyntaxException("Переменная " + getter + " не является числом: " + context.getVarExpression(getter), context);
        }
    }

    private Object getFromData(Context context, String argument) {
        List<String> splitArgument = splitArgument(argument, '.');
        Object[] keys = replaceWithVars(context, splitArgument);

        try {
            return context.getParserArguments().getDataObject().getValue(keys);
        } catch (Exception e) {
            throw new ArgumentException(e.getMessage(), e, context, StringUtils.join(keys, '.'));
        }
    }

    private Object getFromVariable(Context context, String argument) {
        return context.getVarExpression(argument);
    }

    private Object[] replaceWithVars(Context context, List<String> splitStrings) {
        int size = splitStrings.size();
        Object[] out = new Object[size];

        if (size > 0) {
            //don` replace first part of path
            out[0] = splitStrings.get(0);

            for (int i = 1; i < size; i++) {
                if (context.hasVarExpression(splitStrings.get(i))) {
                    out[i] = getFromVariable(context, splitStrings.get(i));
                } else {
                    out[i] = splitStrings.get(i);
                }
            }
        }

        return out;
    }

    private Number getAsNumber(Context context, String argument) {
        try {
            return context.getParserConfig().getInputNumberFormat().parse(argument);
        } catch (ParseException e) {
            throw new SyntaxException("Не верный числовой формат: " + argument, context);
        }
    }

    private List<String> splitArgument(String argument, Character separator) {
        List<String> substring = new ArrayList<>();

        int length = argument.length();
        int begin = 0;
        boolean stringStart = false;

        for(int i = 0; i < length; i++){
            if(argument.charAt(i) == '\''){
                stringStart = !stringStart;
            }

            if(stringStart) continue;

            if(argument.charAt(i) == separator){
                substring.add(argument.substring(begin, i));
                i++;
                begin = i;
            }
        }

        substring.add(argument.substring(begin, argument.length()));

        return substring;
    }
}
