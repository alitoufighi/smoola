package symbolTable;

import ast.Type.Type;
import ast.node.declaration.ClassDeclaration;

public class SymbolTableVariableItem extends SymbolTableItem {
    static private int indexCounter = 1;
    private int index;
    private ClassDeclaration classDeclaration;
    protected Type type;

    public String getFieldDescriptorCode() {
        return classDeclaration.getName().getName() + "/" + name + " " + type.getCodeString();
    }

    public SymbolTableVariableItem(String name, Type type) {
        this.name = name;
        this.type = type;
        this.classDeclaration = null;
        this.index = indexCounter++;
    }

    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }

    public void setClassDeclaration(ClassDeclaration classDeclaration) {
        this.classDeclaration = classDeclaration;
    }

    public SymbolTableVariableItem(String name, Type type, ClassDeclaration classDeclaration) {
        this.name = name;
        this.type = type;
        this.classDeclaration = classDeclaration;
        this.index = indexCounter++;
    }
    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String getKey() {
        return name.concat("@").concat("var");
    }

    public int getIndex() {
        return index;
    }

    public static void resetIndex() { indexCounter = 1; }

    public String getFieldInitializationCode() {
        return name + " " + type.getCodeString();
    }
}