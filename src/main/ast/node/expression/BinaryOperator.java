package ast.node.expression;


import java.util.Arrays;

public class BinaryOperator{
    public enum OperatorTypes{
        add, sub, mult, div, and, or, eq, neq, lt, gt, assign
    }

    public static boolean isBooleanOperator(OperatorTypes operator){
        return Arrays.asList(OperatorTypes.eq, OperatorTypes.and, OperatorTypes.gt,
                OperatorTypes.lt, OperatorTypes.neq, OperatorTypes.or).contains(operator);
    }
}