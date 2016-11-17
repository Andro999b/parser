package org.hatis.parser.expressions.types;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by Andrey on 18.06.2015.
 */
public class BoolTest {

    @Test
    public void testNull(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("obj", null);

        String actual = parser.parseTemplate("{$bool:obj}", binding);
        String expect = "false";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testTrue(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("obj", true);

        String actual = parser.parseTemplate("{$bool:obj}", binding);
        String expect = "true";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testFalse(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("obj", false);

        String actual = parser.parseTemplate("{$bool:obj}", binding);
        String expect = "false";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testEmptyString(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("obj", "");

        String actual = parser.parseTemplate("{$bool:obj}", binding);
        String expect = "false";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNonEmptyString(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("obj", "test string");

        String actual = parser.parseTemplate("{$bool:obj}", binding);
        String expect = "true";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testEmptyMap(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("value", new HashMap<>());

        String actual = parser.parseTemplate("{$bool:value}", binding);
        String expect = "false";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNonEmptyMap(){
        CoreParser parser = new CoreParser();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("key", "value");

        DataBinding binding = new DataBinding();
        binding.setValue("value", hashMap);

        String actual = parser.parseTemplate("{$bool:value}", binding);
        String expect = "true";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testEmptyCollection(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("value", new ArrayList<>());

        String actual = parser.parseTemplate("{$bool:value}", binding);
        String expect = "false";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNonEmptyCollection(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("value", Collections.singletonList("value"));

        String actual = parser.parseTemplate("{$bool:value}", binding);
        String expect = "true";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testEmptyArray(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("value", new String[]{});

        String actual = parser.parseTemplate("{$bool:value}", binding);
        String expect = "false";

        Assert.assertEquals(expect, actual);
    }

    @Test
    public void testNonEmptyArray(){
        CoreParser parser = new CoreParser();

        DataBinding binding = new DataBinding();
        binding.setValue("value", new String[]{"test string"});

        String actual = parser.parseTemplate("{$bool:value}", binding);
        String expect = "true";

        Assert.assertEquals(expect, actual);
    }
}
