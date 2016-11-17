package org.hatis.parser.expressions;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by andro on 18.08.15.
 */
public class TestNumberFormat {

    @Test
    public void testNumberFormat1() {
        CoreParser parser = new CoreParser();

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');

        DecimalFormat numberFormat = new DecimalFormat("###,###,###.##", symbols);

        parser.getConfig().setNumberFormat(numberFormat);

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("test", "10 000 000");

        String actual = parser.parseTemplate("{$num:test}", dataBinding);
        String expect = "10000000";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNumberFormat2() {
        CoreParser parser = new CoreParser();

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(' ');

        DecimalFormat numberFormat = new DecimalFormat("###,###,###.##", symbols);

        parser.getConfig().setOutputNumberFormat(numberFormat);

        DataBinding dataBinding = new DataBinding();
        dataBinding.setValue("test", "0.5");

        String actual = parser.parseTemplate("{$num:test}", dataBinding);
        String expect = "0.5";

        Assert.assertEquals(expect, actual);
    }
}
