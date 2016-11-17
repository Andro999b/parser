package org.hatis.parser.expressions.types;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andrey on 17.06.2015.
 */
public class NumTest {
    @Test
    public void testString1(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("number", "1.234");

        String actual = parser.parseTemplate("{$num:number}", binding);
        String expect = "1.234";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testString2(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("{$num:'1.234'}", new DataBinding());
        String expect = "1.234";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNull(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("number", null);

        String actual = parser.parseTemplate("{$num:number}", binding);
        String expect = "0";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNumber1(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("number", 1.234);

        String actual = parser.parseTemplate("{$num:number}", binding);
        String expect = "1.234";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNumber2(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("{$num:1.234}", new DataBinding());
        String expect = "1.234";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testBooleanTrue(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("{$num:true}", new DataBinding());
        String expect = "1";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testBooleanFalse(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("{$num:false}", new DataBinding());
        String expect = "0";

        Assert.assertEquals(expect, actual);
    }
}
