package org.hatis.parser.expressions.conditionals;

import org.hatis.parser.Context;
import org.apache.commons.lang3.StringUtils;

public class EqExpression extends ConditionExpression {

    public EqExpression() {
        super(1, 2);
    }

    protected boolean compare(Context context) {
        String what = arguments[1];
        String with = null;

        if (arguments.length > 2) {
            with = arguments[2];
        }

        if (what.isEmpty()) {
            throw getSyntaxError("Правая часть условия не может быть пустой", context);
        }

        if (StringUtils.isEmpty(with)) {
            return getBooleanArgumentValue(context, what);
        } else {
            Object whatVal = getArgumentValue(context, what);
            Object withVal = getArgumentValue(context, with);
            if (whatVal == null) {
                return withVal == null;
            }else{
                if(whatVal instanceof Number){
                    if(withVal instanceof Number) {
                        double n1 = ((Number) whatVal).doubleValue();
                        double n2 = ((Number) withVal).doubleValue();

                        return n1 == n2;
                    } else {
                        return false;
                    }
                }

                return whatVal.equals(withVal);
            }
        }
    }
}
