package org.hatis.parser.expressions.types;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andrey on 18.06.2015.
 */
public class TypeTest {

    @Test
    public void testNull(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("obj", null);

        String actual = parser.parseTemplate("{$type:obj}", binding);
        String expect = "null";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testClassName(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("obj", "some string");

        String actual = parser.parseTemplate("{$type:obj}", binding);
        String expect = String.class.getCanonicalName();

        Assert.assertEquals(expect, actual);
    }
}
