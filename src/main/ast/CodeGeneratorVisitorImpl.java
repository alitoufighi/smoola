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
            System.out.println("WTF");
            System.out.println(type.toString());
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
        addInstruction(".limit locals 20");
        addInstruction(".limit stack 20");
    }

    private void generateConstructorCode(ClassDeclaration classDeclaration) {
        addInstruction(".method public <init>()V");
        incIndent();
        addInstruction(".limit locals 1");
        addInstruction(".limit stack 1");
        addInstruction("aload_0");
        addInstruction("invokespecial "+classDeclaration.getParentObjectString()+"/<init>()V");
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

        addInstruction(".class public "+classDeclaration.getName().getName());
        addInstruction(".super "+classDeclaration.getParentObjectString());

        generateConstructorCode(classDeclaration);

        SymbolTable.push(classDeclaration.getSymbolTable());

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
        for(Statement stm : methodDeclaration.getBody())
            stm.accept(this);
        generateReturnCode(methodDeclaration);
        decIndent();
        addInstruction(".end method\n");
    }


    @Override
    public void visit(VarDeclaration varDeclaration, VarVisitType visitType) {
        // not called.
    }

    @Override
    public void visit(ArrayCall arrayCall) {
//        Expression instance = arrayCall.getInstance();
//        if(instance instanceof Identifier){
//            int index = SymbolTable.getIndex((Identifier)arrayCall.getInstance());
//            ((Identifier) arrayCall.getInstance()).accept();
//        }
        //if return value of function is array?
        arrayCall.getInstance().accept(this);
        arrayCall.getIndex().accept(this);
        //TODO: Check
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        String labelFalse = "", labelTrue = ""; //TODO: CLEAN IT
        if(!(binaryExpression.getBinaryOperator().equals(BinaryOperator.OperatorTypes.or) ||
                binaryExpression.getBinaryOperator().equals(BinaryOperator.OperatorTypes.and))) {
            binaryExpression.getLeft().accept(this);
            binaryExpression.getRight().accept(this);
        } else {
            labelFalse = getLabel();
            labelTrue = getLabel();
//            if(binaryExpression.ge)
        }

        Expression left = binaryExpression.getLeft();
        Expression right = binaryExpression.getRight();
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
                //TODO: dup vaseye arraycall dastan nadare?
                addInstruction("dup_x2");
                if(left instanceof Identifier){
                    int index = SymbolTable.getIndex((Identifier) left);
                    generateCodeAccordingToType(
                            left.getType(), "istore " + index, "astore " + index);
                } else if (left instanceof ArrayCall){
                    addInstruction("iastore");
                    //TODO: vaghti mikham be arraycall access dashte basham (x = arr[5] ya writeln(arr[4]) ) bayad "iaload" konam
                } else
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
//                String labelFalse = getLabel();
//                String labelTrue = getLabel();
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
        //System.out.println("Visiting "+identifier.getName()+ " at line "+identifier.getLineNum());
        int index = SymbolTable.getIndex(identifier);
        generateCodeAccordingToType(identifier.getType()
                , "iload " + index, "aload " + index);
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
        methodCall.getInstance().accept(this);

        for(Expression arg : methodCall.getArgs())
            arg.accept(this);
        String methodName = methodCall.getMethodName().getName();
        String instanceName = methodCall.getInstance().getType().toString();
        addInstruction("invokevirtual " + instanceName + "/" +
                methodName + getMethodDescriptorCode(instanceName, methodName));

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
        }
    }

    @Override
    public void visit(BooleanValue value) {
        addInstruction("iconst_" + (value.isConstant() ? "1" : "0")); //TODO: Check
    }

    @Override
    public void visit(IntValue value) {
        addInstruction("bipush "+value.getConstant());
    }

    @Override
    public void visit(StringValue value) {
        addInstruction("ldc " + value.getConstant());
    }

    @Override
    public void visit(Assign assign) {
        int leftIndex;
        if(assign.getlValue() instanceof ArrayCall){
            assign.getlValue().accept(this);
            assign.getrValue().accept(this);
            if(assign.getrValue() instanceof ArrayCall)
                addInstruction("iaload");
            addInstruction("iastore");
        }
        else {
            assign.getrValue().accept(this);
            leftIndex = SymbolTable.getIndex((Identifier)assign.getlValue());
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
        if(conditional.hasAlternativeBody()){
            conditional.getAlternativeBody().accept(this);
        }
        addInstruction(endLabel + ":");
    }

//TODO: Meghdar dehie avalie!

    @Override
    public void visit(MethodCallInMain methodCallInMain) {
        methodCallInMain.getInstance().accept(this);

        for(Expression arg : methodCallInMain.getArgs())
            arg.accept(this);
        String methodName = methodCallInMain.getMethodName().getName();
        String instanceName = methodCallInMain.getInstance().getType().toString();
        addInstruction("invokevirtual " + instanceName + "/" +
                methodName + getMethodDescriptorCode(instanceName, methodName));
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
        addInstruction("getstatic " + "java/lang/System/out Ljava/io/PrintStream;");
        write.getArg().accept(this);
        if(write.getArg() instanceof ArrayCall)
            addInstruction("iaload");
        addInstruction("invokevirtual " + "java/io/PrintStream/println(" + write.getArg().getType().getCodeString() + ")V");
    }
}
