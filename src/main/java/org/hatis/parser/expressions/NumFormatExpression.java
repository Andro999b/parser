package org.hatis.parser.expressions;

import org.hatis.parser.Context;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.IllegalFormatException;

public class NumFormatExpression extends Expression {

    public NumFormatExpression() {
        super(1, 2);
    }

    @Override
    public Object process(Context context) {
        Double value;
        NumberFormat nf;

        if(arguments.length > 2){
            value = getDoubleArgumentValue(context, arguments[1]);
            nf = new DecimalFormat(arguments[2]);
        }else{
            value = getDoubleArgumentValue(context, arguments[1]);
            nf = context.getParserConfig().getOutputNumberFormat();
        }

        String res;

        try {
            res = nf.format(value);
        }catch (IllegalFormatException e){
            throw getSyntaxError("Неверный формат числа", context);
        }

        return res;
    }
}
