package ast.node;

import ast.Visitor;
import java.util.ArrayList;
import ast.node.declaration.ClassDeclaration;
import symbolTable.SymbolTable;

import java.util.HashMap;
import java.util.List;

public class Program {
    private static boolean valid = true;
    private static int tempVars = 0;
    private ArrayList<ClassDeclaration> classes = new ArrayList<>();
    private ClassDeclaration mainClass;
    private static ArrayList<String> messages = new ArrayList<>();
    private static ArrayList<String> errors = new ArrayList<>();
    private static HashMap<String, SymbolTable> classesSymbolTable = new HashMap<>();

    public static SymbolTable getClassSymbolTable(String className){
        return classesSymbolTable.get(className);
    }

    public static void addClassSymbolTable(String name, SymbolTable st){
        classesSymbolTable.put(name, st);
    }

    public static void addMessage(String msg){
        messages.add(msg);
    }

    public static void addError(String error){
        errors.add(error);
    }

    public void printErrors(){
        for(String error : errors)
            System.out.println(error);
    }

    public void printMessages(){
        for(String msg : messages)
            System.out.println(msg);
    }

    public static int getNewTempVar() {
        return ++tempVars;
    }

    public static void invalidate() { valid = false; }

    public boolean isValid() { return valid; }

    public ClassDeclaration getMainClass() {
        return mainClass;
    }

    public void setMainClass(ClassDeclaration mainClass) {
        this.mainClass = mainClass;
    }

    public void addClass(ClassDeclaration classDeclaration) {
        classes.add(classDeclaration);
    }

    public List<ClassDeclaration> getClasses() {
        return classes;
    }

    @Override
    public String toString() {
        return "Program";
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }


}
