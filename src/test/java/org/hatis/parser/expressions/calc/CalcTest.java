package org.hatis.parser.expressions.calc;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Andrey on 27.06.2015.
 */
public class CalcTest {

    @Test
    public void testPriority1() {
        CoreParser parser = new CoreParser();
        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$calc: 2 + 2 * 2}", binding);
        String expect = "6.0";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testPriority2() {
        CoreParser parser = new CoreParser();
        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$calc: (2 + 2) * 2}", binding);
        String expect = "8.0";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testDataAccess() {
        CoreParser parser = new CoreParser();
        DataBinding binding = new DataBinding();
        binding.setValue("two", 2);

        String actual = parser.parseTemplate("{$calc: (2 + two) * 2}", binding);
        String expect = "8.0";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testVar1() {
        CoreParser parser = new CoreParser();
        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$var:two:2}{$calc: (2 + $two) * 2}", binding);
        String expect = "8.0";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testVar2(){
        CoreParser parser = new CoreParser();
        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$calc: (2 + {$var:two:2}) * 2} {$two}", binding);
        String expect = "8.0 2";

        Assert.assertEquals(expect, actual);
    }
}
