package org.hatis.parser.expressions;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.CoreParserArguments;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by andrey on 11.05.16.
 */
public class DefaultVarTest {

    @Test
    public void testExistsVar(){
        CoreParser parser = new CoreParser();

        CoreParserArguments arguments = new CoreParserArguments();
        arguments.setVariable("test", "1");
        String result = parser.parseTemplate("{$default:test:2}Test Var: {$out:$test}", arguments);
        String expect = "Test Var: 1";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNotExistsVar(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("{$default:test:2}Test Var: {$out:$test}", new DataBinding());
        String expect = "Test Var: 2";

        Assert.assertEquals(expect, result);
    }
}
