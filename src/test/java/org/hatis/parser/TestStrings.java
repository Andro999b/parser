package org.hatis.parser;

import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Hatis on 06.11.2015.
 */
public class TestStrings {
    @Test
    public void testString1(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("{$out:'{$test}'}", new DataBinding());
        String expect = "{$test}";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testString2(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("{$out:'test#1'}", new DataBinding());
        String expect = "test#1";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testString3(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("{$out:'test.test.test'}", new DataBinding());
        String expect = "test.test.test";

        Assert.assertEquals(expect, actual);
    }
}
