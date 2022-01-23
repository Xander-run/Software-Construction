package expressivo;


/**
 * Represents Binary Operator, such as '+", '-', '*', '/'
 */
public abstract class BinaryOperatorExpression extends OperatorExpression {

    private final OperandExpression expression1, expression2;

    public BinaryOperatorExpression(OperandExpression exp1, OperandExpression exp2) {
        expression1 = exp1;
        expression2 = exp2;
    }

    public double doBinaryOperate(NumberOperandExpression num1, NumberOperandExpression num2) {
        throw new RuntimeException("unimplemented");
    }

    @Override
    public String toString() {
        throw new RuntimeException("unimplemented");
    }
}
