package ast.node.expression;

import ast.Visitor;

public class BinaryExpression extends Expression {

    private Expression left;
    private Expression right;
    private BinaryOperator.OperatorTypes binaryOperator;

    public BinaryExpression(Expression left, Expression right, BinaryOperator.OperatorTypes binaryOperator) {
        this.left = left;
        this.right = right;
        this.binaryOperator = binaryOperator;
        this.lineNum = left.getLineNum();
    }

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public BinaryOperator.OperatorTypes getBinaryOperator() {
        return binaryOperator;
    }

    public void setBinaryOperator(BinaryOperator.OperatorTypes binaryOperator) {
        this.binaryOperator = binaryOperator;
    }

    @Override
    public String toString() {
        return "BinaryExpression " + binaryOperator.name();
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
