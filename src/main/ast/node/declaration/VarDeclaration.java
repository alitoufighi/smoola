package ast.node.declaration;

import ast.Type.Type;
import ast.VarVisitType;
import ast.Visitor;
import ast.node.expression.Identifier;

public class VarDeclaration extends Declaration {
    private Identifier identifier;
    private Type type;

    public VarDeclaration(Identifier identifier, Type type) {
        this.identifier = identifier;
        this.type = type;
        this.lineNum = identifier.getLineNum();
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VarDeclaration";
    }

//    @Override
    public void accept(Visitor visitor, VarVisitType visitType) {
        visitor.visit(this, visitType);
    }
}
