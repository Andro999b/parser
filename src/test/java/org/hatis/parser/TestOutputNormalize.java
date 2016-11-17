package org.hatis.parser;

import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by andro on 17.07.15.
 */
public class TestOutputNormalize {

    @Test
    public void testSpaceNormalize(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("test        string", new DataBinding());
        String expect = "test string";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testSpaceTrimAndNormalize(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("    test        string     ", new DataBinding());
        String expect = "test string";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNewLineTrimAndNormalize(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("    test\n\n\n\rstring\t", new DataBinding());
        String expect = "test string";

        Assert.assertEquals(expect, actual);
    }
}
