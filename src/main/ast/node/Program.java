package ast.node;

import ast.Type.ArrayType.ArrayType;
import ast.Type.NoType;
import ast.Type.PrimitiveType.IntType;
import ast.Type.PrimitiveType.StringType;
import ast.Type.Type;
import ast.Visitor;
import ast.node.declaration.ClassDeclaration;
import ast.node.expression.Expression;
import symbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class Program {
    private static boolean phaseTwoValid = true;
    private static boolean phaseThreeValid = true;
    private static int tempVars = 0;
    public static int passNum = 1;
    private static LinkedHashMap<String, ClassDeclaration> classes = new LinkedHashMap<>();
    private static ClassDeclaration mainClass;
    private static ArrayList<String> messages = new ArrayList<>();
    private static ArrayList<String> errorsPhase2 = new ArrayList<>();
    private static ArrayList<String> errorsPhase3 = new ArrayList<>();
    private static HashMap<String, SymbolTable> classesSymbolTable = new HashMap<>();
    public static String currentClass;

    public static SymbolTable getClassSymbolTable(String className) {
        return classesSymbolTable.get(className);
    }

    public static void addClassSymbolTable(String name, SymbolTable st) {
        classesSymbolTable.put(name, st);
    }

    public static void addMessage(String msg) {
        messages.add(msg);
    }

    public static boolean isPrimitiveType(String typeName) {
        return typeName.equals("int") || typeName.equals("int[]") || typeName.equals("string") || typeName.equals("bool");
    }

    public static void addError(String error, PhaseNum phaseNum) {
        Program.invalidate(phaseNum);
        ArrayList<String> errors = (phaseNum == PhaseNum.two) ? errorsPhase2 : errorsPhase3;
        if (!errors.contains(error))
            errors.add(error);
    }

    public static boolean isMainClass(String name) {
        return mainClass.getName().getName().equals(name);
    }

    public void createClassSymbolTableHierarchy() {
//        HashMap<String, String> parentsList = new HashMap<>(); // to prevent circular inheritance!
        for (HashMap.Entry<String, SymbolTable> classTable : classesSymbolTable.entrySet()) {
            ClassDeclaration c;
            try {
                c = classes.get(classTable.getKey());
                String className = c.getName().getName();
                if (c.hasParent()) {
                    String parentName = classes.get(classTable.getKey()).getParentName().getName();
                    if(checkCircularInheritance(c)){
                        Program.addError(
                                "line:" + c.getLineNum() + ":circular inheritance found for classes " +
                                        className + " and " + parentName,
                                PhaseNum.three
                        );
                    } else {
//                        parentsList.put(className, parentName);
                        if (!parentName.equals(c.getName().getName()))
                            classTable.getValue().setPre(classesSymbolTable.get(parentName));
                    }
                } else if (!c.getName().getName().equals("Object")) {
                    classTable.getValue().setPre(classesSymbolTable.get(
                            classes.get("Object").getName().getName()
                    ));
                }

            } catch (Exception e) {
                return;
            }
        }
    }

//    public static void addSymbolTableItems(SymbolTable symbolTable, ClassDeclaration current){
//        ClassDeclaration parent = current;
//
//        while(parent.hasParent()){
//
//            if(!classes.containsKey(parent.getParentName().getName())){
//                Program.addError(
//                        "Line:" + parent.getLineNum() +
//                                ":class " + parent.getParentName().getName() + " is not declared"
//                        , PhaseNum.three);
//                break;
//            }
//
//            parent = classes.get(parent.getParentName().getName());
//            HashMap<String, SymbolTableItem> items = classesSymbolTable.get(parent.getName().getName()).getItems();
//            for(Map.Entry<String, SymbolTableItem> entry : items.entrySet()){
//                try{
//                    symbolTable.put(entry.getValue());
//                }
//                catch (ItemAlreadyExistsException e){
//                    String key = entry.getKey();
//                    String[] tokens = key.split("@");
//                    String name = tokens[0];
//                    String type = tokens[1];
//                    if(type.equals("var")){
//                        for(VarDeclaration var : current.getVarDeclarations()) {
//                            if (var.getIdentifier().getName().equals(name)) {
//                                Program.invalidate();
//
//                                Program.addError(
//                                    "Line:" + var.getLineNum() +
//                                            ":Redefinition of variable " + name
//                                    , PhaseNum.two);
//                                break;
//                            }
//                        }
//                    }
//                    else if(type.equals("method")){
//                        for(MethodDeclaration method : current.getMethodDeclarations())
//                            if (method.getName().getName().equals(name)) {
//                                Program.addError(
//                                        "Line:" + method.getLineNum() +
//                                                ":Redefinition of method " + name
//                                        , PhaseNum.two);
//                                break;
//                            }
//                    }
//                }
//            }
//        }
//    }

    public static boolean doesWritelnSupport(Expression argument) {
        Type type = argument.getType();
        return type instanceof StringType || type instanceof IntType || type instanceof ArrayType || type instanceof NoType;
    }

    public void printErrors(PhaseNum phaseNum) {
        ArrayList<String> errors = (phaseNum == PhaseNum.two) ? errorsPhase2 : errorsPhase3;
        errors.sort(Comparator.comparing(e -> Integer.valueOf(e.split(":")[1])));
        for (String error : errors)
            System.out.println(error);
    }

    public void printMessages() {
        for (String msg : messages)
            System.out.println(msg);
    }

    public static int getNewTempVar() {
        return ++tempVars;
    }

    private static void invalidate(PhaseNum phaseNum) {
        if (phaseNum == PhaseNum.two)
            phaseTwoValid = false;
        phaseThreeValid = false;
        // if phase 2 is invalid, then phase 3 is invalid, too!
    }

    public boolean isValid(PhaseNum phaseNum) {
        return phaseNum == PhaseNum.two ? phaseTwoValid : phaseThreeValid;
    }

    public ClassDeclaration getMainClass() {
        return mainClass;
    }

    public void setMainClass(ClassDeclaration newMainClass) {
        mainClass = newMainClass;
    }

    public void addClass(ClassDeclaration classDeclaration) {
        String className = classDeclaration.getName().getName();
        if (classes.containsKey(className)) {
            Program.addError(
                    "Line:" + classDeclaration.getLineNum() +
                            ":Redefinition of class " + className
                    , PhaseNum.two);
            className = "temp" + Program.getNewTempVar() +
                    "-" + className;

        }
        classes.put(className, classDeclaration);
    }

    public static ClassDeclaration getClass(String name) throws Exception {
        if (!classes.containsKey(name))
            throw new Exception();
        return classes.get(name);
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


    private boolean checkCircularInheritance(ClassDeclaration c) {
        String className = c.getName().getName();
        ArrayList<String> parentsList = new ArrayList<>();
        if (!className.equals("Object")) {
            String parentName = c.getParentName().getName();
            while (!parentName.equals("Object")) {
                parentsList.add(parentName);
                ClassDeclaration parent = classes.get(parentName);
                parentName = parent.getParentName().getName();
                if (parentsList.contains(parentName))
                    return true;
            }

        }
        return false;
    }

}
