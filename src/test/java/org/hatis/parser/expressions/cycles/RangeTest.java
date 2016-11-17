package org.hatis.parser.expressions.cycles;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andrey on 29.06.2015.
 */
public class RangeTest {

    @Test
    public void testCount() {
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$range:3}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:0[1]:1[2]:2";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testStartFrom() {
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$range:1:3}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:1[1]:2[2]:3";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testStep() {
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$range:1:3:2}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:1[1]:3[2]:5";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNegativeStep() {
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$range:1:3:-2}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:1[1]:-1[2]:-3";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNegativeCount() {
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$range:1:-3:2}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:1[1]:-1[2]:-3";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNegativeStepAndCount() {
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$range:1:-3:-2}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:1[1]:3[2]:5";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNegativeFloatStart() {
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$range:-1.5:3:2}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:-1[1]:1[2]:3";

        Assert.assertEquals(expect, actual);
    }
}
