package org.hatis.parser.expressions.conditions;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * Created by Andrey on 17.06.2015.
 */
public class ConditionsTest {

    @Test
    public void testEqTrue(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:true}test{$end}", new DataBinding());
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
     public void testEqFalse(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:false}test{$end}", new DataBinding());
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testElse(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:false}test1{$else}test2{$end}", new DataBinding());
        String expect = "test2";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testElseEq(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:false}test1{$else:${eq:false}}test2{$end}", new DataBinding());
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testElseEqElse(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:false}test1{$else:{$eq:false}}test2{$else}test3{$end}", new DataBinding());
        String expect = "test3";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testElseElseEq(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:false}test1{$else}test3{$else:{$eq:true}}test2{$else:{$eq:true}}test4{$end}", new DataBinding());
        String expect = "test3";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testElseElseEqInner(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$if:false}{$eq:false}test1{$else}test3{$else:{$eq:true}}test2{$end}{$end}", new DataBinding());
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testInnerEq1(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:true}test1 {$eq:true}test2{$end}{$end}", new DataBinding());
        String expect = "test1 test2";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testInnerEq2(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:true}test1 {$eq:false}test2{$end}{$end}", new DataBinding());
        String expect = "test1";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testInnerEq3(){
        CoreParser parser = new CoreParser();
        String result = parser.parseTemplate("{$eq:false}test1 {$else}{$eq:false}test2{$end}{$end}", new DataBinding());
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testEqEmptyCollection(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("test", Collections.emptyList());

        String result = parser.parseTemplate("{$eq:test}test{$end}", dataBinding);
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testEqNotEmptyCollection(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("test", Collections.singletonList(1));

        String result = parser.parseTemplate("{$eq:test}test{$end}", dataBinding);
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
     public void testEqEmptyArray(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("test", new Object[]{});

        String result = parser.parseTemplate("{$eq:test}test{$end}", dataBinding);
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testEqNotEmptyArray(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("test", new Object[]{1});

        String result = parser.parseTemplate("{$eq:test}test{$end}", dataBinding);
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNot(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("test", 0);

        String result = parser.parseTemplate("{$not:test}test{$end}", dataBinding);
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testObjectsEq(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", 1);
        dataBinding.setValue("second", 1);

        String result = parser.parseTemplate("{$eq:first:second}test{$end}", dataBinding);
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testObjectsNotEq(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", 1);
        dataBinding.setValue("second", 2);

        String result = parser.parseTemplate("{$eq:first:second}test{$end}", dataBinding);
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testStringEq(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", "first");

        String result = parser.parseTemplate("{$eq:first:'first'}test{$end}", dataBinding);
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNullEq1(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", null);
        dataBinding.setValue("second", null);

        String result = parser.parseTemplate("{$eq:first:second}test{$end}", dataBinding);
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNullEq2(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", null);

        String result = parser.parseTemplate("{$eq:first}test{$end}", dataBinding);
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNotNullEq(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", new Object());

        String result = parser.parseTemplate("{$eq:first}test{$end}", dataBinding);
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNullEq3(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", null);
        dataBinding.setValue("second", 1);

        String result = parser.parseTemplate("{$eq:first:second}test{$end}", dataBinding);
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testOr1(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", true);
        dataBinding.setValue("second", false);

        String result = parser.parseTemplate("{$or:first:second}test{$end}", dataBinding);
        String expect = "test";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testOr2(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", false);
        dataBinding.setValue("second", false);

        String result = parser.parseTemplate("{$or:first:second}test{$end}", dataBinding);
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testOr3(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", false);
        dataBinding.setValue("second", false);

        String result = parser.parseTemplate("{$and:first:second}test{$end}", dataBinding);
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testAnd1(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", false);
        dataBinding.setValue("second", false);

        String result = parser.parseTemplate("{$and:first:second}test{$end}", dataBinding);
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testAnd2(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", true);
        dataBinding.setValue("second", false);

        String result = parser.parseTemplate("{$and:first:second}test{$end}", dataBinding);
        String expect = "";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testAnd3(){
        CoreParser parser = new CoreParser();

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("first", true);
        dataBinding.setValue("second", true);

        String result = parser.parseTemplate("{$and:first:second}test{$end}", dataBinding);
        String expect = "test";

        Assert.assertEquals(expect, result);
    }
}
