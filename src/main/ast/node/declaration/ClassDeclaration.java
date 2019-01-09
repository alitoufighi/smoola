package ast.node.declaration;

import ast.Type.Type;
import ast.Visitor;
import ast.node.expression.Identifier;
import symbolTable.SymbolTable;

import java.util.ArrayList;

public class ClassDeclaration extends Declaration{
    private Identifier name;
    private Identifier parentName;
    private ArrayList<VarDeclaration> varDeclarations = new ArrayList<>();
    private ArrayList<MethodDeclaration> methodDeclarations = new ArrayList<>();
    private SymbolTable symbolTable;

	public SymbolTable getSymbolTable() {
		return symbolTable;
	}

	public void setSymbolTable(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	public ClassDeclaration(Identifier name, Identifier parentName) {
        this.name = name;
        this.parentName = parentName;
        this.lineNum = name.getLineNum();
    }

    public ArrayList<Type> getMethodArgsType(String methodName) throws MethodNotFoundException {
        ArrayList<Type> result = new ArrayList<>();
        for(MethodDeclaration methodDeclaration : methodDeclarations)
            if (methodDeclaration.getName().getName().equals(methodName)){
                ArrayList<VarDeclaration> args = methodDeclaration.getArgs();
                for(VarDeclaration arg : args)
                    result.add(arg.getType());
                return result;
            }
        throw new MethodNotFoundException();
    }

    public Type getMethodReturnType(String methodName) throws MethodNotFoundException{
        for(MethodDeclaration methodDeclaration : methodDeclarations)
            if (methodDeclaration.getName().getName().equals(methodName))
                return methodDeclaration.getReturnType();
        throw new MethodNotFoundException();
    }

    public Identifier getName() {
        return name;
    }

    public boolean hasParent() {
	    return parentName != null;
//        return !(name.getName().equals("Object"));
    }

    public boolean isObject() {
        return name.getName().equals("Object");
    }

    public void setName(Identifier name) {
        this.name = name;
    }

    public Identifier getParentName() {
        return parentName;
    }

    public void setParentName(Identifier parentName) {
        this.parentName = parentName;
    }

    public ArrayList<VarDeclaration> getVarDeclarations() {
        return varDeclarations;
    }

    public void addVarDeclaration(VarDeclaration varDeclaration) {
        this.varDeclarations.add(varDeclaration);
    }

    public ArrayList<MethodDeclaration> getMethodDeclarations() {
        return methodDeclarations;
    }

    public void addMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.methodDeclarations.add(methodDeclaration);
    }

    @Override
    public String toString() {
        return "ClassDeclaration";
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    // if we have parent, our parent's object string is our parent's name.
    // elsewhere, we are Object class and our parent is "java/lang/Object"
    public String getParentObjectString() {
        if(this.hasParent()){
            return parentName.getName();
        } else
            return "java/lang/Object";
    }
}
