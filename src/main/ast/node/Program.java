package ast.node;

import ast.Visitor;

import java.util.*;

import ast.node.declaration.ClassDeclaration;
import ast.node.declaration.MethodDeclaration;
import ast.node.declaration.VarDeclaration;
import symbolTable.SymbolTable;
import symbolTable.SymbolTableItem;
import symbolTable.ItemAlreadyExistsException;


public class Program {
    private static boolean valid = true;
    private static int tempVars = 0;
    public static int passNum = 1;
    private static LinkedHashMap<String, ClassDeclaration> classes = new LinkedHashMap<>();
    private ClassDeclaration mainClass;
    private static ArrayList<String> messages = new ArrayList<>();
    private static ArrayList<String> errors = new ArrayList<>();
    private static HashMap<String, SymbolTable> classesSymbolTable = new HashMap<>();

    public SymbolTable getSymbolTable(String className) { return classesSymbolTable.get(className); }

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
        if(!errors.contains(error))
            errors.add(error);
    }

    public static void addSymbolTableItems(SymbolTable symbolTable, ClassDeclaration current){
        ClassDeclaration parent = current;

        while(parent.hasParent()){

            parent = classes.get(parent.getParentName().getName());
            HashMap<String, SymbolTableItem> items = classesSymbolTable.get(parent.getName().getName()).getItems();
            for(Map.Entry<String, SymbolTableItem> entry : items.entrySet()){
                try{
                    symbolTable.put(entry.getValue());
                }
                catch (ItemAlreadyExistsException e){
                    String key = entry.getKey();
                    String[] tokens = key.split("@");
                    String name = tokens[0];
                    String type = tokens[1];
                    if(type.equals("var")){
                        for(VarDeclaration var : current.getVarDeclarations()) {
                            if (var.getIdentifier().getName().equals(name)) {
                                Program.invalidate();

                                Program.addError(
                                    "line:" + var.getLineNum() +
                                            ":Redefinition of variable " + name
                                );
                                break;
                            }
                        }
                    }
                    else if(type.equals("method")){
                        for(MethodDeclaration method : current.getMethodDeclarations()) {
                            if (method.getName().getName().equals(name)) {
                                Program.invalidate();

                                Program.addError(
                                    "line:" + method.getLineNum() +
                                            ":Redefinition of method " + name
                                );
                                break;
                            }
                        }
                    }
                }
            }
        }
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
        classes.put(classDeclaration.getName().getName(), classDeclaration);
    }

    public ArrayList<ClassDeclaration> getClasses() {
        ArrayList<ClassDeclaration> tempList = new ArrayList<>();
        for (String str : classes.keySet()) {
			tempList.add(classes.get(str));
        }

        return tempList;
    }

    @Override
    public String toString() {
        return "Program";
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }


}
