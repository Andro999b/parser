package org.hatis.parser.expressions.cycles;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Andrey on 28.06.2015.
 */
public class EachTest {
    @Test
    public void testNullEach(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("v1", null);

        String actual = parser.parseTemplate("{$each:v1}[{$index}]:{$item}{$end}", binding);
        String expect = "";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testSetEach(){
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add(1);
        set.add("test");
        set.add(true);

        DataBinding binding = new DataBinding();
        binding.setValue("v1", set);

        String actual = parser.parseTemplate("{$each:v1}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:1[1]:test[2]:true";

        Assert.assertEquals(expect, actual);
    }


    @Test
    public void testListEach() {
        CoreParser parser = new CoreParser();

        List<Object> arr = new ArrayList<>();
        arr.add(1);
        arr.add("test");
        arr.add(true);

        DataBinding binding = new DataBinding();
        binding.setValue("v1", arr);

        String actual = parser.parseTemplate("{$each:v1}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:1[1]:test[2]:true";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testArrayEach(){
        CoreParser parser = new CoreParser();

        Object[] arr = new Object[]{1, "test", true};

        DataBinding binding = new DataBinding();
        binding.setValue("v1", arr);

        String actual = parser.parseTemplate("{$each:v1}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:1[1]:test[2]:true";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testMapEach(){
        CoreParser parser = new CoreParser();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("first", 1);
        map.put("true", true);
        map.put("second", "second");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", map);

        String actual = parser.parseTemplate("{$each:v1}[{$index}>{$key}]:{$item}{$end}", binding);
        String expect = "[0>first]:1[1>true]:true[2>second]:second";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testBreakEach() {
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add(1);
        set.add("test");
        set.add(true);

        DataBinding binding = new DataBinding();
        binding.setValue("v1", set);

        String actual = parser.parseTemplate("{$each:v1}[{$index}]:{$item}{$break}{$end}", binding);
        String expect = "[0]:1";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNextEach(){
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add(1);
        set.add("test");
        set.add(true);

        DataBinding binding = new DataBinding();
        binding.setValue("v1", set);

        String actual = parser.parseTemplate("{$each:v1}[{$index}]:{$item}{$next}[{$index}]:{$item}{$end}", binding);
        String expect = "[0]:1[1]:test[2]:true";

        Assert.assertEquals(expect, actual);
    }
}
