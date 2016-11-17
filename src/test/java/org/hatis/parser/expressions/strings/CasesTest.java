package org.hatis.parser.expressions.strings;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by andro on 02.09.15.
 */
public class CasesTest {

    @Test
    public void testLowerCase(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("v1", "TEST");

        String actual = parser.parseTemplate("{$lower:v1}", binding);
        String expect = "test";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testUpperCase(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("v1", "test");

        String actual = parser.parseTemplate("{$upper:v1}", binding);
        String expect = "TEST";

        Assert.assertEquals(expect, actual);
    }
}
