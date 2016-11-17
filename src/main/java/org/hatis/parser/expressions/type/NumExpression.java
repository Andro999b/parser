package org.hatis.parser.expressions.type;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.Expression;

/**
 * Created by Andrey on 12.06.2015.
 */
public class NumExpression extends Expression {

    public NumExpression() {
        super(1, 2);
    }

    @Override
    public Object process(Context context) {

        Number value = getNumberArgumentValue(context, arguments[1]);

        if(arguments.length > 2){
            switch (arguments[2]){
                case "i": value = value.intValue(); break;
                case "l": value = value.longValue(); break;
                case "f": value = value.longValue(); break;
                case "d": value = value.longValue(); break;
            }
        }

        return value;
    }
}
