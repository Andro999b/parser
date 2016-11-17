package org.hatis.parser;

import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by andro on 27.08.15.
 */
public class ScopingTest {
    @Test
    public void testScope(){
        CoreParser parser = new CoreParser();

        String template = "{$if:true}{$var:test:'test'}{$if:true}{$var:test:'test1'}{$test} {$end}{$if:true}{$var:test:'test2'}{$test} {$end}{$test}{$end}";
        String actual = parser.parseTemplate(template, new DataBinding());
        String expect = "test1 test2 test";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testScopeInCycles(){
        CoreParser parser = new CoreParser();

        String template = "{$var:count:2}";
        template += "{$count} ";
        template += "{$range:1:$count}";
        template += "{$if:$count:2}test {$end}";
        template += "{$end}";
        template += "{$count} ";
        template += "{$range:1:$count}";
        template += "{$if:$count:2}test {$end}";
        template += "{$end}";

        String actual = parser.parseTemplate(template, new DataBinding());
        String expect = "2 test test 2 test test";

        Assert.assertEquals(expect, actual);
    }
}
