package ast.node.expression;

import ast.Visitor;

public class This extends Expression {
    public This(int lineNum) { this.lineNum = lineNum; }
    @Override
    public String toString() {
        return "This";
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
