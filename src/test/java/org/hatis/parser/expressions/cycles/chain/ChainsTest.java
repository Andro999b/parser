package org.hatis.parser.expressions.cycles.chain;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by andro on 04.12.15.
 */
public class ChainsTest {

    @Test
    public void testSortEach(){
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add("d");
        set.add("a");
        set.add("e");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", set);

        String actual = parser.parseTemplate("{$each:{$sort:v1}}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:a[1]:d[2]:e";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testRangeEach(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$each:{$range:3}}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:0[1]:1[2]:2";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testSortJoin(){
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add("d");
        set.add("a");
        set.add("e");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", set);

        String actual = parser.parseTemplate("{$join:{$sort:v1}}", binding);
        String expect = "a,d,e";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testRangeJoin(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$join:{$range:3}:''}", binding);
        String expect = "012";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testSplitSortJoin(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("in", "b c a");

        String actual = parser.parseTemplate("{$join:{$sort:{$split:in:' '}}:''}", binding);
        String expect = "abc";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testSplitSortJoinByCommas(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("in", "b,c,a");

        String actual = parser.parseTemplate("{$join:{$sort:{$split:in}}}", binding);
        String expect = "a,b,c";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testMapEachForLists(){
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add("d");
        set.add("a");
        set.add("e");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", set);

        String actual = parser.parseTemplate("{$each:{$map:v1:{$concat:$val:'1'}}}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:d1[1]:a1[2]:e1";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testMapEachForMaps(){
        CoreParser parser = new CoreParser();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("first", 1);
        map.put("true", true);
        map.put("second", "second");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", map);

        String actual = parser.parseTemplate("{$each:{$map:v1:$key:{$concat:$val:'_1'}}}[{$index}>{$key}]:{$item}{$end}", binding);
        String expect = "[0>first]:1_1[1>true]:true_1[2>second]:second_1";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testRangeFilterEach(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$each:{$filter:{$range:6}:{$gt:$val:2}}}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:3[1]:4[2]:5";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testRangeReverseEach(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();

        String actual = parser.parseTemplate("{$each:{$reverse:{$range:3}}}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:2[1]:1[2]:0";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testReverseSortEach(){
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add("d");
        set.add("a");
        set.add("e");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", set);

        String actual = parser.parseTemplate("{$each:{$sort:v1:true}}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:e[1]:d[2]:a";

        Assert.assertEquals(expect, actual);
    }
}
