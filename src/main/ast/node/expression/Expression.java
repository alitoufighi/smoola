package ast.node.expression;

import ast.Type.Type;
import ast.Visitor;
import ast.node.Node;

public abstract class Expression extends Node{
    private Type type;
    private boolean lvalue;

    public void setLvalue(boolean lvalue) {
        this.lvalue = lvalue;
    }

    public boolean isLvalue() {
        return lvalue;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void accept(Visitor visitor) {}
}
