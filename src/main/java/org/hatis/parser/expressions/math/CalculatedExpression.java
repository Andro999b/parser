package org.hatis.parser.expressions.math;

import org.hatis.parser.Context;
import org.hatis.parser.expressions.Expression;

abstract public class CalculatedExpression extends Expression {
    public CalculatedExpression() {
    }

    public CalculatedExpression(int minArguments, int maxArguments) {
        super(minArguments, maxArguments);
    }

    protected double getCalculatedValue(Context context, String argument) {
        Calculator calculator = new Calculator(context);
        return calculator.calc(argument);
    }

    private class Calculator {
        private int pos;
        private Context context;
        private String mathExpr;
        private Character op;
        private double value;

        private Calculator(Context context) {
            this.context = context;
        }

        private void throwCalcError(String msg) {
            throw CalculatedExpression.this.getSyntaxError("Ощибка математического выражения: " + msg, context);
        }

        private double sumToken() {
            double result = mulToken();
            double tmp;

            while (op != null) {
                if (op.equals('+')) {
                    getToken();
                    tmp = mulToken();
                    result += tmp;
                } else if (op.equals('-')) {
                    getToken();
                    tmp = mulToken();
                    result -= tmp;
                } else {
                    break;
                }
            }

            return result;
        }

        private double mulToken() {
            double result = expToken();
            double tmp;

            while (op != null) {
                if (op.equals('*')) {
                    getToken();
                    tmp = expToken();
                    result *= tmp;
                } else if (op.equals('/')) {
                    getToken();
                    tmp = expToken();

                    if (tmp == 0.0) {
                        throwCalcError("Деление на ноль");
                    }

                    result /= tmp;
                } else if (op.equals('%')) {
                    getToken();
                    tmp = expToken();
                    result = result % tmp;
                } else {
                    break;
                }
            }

            return result;
        }

        private double expToken() {
            double result = unarToken();
            double tmp;

            while (op != null) {
                if (op.equals('^')) {
                    getToken();
                    tmp = unarToken();
                    result = Math.pow(result, tmp);
                } else {
                    break;
                }
            }

            return result;
        }

        private double unarToken() {
            double result;
            boolean negative = false;

            if (op != null) {
                if (op.equals('+')) {
                    getToken();
                } else if (op.equals('-')) {
                    getToken();
                    negative = true;
                }
            }

            result = braceToken();

            if (negative)
                result = -result;

            return result;
        }

        private double braceToken() {
            double result = 0;

            if (op != null) {
                if (op.equals('(')) {
                    getToken();
                    result = sumToken();

                    if (op == null || !op.equals(')')) {
                        throwCalcError("Ожидалась закрывающая скобка");
                    }

                    getToken();
                }
            } else {
                result = atomToken();
            }

            return result;
        }

        private double atomToken() {
            double result = 0;

            if (op == null) {
                result = value;
                getToken();
            } else {
                throwCalcError("Ожидалось число");
            }

            return result;
        }

        private void getToken() {
            op = null;

            if (pos >= mathExpr.length())
                return;

            char ch = mathExpr.charAt(pos);

            while (ch == ' ') {
                pos++;
                ch = mathExpr.charAt(pos);
            }

            if (isOp(ch)) {
                op = ch;
                pos++;
            } else {
                StringBuilder sb = new StringBuilder();

                int curlyBrackets = 0;

                while (true) {
                    ch = mathExpr.charAt(pos);
                    sb.append(ch);

                    if (ch == '}') {
                        curlyBrackets--;
                    }else if (ch == '{') {
                        curlyBrackets++;
                    }

                    pos++;

                    //test end
                    if (pos >= mathExpr.length())
                        break;

                    ch = mathExpr.charAt(pos);

                    if (curlyBrackets == 0) {
                        if (isOp(ch) || ch == ' ')
                            break;
                    }
                }

                value = getDoubleArgumentValue(context, sb.toString());
            }
        }

        private boolean isOp(char ch) {
            switch (ch) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '%':
                case '(':
                case ')':
                    return true;
                default:
                    return false;
            }
        }

        public double calc(String mathExpr) {
            this.pos = 0;
            this.mathExpr = mathExpr;

            getToken();

            return sumToken();
        }
    }

}
