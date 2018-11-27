package symbolTable;

import ast.Type.Type;
import ast.node.declaration.ClassDeclaration;
import ast.node.declaration.MethodDeclaration;
import ast.node.declaration.VarDeclaration;

import java.util.ArrayList;

public class ClassData {
    private ClassDeclaration declaration;
    private SymbolTable symbolTable;

    public ClassData(ClassDeclaration declaration){
        this.declaration = declaration;
        symbolTable = new SymbolTable();
        System.out.println("!");
        for(MethodDeclaration method : declaration.getMethodDeclarations()) {
            ArrayList<Type> args = new ArrayList<>();
            for(VarDeclaration var : method.getArgs())
                args.add(var.getType());
            try {
                symbolTable.put(new SymbolTableMethodItem(method.getName().getName(), args));
            } catch (ItemAlreadyExistsException e) {
                // System.out.println("item already exist");
            }
        }
        for (VarDeclaration var : declaration.getVarDeclarations()) {
            try {
                symbolTable.put(new SymbolTableVariableItem(var.getIdentifier().getName(), var.getType()));
            } catch (ItemAlreadyExistsException e) {
                // ?!?
            }
        }
        System.out.println("!");
    }

    public ClassDeclaration getDeclaration() {
        return declaration;
    }

    public void setDeclaration(ClassDeclaration declaration) {
        this.declaration = declaration;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}