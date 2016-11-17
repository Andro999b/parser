package org.hatis.parser.expressions;

import org.hatis.parser.CoreParser;
import org.hatis.parser.data.ContextVariable;
import org.hatis.parser.data.CoreParserArguments;
import org.hatis.parser.data.accessor.DataBinding;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Andrey on 12.06.2015.
 */
public class VarsTest {

    @Test
    public void testContextVar(){
        CoreParser parser = new CoreParser();

        CoreParserArguments arguments = new CoreParserArguments();
        arguments.setVariable("test", (ContextVariable) context -> "Test Variable");

        String result = parser.parseTemplate("Test Var: {$out:$test}", arguments);
        String expect = "Test Var: Test Variable";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testVar(){
        CoreParser parser = new CoreParser();

        CoreParserArguments arguments = new CoreParserArguments();
        arguments.setVariable("test", "Test Variable");

        String result = parser.parseTemplate("Test Var: {$out:$test}", arguments);
        String expect = "Test Var: Test Variable";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testVarDeclaration(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("{$var:test:'Test Variable'}Test Var: {$out:$test}", new DataBinding());
        String expect = "Test Var: Test Variable";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testDataBindingReplace1(){
        CoreParser parser = new CoreParser();

        DataBinding dataAccessor = new DataBinding();
        dataAccessor.setValue("some_data.key", "Test Variable");

        String result = parser.parseTemplate("{$var:test:'key'}Test Var: {$out:some_data.$test}", dataAccessor);
        String expect = "Test Var: Test Variable";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testDataBindingReplace2(){
        CoreParser parser = new CoreParser();

        DataBinding dataAccessor = new DataBinding();
        dataAccessor.setValue("some_data.key", Arrays.asList(1, 2, 3));

        String result = parser.parseTemplate("{$var:test:'key'}{$var:i:1}Test Var: {$out:some_data.$test#$i}", dataAccessor);
        String expect = "Test Var: 2";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testVarDeclarationSubExpression(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("{$var:test:{$out:'Test Variable'}}Test Var: {$out:$test}", new DataBinding());
        String expect = "Test Var: Test Variable";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testVarDeclarationInlineExpression(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("{$var:test:$out:'Test Variable'}Test Var: {$out:$test}", new DataBinding());
        String expect = "Test Var: Test Variable";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testVarDelete(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("{$var:test:$out:'Test Variable'}{$del:test}Test Var: {$out:$test}", new DataBinding());
        String expect = "Test Var:";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testVarDirectPrint(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("{$var:test:$out:'Test Variable'}Test Var: {$test}", new DataBinding());
        String expect = "Test Var: Test Variable";

        Assert.assertEquals(expect, result);
    }

    @Test
    public void testGlobalVar(){
        CoreParser parser = new CoreParser();

        String result = parser.parseTemplate("{$if:true}{$setVar:test1:'test 1'}{$var:test2:'test 2'}{$end}{$out:$test1}{$out:$test2}", new DataBinding());
        String expect = "test 1";

        Assert.assertEquals(expect, result);
    }
}
