package org.hatis.parser.expressions.types;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andrey on 18.06.2015.
 */
public class StringTest {
    @Test
    public void testNull(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("obj", null);

        String actual = parser.parseTemplate("{$str:obj}", binding);
        String expect = "";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testToString(){
        CoreParser parser = new CoreParser();

        final String expect = "test";

        DataBinding binding = new DataBinding();
        binding.setValue("obj", new Object(){
            @Override
            public String toString() {
                return expect;
            }
        });

        String actual = parser.parseTemplate("{$str:obj}", binding);

        Assert.assertEquals(expect, actual);
    }
}
