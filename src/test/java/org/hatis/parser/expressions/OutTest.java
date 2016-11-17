package org.hatis.parser.expressions;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Andrey on 11.06.2015.
 */
public class OutTest {
    @Test
    public void testStringOut(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("Test out: {$out:'test value'}", new DataBinding());
        String expect = "Test out: test value";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testIntOut(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("Test out: {$out:123,567}", new DataBinding());
        String expect = "Test out: 123567";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testFloatOut(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("Test out: {$out:1.23}", new DataBinding());
        String expect = "Test out: 1.23";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testNegativeFloatOut(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("Test out: {$out:-1.23}", new DataBinding());
        String expect = "Test out: -1.23";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testTrueOut(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("Test out: {$out:false}", new DataBinding());
        String expect = "Test out: false";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testFalseOut(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("Test out: {$out:false}", new DataBinding());
        String expect = "Test out: false";

        Assert.assertEquals(expect, result);
    }

    @Test
     public void testStringFromDataOut(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", "test value");

        String result = parser.parseTemplate("Test out: {$out:test_key}", binding);
        String expect = "Test out: test value";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testCollectionFromDataOut(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", Arrays.asList(1, 2, 3));

        String result = parser.parseTemplate("Test out: {$out:test_key}", binding);
        String expect = "Test out: [1, 2, 3]";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testMapFromDataOut(){
        CoreParser parser = new CoreParser();

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", map);

        String result = parser.parseTemplate("Test out: {$out:test_key}", binding);
        String expect = "Test out: {one=1, two=2}";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testFromListByIndex(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", Arrays.asList(1, 4, 3));

        String result = parser.parseTemplate("Test out: {$out:test_key#1}", binding);
        String expect = "Test out: 4";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testFromArrayByIndex(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", new Integer[]{1, 4, 3});

        String result = parser.parseTemplate("Test out: {$out:test_key#1}", binding);
        String expect = "Test out: 4";

        Assert.assertEquals(expect, result);
    }


    @Test
    public void testFromSetByIndex(){
        CoreParser parser = new CoreParser();

        Set<Integer> set = new LinkedHashSet<>();
        set.add(1);
        set.add(3);

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", set);

        String result = parser.parseTemplate("Test out: {$out:test_key#1}", binding);
        String expect = "Test out: 3";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testSetSize(){
        CoreParser parser = new CoreParser();

        Set<Integer> set = new LinkedHashSet<>();
        set.add(1);
        set.add(2);

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", set);

        String result = parser.parseTemplate("Test out: {$out:test_key#}", binding);
        String expect = "Test out: 2";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testFromMapByKey(){
        CoreParser parser = new CoreParser();

        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", map);

        String result = parser.parseTemplate("Test out: {$out:test_key#'two'}", binding);
        String expect = "Test out: 2";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testFromMapByKeyFromData(){
        CoreParser parser = new CoreParser();

        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);

        DataBinding binding = new DataBinding();
        binding.setValue("test_map", map);
        binding.setValue("test_key", "two");

        String result = parser.parseTemplate("Test out: {$out:test_map#test_key}", binding);
        String expect = "Test out: 2";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testSizeOfList(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", Arrays.asList(1, 2, 3));

        String result = parser.parseTemplate("Test out: {$out:test_key#}", binding);
        String expect = "Test out: 3";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testSizeOfArray(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", new Integer[]{1, 2, 3});

        String result = parser.parseTemplate("Test out: {$out:test_key#}", binding);
        String expect = "Test out: 3";

        Assert.assertEquals(expect, result);
    }


    @Test
    public void testSizeOfMap(){
        CoreParser parser = new CoreParser();

        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);

        DataBinding binding = new DataBinding();
        binding.setValue("test_key", map);

        String result = parser.parseTemplate("Test out: {$out:test_key#}", binding);
        String expect = "Test out: 2";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testDotAccess1(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("one.two", 1);

        String result = parser.parseTemplate("Test out: {$out:one.two}", binding);
        String expect = "Test out: 1";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testDotAccess2(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue(new String[]{"one","two"}, 1);

        String result = parser.parseTemplate("Test out: {$out:one.two}", binding);
        String expect = "Test out: 1";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testDotAccessWithIndex(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue(new String[]{"one","two"}, new Integer[]{1, 2, 3});

        String result = parser.parseTemplate("Test out: {$out:one.two#1}", binding);
        String expect = "Test out: 2";

        Assert.assertEquals(expect, result);
    }
}
