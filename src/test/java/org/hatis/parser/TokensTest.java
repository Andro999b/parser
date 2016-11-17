package org.hatis.parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Andrey on 01.06.2015.
 */
public class TokensTest {

    @Test
    public void testExpressionRecognize() {
        String template = "{$exp1} text1 text2 {$exp2} text {$exp3}";

        Tokens tokens = new Tokens(template);

        List<String> expect = Arrays.asList("$exp1", " text1 text2 ", "$exp2", " text ", "$exp3");

        Assert.assertEquals(expect, tokens.getTokensList());
    }

    @Test
    public void testExpressionRecognizeWithTextEnd() {
        String template = "{$exp1} text1 text2 {$exp2} text ";

        Tokens tokens = new Tokens(template);

        List<String> expect = Arrays.asList("$exp1", " text1 text2 ", "$exp2", " text ");

        Assert.assertEquals(expect, tokens.getTokensList());
    }

    @Test
    public void testSubExpressionRecognize() {
        String template = "{$exp1:{$sub1}} text1 text2 {$exp2} text {$exp3}";

        Tokens tokens = new Tokens(template);

        List<String> expect = Arrays.asList("$exp1:{$sub1}", " text1 text2 ", "$exp2", " text ", "$exp3");

        Assert.assertEquals(expect, tokens.getTokensList());
    }

    @Test
    public void testArgumentsRecognize() {
        String token = "$exp1:{$sub1:text1}:text2";

        Tokens tokens = new Tokens("");
        String[] tokenArguments = tokens.getTokenArguments(token);

        String[] expect = new String[]{"$exp1", "{$sub1:text1}", "text2"};

        Assert.assertArrayEquals(expect, tokenArguments);
    }

    @Test
    public void testArgumentsRecognizeMathLikeSubExpression() {
        String token = "$exp1:1+{$sub1:text1}:text2";

        Tokens tokens = new Tokens("");
        String[] tokenArguments = tokens.getTokenArguments(token);

        String[] expect = new String[]{"$exp1", "1+{$sub1:text1}", "text2"};

        Assert.assertArrayEquals(expect, tokenArguments);
    }

    @Test
    public void testArgumentsRecognizeDoubleSubExpression() {
        String token = "$exp1:{$sub1:{$sub2}:text1}:text2";

        Tokens tokens = new Tokens("");
        String[] tokenArguments = tokens.getTokenArguments(token);
        String[] expect = new String[]{"$exp1", "{$sub1:{$sub2}:text1}", "text2"};
        Assert.assertArrayEquals(expect, tokenArguments);

        tokenArguments = tokens.getTokenArguments(token);
        expect = new String[]{"$exp1", "{$sub1:{$sub2}:text1}", "text2"};
        Assert.assertArrayEquals(expect, tokenArguments);
    }

    @Test
    public void testBadRecognize1(){
        String token = "$var:i:1";

        Tokens tokens = new Tokens("");
        String[] tokenArguments = tokens.getTokenArguments(token);
        String[] expect = new String[]{"$var", "i", "1"};
        Assert.assertArrayEquals(expect, tokenArguments);
    }

    @Test
    public void testBadRecognize2(){
        String token = "$calc: (2 + {$var:two:2}) * 2";

        Tokens tokens = new Tokens("");
        String[] tokenArguments = tokens.getTokenArguments(token);
        String[] expect = new String[]{"$calc", " (2 + {$var:two:2}) * 2"};
        Assert.assertArrayEquals(expect, tokenArguments);
    }

    @Test
    public void testBadRecognize3() {
        String token = "{$exp}${test}{$exp}";

        Tokens tokens = new Tokens(token);

        List<String> expect = Arrays.asList("$exp", "${test}", "$exp");
        Assert.assertEquals(expect, tokens.getTokensList());
    }
}
