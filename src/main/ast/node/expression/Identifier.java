package ast.node.expression;

import ast.Visitor;

public class Identifier extends Expression {
    private String name;

    public Identifier(String name, int lineNum) {
        this.name = name;
        this.lineNum = lineNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Identifier " + name;
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void accept(Visitor visitor, int mode){
        visitor.visit(this, mode);
    }
}
