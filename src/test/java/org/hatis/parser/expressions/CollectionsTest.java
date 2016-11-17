package org.hatis.parser.expressions;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Andrey on 21.06.2015.
 */
public class CollectionsTest {

    @Test
    public void testArr(){
        CoreParser parser = new CoreParser();

        String actual = parser.parseTemplate("{$arr:v1:1:'test':true}{$v1}", new DataBinding());
        String expect = Arrays.asList(1, "test", true).toString();

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testSet(){
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add(1);
        set.add("test");
        set.add(true);

        String actual = parser.parseTemplate("{$hashSet:v1:1:'test':'test':true}{$v1}", new DataBinding());
        String expect = set.toString();

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testMap(){
        CoreParser parser = new CoreParser();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("first", 1);
        map.put("true", true);
        map.put("second", "second");

        String template = "{$hashMap:v1:'first':1}{$hashMap:v1:true:true}{$hashMap:v1:'second':2}{$hashMap:v1:'second':'second'}{$v1}";
        String actual = parser.parseTemplate(template, new DataBinding());
        String expect = map.toString();

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testListSize(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("v1", Arrays.asList(1, "test", true));

        String actual = parser.parseTemplate("{$size:v1}", binding);
        String expect = "3";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testArrSize(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("v1", new Object[]{1, "test", true});

        String actual = parser.parseTemplate("{$size:v1}", binding);
        String expect = "3";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testSetSize(){
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add(1);
        set.add("test");
        set.add(true);

        DataBinding binding = new DataBinding();
        binding.setValue("v1", set);

        String actual = parser.parseTemplate("{$size:v1}", binding);
        String expect = "3";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testStringSize(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("v1", "abc");

        String actual = parser.parseTemplate("{$size:v1}", binding);
        String expect = "3";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testMapSize(){
        CoreParser parser = new CoreParser();

        Map<String, Object> map = new HashMap<>();
        map.put("first", 1);
        map.put("true", true);
        map.put("second", "second");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", map);

        String actual = parser.parseTemplate("{$size:v1}", binding);
        String expect = "3";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNullContains(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("v1", null);

        String actual = parser.parseTemplate("{$contains:v1:{$num:1:i}}true{$end}", binding);
        String expect = "";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testListContains(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("v1", Arrays.asList(1, "test", true));

        String actual = parser.parseTemplate("{$contains:v1:{$num:1:i}}true{$end}", binding);
        String expect = "true";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testArrContains(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("v1", new Object[]{1, "test", true});

        String actual = parser.parseTemplate("{$contains:v1:'test'}true{$end}", binding);
        String expect = "true";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testSetContains(){
        CoreParser parser = new CoreParser();

        Set<Object> set = new LinkedHashSet<>();
        set.add(1);
        set.add("test");
        set.add(true);

        DataBinding binding = new DataBinding();
        binding.setValue("v1", set);

        String actual = parser.parseTemplate("{$contains:v1:true}true{$end}", binding);
        String expect = "true";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testMapContains(){
        CoreParser parser = new CoreParser();

        Map<String, Object> map = new HashMap<>();
        map.put("first", 1);
        map.put("true", true);
        map.put("second", "second");

        DataBinding binding = new DataBinding();
        binding.setValue("v1", map);

        String actual = parser.parseTemplate("{$contains:v1:{$num:1:i}}true{$end}", binding);
        String expect = "true";

        Assert.assertEquals(expect, actual);
    }
}
