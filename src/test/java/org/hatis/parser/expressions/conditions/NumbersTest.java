package org.hatis.parser.expressions.conditions;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Created by Andrey on 19.06.2015.
 */
public class NumbersTest {

    @Test
    public void testEq(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:1:1}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testEqDifferentFormat(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("d", 1.0d);
        dataBinding.setValue("l", 1l);

        String result = parser.parseTemplate("{$eq:d:l}test{$end}", dataBinding);
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNotEq(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:1:2}test{$end}", new DataBinding());
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testGt(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$gt:2:1}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNotGt(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$gt:1:2}test{$end}", new DataBinding());
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testGte(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$gte:2:1}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testGtEqual(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$gte:1:1}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNotGte(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$gte:1.2:1.4}test{$end}", new DataBinding());
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testLt(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$lt:1:2.5}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNotLt(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$lt:2.5:1}test{$end}", new DataBinding());
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testLte(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$lte:1:2}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testLtEqual(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$lte:1:1}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNotLte(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$lte:1.6:1.2}test{$end}", new DataBinding());
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testBt(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$bt:1.5:1:2}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testBtLeftBound(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$bt:1:1:2}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testBtRightBound(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$bt:2:1:2}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNotBt(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$bt:5:1:2}test{$end}", new DataBinding());
        String expect = "";

        Assert.assertEquals(expect, result);
    }
}
