package ast;

import ast.Type.ArrayType.ArrayType;
import ast.Type.PrimitiveType.BooleanType;
import ast.Type.PrimitiveType.IntType;
import ast.Type.PrimitiveType.StringType;
import ast.Type.Type;
import ast.Type.UserDefinedType.UserDefinedType;
import ast.node.Program;
import ast.node.declaration.ClassDeclaration;
import ast.node.declaration.MethodDeclaration;
import ast.node.declaration.VarDeclaration;
import ast.node.expression.*;
import ast.node.expression.Value.BooleanValue;
import ast.node.expression.Value.IntValue;
import ast.node.expression.Value.StringValue;
import ast.node.statement.*;
import symbolTable.SymbolTable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CodeGeneratorVisitorImpl implements Visitor {
    private String getJasminFileName() {
        return Program.currentClass.concat(".j");
    }

    private int indent = 0;
    private int labelCount = 0;

    private String getLabel() {
        return "LABEL" + labelCount++;
    }

    private String getMethodDescriptorCode(String className, String methodName){
        StringBuilder result = new StringBuilder();
        try{
            ClassDeclaration classDeclaration = Program.getClass(className);
            ArrayList<Type> args = classDeclaration.getMethodArgsType(methodName);
            Type returnType = classDeclaration.getMethodReturnType(methodName);
            result.append("(");
            for(Type arg : args)
                result.append(arg.getCodeString());
            result.append(")");
            result.append(returnType.getCodeString());
            return result.toString();
        } catch (Exception e){
            System.out.println("Class or Method Not Found!");
            return "";
        }

    }

    private void addInstruction(String instruction){
        try{
            Files.write(Paths.get(getJasminFileName()),
                    (getIndent() + instruction + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e){
            System.out.println("Exception occurred.");
        }
    }

    private void generateCodeAccordingToType(Type type, String iInstruction, String aInstruction){
        if(type instanceof UserDefinedType ||
                type instanceof StringType ||
                type instanceof ArrayType) {
            addInstruction(aInstruction);
        } else if(type instanceof IntType ||
                type instanceof BooleanType) {
            addInstruction(iInstruction);
        } else {
            System.out.println("Unknown type : " + type.toString());
        }
    }

    private void generateReturnCode(MethodDeclaration methodDeclaration) {
        methodDeclaration.getReturnValue().accept(this);

        if(methodDeclaration.isMainMethod()){
            addInstruction("pop");
            addInstruction("return");
            return;
        }


        Type returnType = methodDeclaration.getReturnType();
        generateCodeAccordingToType(returnType, "ireturn", "areturn");
    }

    private void generateLimitsCode() {
        addInstruction(".limit locals 50");
        addInstruction(".limit stack 50");
    }

    private void generateConstructorCode(ClassDeclaration classDeclaration) {
        addInstruction(".method public <init>()V");
        incIndent();
        generateLimitsCode();
        addInstruction("aload_0");
        addInstruction("invokespecial " + classDeclaration.getParentObjectString() + "/<init>()V");
        for(VarDeclaration varDeclaration : classDeclaration.getVarDeclarations())
            varDeclaration.accept(this, classDeclaration.getName().getName());
        addInstruction("return");
        decIndent();
        addInstruction(".end method\n");
    }

    private String getIndent() {
        return IntStream.range(0, indent).mapToObj(i -> "\t").collect(Collectors.joining());
    }

    private void incIndent() { indent++; }
    private void decIndent() { indent--; }

    @Override
    public void visit(Program program) {
        Program.cleanJasminFiles();

        program.getMainClass().accept(this);
        for (ClassDeclaration classDeclaration : program.getClasses())
            classDeclaration.accept(this);
    }

    @Override
    public void visit(ClassDeclaration classDeclaration) {
        Program.currentClass = classDeclaration.getName().getName();
        SymbolTable.push(classDeclaration.getSymbolTable());

        addInstruction(".class public "+classDeclaration.getName().getName());
        addInstruction(".super "+classDeclaration.getParentObjectString());

        for (VarDeclaration varDeclaration : classDeclaration.getVarDeclarations())
            addInstruction(".field " + SymbolTable.getFieldInitializationCode(varDeclaration.getIdentifier().getName()));

        generateConstructorCode(classDeclaration);


        for(MethodDeclaration methodDeclaration : classDeclaration.getMethodDeclarations())
            methodDeclaration.accept(this);
    }


    @Override
    public void visit(MethodDeclaration methodDeclaration) {
        if(methodDeclaration.isMainMethod())
            addInstruction(".method public static main([Ljava/lang/String;)V");
        else
            addInstruction(".method public "+methodDeclaration.getName().getName()+
                    "("+methodDeclaration.getArgsCodeString()+")"+methodDeclaration.getReturnTypeCodeString());

        SymbolTable.push(methodDeclaration.getSymbolTable());

        incIndent();
        generateLimitsCode();
        for(VarDeclaration varDeclaration : methodDeclaration.getLocalVars())
            varDeclaration.accept(this, VarVisitType.InMethod); // clean this mess
        for(Statement stm : methodDeclaration.getBody())
            stm.accept(this);
        generateReturnCode(methodDeclaration);
        decIndent();
        addInstruction(".end method\n");
    }

    @Override
    public void visit(VarDeclaration varDeclaration, String className) {
        Type varType = varDeclaration.getType();
        if(!(varType instanceof IntType || varType instanceof BooleanType || varType instanceof StringType))
            return;
        String fieldSpec = className + "/" + varDeclaration.getIdentifier().getName();
        String descriptor = varDeclaration.getTypeCodeString();
        addInstruction("aload_0");
        if(varType instanceof IntType || varType instanceof BooleanType)
            addInstruction("iconst_0");
        else
            addInstruction("ldc \"\"");
        addInstruction("putfield " + fieldSpec + " " + descriptor);
    }

    @Override
    public void visit(VarDeclaration varDeclaration, VarVisitType visitType) {
        Type varType = varDeclaration.getType();
        int varIndex = SymbolTable.getIndex(varDeclaration.getIdentifier());
        if(varType instanceof IntType || varType instanceof BooleanType){
            addInstruction("iconst_0");
            addInstruction("istore " + varIndex);
        } else if(varType instanceof StringType){
            addInstruction("ldc " + "\"\"");
            addInstruction("astore " + varIndex);
        }

    }

    @Override
    public void visit(ArrayCall arrayCall) {
        arrayCall.getInstance().accept(this);
        arrayCall.getIndex().accept(this);
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        Expression left = binaryExpression.getLeft();
        Expression right = binaryExpression.getRight();
        String labelFalse = "", labelTrue = ""; //TODO: CLEAN IT
        if(!(binaryExpression.getBinaryOperator().equals(BinaryOperator.OperatorTypes.or) ||
                binaryExpression.getBinaryOperator().equals(BinaryOperator.OperatorTypes.and))){
            if(!(binaryExpression.getBinaryOperator().equals(BinaryOperator.OperatorTypes.assign) &&
                    !(binaryExpression.getLeft() instanceof ArrayCall)))
                binaryExpression.getLeft().accept(this);
            if(left instanceof ArrayCall && !binaryExpression.getBinaryOperator().equals(BinaryOperator.OperatorTypes.assign))
                addInstruction("iaload");

            if(binaryExpression.getBinaryOperator().equals(BinaryOperator.OperatorTypes.assign) &&
                    SymbolTable.isClassField(left) || (left instanceof ArrayCall && SymbolTable.isClassField(((ArrayCall) left).getInstance())))
                addInstruction("aload_0");
            binaryExpression.getRight().accept(this);
        } else {
            labelFalse = getLabel();
            labelTrue = getLabel();
        }


        if(right instanceof ArrayCall)
            addInstruction("iaload");
        switch (binaryExpression.getBinaryOperator()) {
            case mult:
                addInstruction("imul");
                break;
            case add:
                addInstruction("iadd");
                break;
            case assign:
                if(left instanceof ArrayCall)
                    addInstruction("dup_x2");
                else
                    addInstruction("dup");
                if(left instanceof Identifier){
                    int index = SymbolTable.getIndex((Identifier) left);
                    if(SymbolTable.isClassField(left)) {
						addInstruction("putfield " + SymbolTable.getFieldDescriptorCode(((Identifier)left).getName()));
					}
                    else
                        generateCodeAccordingToType(binaryExpression.getLeft().getType(),
                                "istore " + index, "astore " + index);
                } else if (left instanceof ArrayCall){
                    if(SymbolTable.isClassField(((ArrayCall) left).getInstance())) {
						addInstruction("putfield " + SymbolTable.getFieldDescriptorCode(((Identifier) ((ArrayCall) left).getInstance()).getName()));
                    }
					else
                        addInstruction("iastore");
                }
                else
                    System.out.println("Unexpected Error in Binary Expression");
                break;
            case eq: case lt: case gt: case neq:
                generateBooleanBinaryExpressionCode(binaryExpression.getBinaryOperator());
                break;
            case div:
                addInstruction("idiv");
                break;
            case sub:
                addInstruction("isub");
                break;
            case or:
                String labelStore = getLabel();
                binaryExpression.getLeft().accept(this);
                addInstruction("ifne " + labelTrue);
                binaryExpression.getRight().accept(this);
                addInstruction("ifeq " + labelFalse);
                addInstruction(labelTrue + ":");
                addInstruction("iconst_1");
                addInstruction("goto " + labelStore);
                addInstruction(labelFalse + ":");
                addInstruction("iconst_0");
                addInstruction(labelStore + ":");
                break;
            case and:
                binaryExpression.getLeft().accept(this);
                addInstruction("ifeq " + labelFalse);
                binaryExpression.getRight().accept(this);
                addInstruction("ifeq " + labelFalse);
                addInstruction("iconst_1");
                addInstruction("goto " + labelTrue);
                addInstruction(labelFalse + ":");
                addInstruction("iconst_0");
                addInstruction(labelTrue + ":");
                break;
        }
    }

    private void generateBooleanBinaryExpressionCode(BinaryOperator.OperatorTypes binaryOperator) {
        String jumpCode;
        switch (binaryOperator){
            case eq:
                jumpCode = "if_icmpne";
                break;
            case neq:
                jumpCode = "if_icmpeq";
                break;
            case lt:
                jumpCode = "if_icmpge";
                break;
            case gt:
                jumpCode = "if_icmple";
                break;
            default:
                jumpCode = "";
                break;
        }
        String label1 = getLabel();
        String label2 = getLabel();
        addInstruction(jumpCode + " " + label1);
        addInstruction("iconst_1");
        addInstruction("goto " + label2);
        addInstruction(label1 + ":");
        addInstruction("iconst_0");
        addInstruction(label2 + ":");
    }

    @Override
    public void visit(Identifier identifier) {
    	if(!SymbolTable.isClassField(identifier)) {
			int index = SymbolTable.getIndex(identifier);
			generateCodeAccordingToType(
					identifier.getType(), "iload " + index, "aload " + index);
		}
		else {
			addInstruction("aload_0");
            addInstruction("getfield " + SymbolTable.getFieldDescriptorCode(identifier.getName()));
		}
    }

    @Override
    public void visit(Identifier identifier, int type) {
        // not called
    }

    @Override
    public void visit(Length length) {
        length.getExpression().accept(this);
        addInstruction("arraylength");
    }

    @Override
    public void visit(MethodCall methodCall) {
        String methodName = methodCall.getMethodName().getName();
        String instanceName = methodCall.getInstance().getType().toString();
        String methodDescriptor = instanceName + "/" + methodName + getMethodDescriptorCode(instanceName, methodName);
        methodCallCodeGenerator(methodCall.getInstance(), methodCall.getArgs(), methodDescriptor);
    }

    @Override
    public void visit(NewArray newArray) {
        newArray.getExpression().accept(this);
        addInstruction("newarray int");
    }

    @Override
    public void visit(NewClass newClass) {
        addInstruction("new " + newClass.getName().getName());
        addInstruction("dup");
        addInstruction("invokespecial " + newClass.getName().getName() + "/<init>()V");
    }

    @Override
    public void visit(This instance) {
        addInstruction("aload_0");
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
        unaryExpression.getValue().accept(this);
        if(unaryExpression.getValue() instanceof ArrayCall)
            addInstruction("iaload");
        switch (unaryExpression.getUnaryOperator()){
            case not:
                String labelZero = getLabel();
                String labelEnd = getLabel();
                addInstruction("ifne " + labelZero);
                addInstruction("iconst_1");
                addInstruction("goto " + labelEnd);
                addInstruction(labelZero + ":");
                addInstruction("iconst_0");
                addInstruction(labelEnd + ":");
                break;
            case minus:
                addInstruction("ineg");
                break;
        }
    }

    @Override
    public void visit(BooleanValue value) {
        addInstruction("iconst_" + (value.isConstant() ? "1" : "0"));
    }

    @Override
    public void visit(IntValue value) {
        addInstruction("bipush " + value.getConstant());
    }

    @Override
    public void visit(StringValue value) {
        addInstruction("ldc " + value.getConstant());
    }

    @Override
    public void visit(Assign assign) {
        Expression lvalue = assign.getlValue();
        if(lvalue instanceof ArrayCall) {
            assign.getlValue().accept(this);
            if(SymbolTable.isClassField(((ArrayCall) lvalue).getInstance()))
                addInstruction("aload_0");
            assign.getrValue().accept(this);
            if(assign.getrValue() instanceof ArrayCall)
                addInstruction("iaload");

            if(SymbolTable.isClassField(((ArrayCall) lvalue).getInstance()))
            	addInstruction("putfield " + SymbolTable.getFieldDescriptorCode(((Identifier) ((ArrayCall) lvalue).getInstance()).getName()));
			else
                addInstruction("iastore");
        }
        else {
            if(SymbolTable.isClassField(lvalue))
                addInstruction("aload_0");
            assign.getrValue().accept(this);
            if(assign.getrValue() instanceof ArrayCall)
                addInstruction("iaload");
            int leftIndex = SymbolTable.getIndex((Identifier)assign.getlValue());
            if(SymbolTable.isClassField(lvalue))
				addInstruction("putfield " + SymbolTable.getFieldDescriptorCode(((Identifier) lvalue).getName()));
			else
                generateCodeAccordingToType(assign.getlValue().getType(),
                    "istore " + leftIndex, "astore " + leftIndex);
        }
    }

    @Override
    public void visit(Block block) {
        for(Statement stm : block.getBody())
            stm.accept(this);
    }

    @Override
    public void visit(Conditional conditional) {
        String elseLabel = getLabel();
        String endLabel = getLabel();

        conditional.getExpression().accept(this);
        addInstruction("ifeq " + elseLabel);
        conditional.getConsequenceBody().accept(this);
        addInstruction("goto " + endLabel);
        addInstruction(elseLabel + ":");
        if(conditional.hasAlternativeBody())
            conditional.getAlternativeBody().accept(this);
        addInstruction(endLabel + ":");
    }

    private void methodCallCodeGenerator(Expression instance, ArrayList<Expression> args, String methodDescriptor){
        instance.accept(this);
        for(Expression arg : args)
            arg.accept(this);
        addInstruction("invokevirtual " + methodDescriptor);
    }

    @Override
    public void visit(MethodCallInMain methodCallInMain) {
        String methodName = methodCallInMain.getMethodName().getName();
        String instanceName = methodCallInMain.getInstance().getType().toString();
        String methodDescriptor = instanceName + "/" + methodName + getMethodDescriptorCode(instanceName, methodName);
        methodCallCodeGenerator(methodCallInMain.getInstance(), methodCallInMain.getArgs(), methodDescriptor);
    }

    @Override
    public void visit(While loop) {
        String labelFirst = getLabel();
        String labelEnd = getLabel();

        addInstruction(labelFirst + ":");
        loop.getCondition().accept(this);
        addInstruction("ifeq " + labelEnd);
        loop.getBody().accept(this);
        addInstruction("goto " + labelFirst);
        addInstruction(labelEnd + ":");
    }

    @Override
    public void visit(Write write) {
        String argCodeString;
        addInstruction("getstatic " + "java/lang/System/out Ljava/io/PrintStream;");
        if(write.getArg().getType() instanceof ArrayType){
            String resultLoadCode = generateArrayToStringCode(write.getArg());
            addInstruction(resultLoadCode);
            argCodeString = "Ljava/lang/String;";
        } else {
            write.getArg().accept(this);
            argCodeString = write.getArg().getType().getCodeString();
        }
        if(write.getArg() instanceof ArrayCall)
            addInstruction("iaload");
        addInstruction("invokevirtual " + "java/io/PrintStream/println(" + argCodeString + ")V");
    }

    // returns instruction to load resulting string
    private String generateArrayToStringCode(Expression arg) {
        int resultField = 19, iMaxField = 18, strBuilderField = 17, iField = 16;
        String firstOfLoop = getLabel(), posArrLabel = getLabel(), appendCommaLabel = getLabel(), endLabel = getLabel();
        int arrIndex = SymbolTable.getIndex((Identifier)arg);

        // we store return string value on field 19 of stack

        //1 : arIndex
        //2 : resultField
        //3 : iMaxField
        //4 : strBuilder
        //5 : iField
        if(SymbolTable.isClassField(arg)){
            addInstruction("aload_0");
            addInstruction("getfield " + SymbolTable.getFieldDescriptorCode(((Identifier)arg).getName()));
        } else
            addInstruction("aload " + arrIndex);
        addInstruction("arraylength");
        addInstruction("iconst_1");
        addInstruction("isub");
        addInstruction("istore " + iMaxField);
        addInstruction("iload " + iMaxField);
        addInstruction("iconst_m1");
        addInstruction("if_icmpne "+ posArrLabel);
        addInstruction("ldc " + "\"\"");
        addInstruction("astore " + resultField);
        addInstruction("goto " + endLabel);
        addInstruction(posArrLabel + ":");
        addInstruction("new " + "java/lang/StringBuilder");
        addInstruction("dup");
        addInstruction("invokespecial " + "java/lang/StringBuilder/<init>()V");
        addInstruction("astore " + strBuilderField);
        addInstruction("iconst_0");
        addInstruction("istore " + iField);
        addInstruction(firstOfLoop + ":");
        addInstruction("aload " + strBuilderField);
        if(SymbolTable.isClassField(arg)){
            addInstruction("aload_0");
            addInstruction("getfield " + SymbolTable.getFieldDescriptorCode(((Identifier)arg).getName()));
        } else
            addInstruction("aload " + arrIndex);
        addInstruction("iload " + iField);
        addInstruction("iaload ");
        addInstruction("invokevirtual " + "java/lang/StringBuilder/append(I)Ljava/lang/StringBuilder;");
        addInstruction("pop");
        addInstruction("iload " + iField);
        addInstruction("iload " + iMaxField);
        addInstruction("if_icmpne " + appendCommaLabel);
        addInstruction("aload " + strBuilderField);
        addInstruction("invokevirtual " + "java/lang/StringBuilder/toString()Ljava/lang/String;");
        addInstruction("astore " + resultField);
        addInstruction("goto " + endLabel);
        addInstruction(appendCommaLabel + ":");
        addInstruction("aload " + strBuilderField);
        addInstruction("ldc " + "\", \"");
        addInstruction("invokevirtual " + "java/lang/StringBuilder/append(Ljava/lang/String;)Ljava/lang/StringBuilder;");
        addInstruction("pop");
        addInstruction("iinc " + iField + " 1");
        addInstruction("goto " + firstOfLoop);
        addInstruction(endLabel + ":");
        return "aload " + resultField;
    }
}

//TODO: User Defined Type?







//##Created By Farzad
//class MainClass
//{
//    def main() : int
//    {
//
//        writeln("Hello This is a test");
//        writeln("Factorial of 6 is :");
//        writeln(new A().calculateFactorial(6));
//        new B().binaryExprCheck();
//        new FakeMain().fakeMain();
//        new TestClass().testMethod(new B());
//        return 0;
//    }
//}
//
//class TestClass {
//    def testMethod(input : B) : int {
//        return input.f(2);
//    }
//
//}
//
//class FakeMain
//{
//    def fakeMain() : int
//    {
//        var loopTest : LoopTest;
//        var arr : int[];
//        arr = new int[10];
//        loopTest = new LoopTest();
//        writeln("I pass here");
//        arr = loopTest.initArr(arr);
//        arr = loopTest.bubbleSort(arr);
//        writeln("Sorted : ");
//        writeln(arr);
//        return 0;
//    }
//}
//class A
//{
//    var fact : int;
//    def calculateFactorial(input : int) : int
//    {
//        var i : int;
//        writeln("Salam");
//        i = input;
//        fact = 1;
//        while(i <> 0)
//        {
//            fact = fact * i;
//            i = i - 1;
//        }
//        return fact;
//    }
//
//}
//class B extends A
//{
//    def binaryExprCheck() : int
//    {
//        #var fact : int;
//        var x : int;
//        var y : int;
//        var b : boolean;
//        x = fact + 12 * 123 + x / 12;
//        if( (y == fact) && b ) then
//        {
//            writeln("It's ok");
//        }
//        else
//        {
//            writeln("It's not ok");
//        }
//        writeln("End");
//        return 0;
//    }
//
//    def f(in : int) int {
//        writeln(in);
//        return in;
//    }
//}
//class LoopTest
//{
//
//    var sortedArray : int [];
//    var a : int;
//    var b : int;
//    def initArr ( arr : int[]) : int []
//    {
//
//        var i : int;
//
//        while( i < 10 )
//        {
//            arr[i] = i;
//            i = i + 1;
//        }
//        writeln("Unsorted array :");
//        writeln(arr);
//        return arr;
//
//    }
//    def bubbleSort( arr : int[]) : int[]
//    {
//        var i : int;        #2
//        var j : int;        #3
//        var size : int;     #4
//        var swapped : boolean; #5
//        var temp : int;     #6
//        writeln("Here");
//        swapped = false;
//        i = 0;
//        j = 0;
//        size = arr.length;
//        while(i < size - 1)
//        {
//
//            swapped = false;
//            j = 0;
//            while ( j < size - i - 1 )
//            {
//                if( arr[j] < arr [j+1] ) then
//                {
//                    a = arr[j];
//                    b = arr[j+1];
//                    temp = this.swapAB();
//                    arr[j] = a;
//                    arr[j+1] = b;
//                    swapped = true;
//                }
//                j = j + 1;
//            }
//
//            if(!swapped) then
//            {
//                i = size + 1; # to break !
//            }
//            i = i + 1;
//        }
//        sortedArray = arr;
//        return arr;
//    }
//    def swapAB() : int
//    {
//        var temp : int;
//        temp = a;
//        a = b;
//        b = temp;
//        return 0;
//    }
//
//}