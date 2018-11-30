package ast.Type.UserDefinedType;

import ast.Type.Type;
import ast.node.declaration.ClassDeclaration;
import ast.node.expression.Identifier;

public class UserDefinedType extends Type {
    private ClassDeclaration classDeclaration;

    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }

    public void setClassDeclaration(ClassDeclaration classDeclaration) {
        this.classDeclaration = classDeclaration;
    }

    public Identifier getName() {
        return name;
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public UserDefinedType(Identifier name) {
        this.name = name;
        this.classDeclaration = null;
    }

    private Identifier name; // assumption: `name` is name of the type (which equals classDeclaration.getName() )

    @Override
    public String toString() {
        return classDeclaration.getName().getName();
    }
}
