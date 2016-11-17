package org.hatis.parser.analyzers;

import org.hatis.parser.expressions.*;
import org.hatis.parser.expressions.collections.*;
import org.hatis.parser.expressions.conditionals.*;
import org.hatis.parser.expressions.conditionals.numbers.*;
import org.hatis.parser.expressions.cycles.BreakExpression;
import org.hatis.parser.expressions.cycles.EachExpression;
import org.hatis.parser.expressions.cycles.NextExpression;
import org.hatis.parser.expressions.cycles.chain.*;
import org.hatis.parser.expressions.math.CalcExpression;
import org.hatis.parser.expressions.math.MathExpression;
import org.hatis.parser.expressions.math.RandExpression;
import org.hatis.parser.expressions.strings.ConcatExpression;
import org.hatis.parser.expressions.strings.LowerCaseExpression;
import org.hatis.parser.expressions.strings.SplitExpression;
import org.hatis.parser.expressions.strings.UpperCaseExpression;
import org.hatis.parser.expressions.type.BoolExpression;
import org.hatis.parser.expressions.type.NumExpression;
import org.hatis.parser.expressions.type.ToStringExpression;
import org.hatis.parser.expressions.type.TypeExpression;

/**
 *
 * Created by Andrey on 01.06.2015.
 */
public class CoreAnalyzer extends TokensAnalyzer {

    private void fillMap(){
        //vars
        suppliers.put("$var", VarExpression::new);
        suppliers.put("$set", SetVarExpression::new);
        suppliers.put("$setVar", SetVarExpression::new);
        suppliers.put("$default", DefaultVarExpression::new);
        suppliers.put("$del", DelExpression::new);

        //type conversion
        suppliers.put("$num", NumExpression::new);
        suppliers.put("$str", ToStringExpression::new);
        suppliers.put("$bool", BoolExpression::new);

        //conditions
        suppliers.put("$eq", EqExpression::new);
        suppliers.put("$if", EqExpression::new);//alice for equals
        suppliers.put("$not", NotExpression::new);
        suppliers.put("$or", OrExpression::new);
        suppliers.put("$and", AndExpression::new);
        suppliers.put("$else", ElseExpression::new);

        //num conditions
        suppliers.put("$gt", GTExpression::new);
        suppliers.put("$gte", GTEExpression::new);
        suppliers.put("$lt", LTExpression::new);
        suppliers.put("$lte", LTEExpression::new);
        suppliers.put("$bt", BTExpression::new);

        //cycle
        suppliers.put("$each", EachExpression::new);
        suppliers.put("$break", BreakExpression::new);
        suppliers.put("$next", NextExpression::new);

        //chains
        suppliers.put("$join", JoinExpression::new);
        suppliers.put("$sort", SortExpression::new);
        suppliers.put("$sortByKey", SortByKeyExpression::new);
        suppliers.put("$sbk", SortByKeyExpression::new);
        suppliers.put("$range", RangeExpression::new);
        suppliers.put("$map", MapExpression::new);
        suppliers.put("$filter", FilterExpression::new);
        suppliers.put("$reverse", ReverseExpression::new);

        //math
        suppliers.put("$calc", CalcExpression::new);
        suppliers.put("$math", MathExpression::new);
        suppliers.put("$rand", RandExpression::new);

        //collection
        suppliers.put("$arr", ArrExpression::new);
        suppliers.put("$hashSet", HashSetExpression::new);
        suppliers.put("$hashMap", HashMapExpression::new);
        suppliers.put("$size", SizeExpression::new);
        suppliers.put("$contains", ContainsExpression::new);

        //common
        suppliers.put("$numFormat", NumFormatExpression::new);
        suppliers.put("$out", OutExpression::new);
        suppliers.put("$type", TypeExpression::new);
        suppliers.put("$end", EndExpression::new);

        //strings
        suppliers.put("$lower", LowerCaseExpression::new);
        suppliers.put("$upper", UpperCaseExpression::new);
        suppliers.put("$split", SplitExpression::new);
        suppliers.put("$concat", ConcatExpression::new);
    }

    public CoreAnalyzer() {
        fillMap();
    }

    public CoreAnalyzer(boolean printUnknown){
        super(printUnknown);
        fillMap();
    }
}
