package org.hatis.parser.expressions.cycles;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Andrey on 29.06.2015.
 */
public class SortTest {
    @Test
    public void testNullSort(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("v1", null);

        String actual = parser.parseTemplate("{$sort:v1}[{$index}]:{$item}{$end}", binding);
        String expect = "";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testSetSort(){
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add("d");
        set.add("a");
        set.add("e");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", set);

        String actual = parser.parseTemplate("{$sort:v1}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:a[1]:d[2]:e";

        Assert.assertEquals(expect, actual);
    }


    @Test
    public void testListSort() {
        CoreParser parser = new CoreParser();

        List<Object> arr = new ArrayList<>();
        arr.add("d");
        arr.add("a");
        arr.add("e");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", arr);

        String actual = parser.parseTemplate("{$sort:v1}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:a[1]:d[2]:e";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testArraySort(){
        CoreParser parser = new CoreParser();

        Object[] arr = new Object[]{"d", "a", "e"};

        DataBinding binding = new DataBinding();
        binding.setValue("v1", arr);

        String actual = parser.parseTemplate("{$sort:v1}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:a[1]:d[2]:e";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testMapSort(){
        CoreParser parser = new CoreParser();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("first", "d");
        map.put("third", "a");
        map.put("second", "e");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", map);

        String actual = parser.parseTemplate("{$sort:v1}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:a[1]:d[2]:e";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testMapSortByKey(){
        CoreParser parser = new CoreParser();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("b", "a");
        map.put("a", "d");
        map.put("d", "e");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", map);

        String actual = parser.parseTemplate("{$sbk:v1}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:d[1]:a[2]:e";

        Assert.assertEquals(expect, actual);
    }
}
