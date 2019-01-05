package ast;

import ast.node.Program;
import ast.node.declaration.ClassDeclaration;
import ast.node.declaration.MethodDeclaration;
import ast.node.declaration.VarDeclaration;
import ast.node.expression.*;
import ast.node.expression.Value.BooleanValue;
import ast.node.expression.Value.IntValue;
import ast.node.expression.Value.StringValue;
import ast.node.statement.*;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

//import java.io.FileWriter;
//import java.util.stream.Stream;

public class CodeGeneratorVisitorImpl implements Visitor {
    private String getJasminFileName() {
        return Program.currentClass.concat(".j");
    }

    private void addInstruction(String instruction){
        try{
            Files.write(Paths.get(getJasminFileName()),
                    (instruction+System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e){
            System.out.println("Exception occurred.");
        }
    }


    @Override
    public void visit(Program program) {
        program.getMainClass().accept(this);
        for (ClassDeclaration classDeclaration : program.getClasses())
            classDeclaration.accept(this);

//        try {
//            Files.write(Paths.get(getJasminFileName()), 5, Charset.forName("UTF-8"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void visit(ClassDeclaration classDeclaration) {
        classDeclaration.getName().accept(this);
        Program.currentClass = classDeclaration.getName().getName();

        addInstruction(".class public "+classDeclaration.getName().getName());
        addInstruction(".super "+classDeclaration.getParentObjectString());

        // constructor
        addInstruction(".method public <init>()V");
        addInstruction("\taload_0");
        addInstruction("\tinvokespecial "+classDeclaration.getParentObjectString()+"/<init>()V");
        addInstruction("\treturn");
        addInstruction(".end method\n");

//        if()
        for(VarDeclaration varDeclaration : classDeclaration.getVarDeclarations())
            varDeclaration.accept(this);
        for(MethodDeclaration methodDeclaration : classDeclaration.getMethodDeclarations())
            methodDeclaration.accept(this);
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration) {
        if(methodDeclaration.isMainMethod())
            addInstruction(".method public static main()V");
        else
            addInstruction(".method public "+methodDeclaration.getName().getName()+
                    "("+methodDeclaration.getArgsCodeString()+")"+methodDeclaration.getReturnTypeCodeString());
    }

    @Override
    public void visit(VarDeclaration varDeclaration, VarVisitType visitType) {

    }

    @Override
    public void visit(ArrayCall arrayCall) {

    }

    @Override
    public void visit(BinaryExpression binaryExpression) {

    }

    @Override
    public void visit(Identifier identifier) {

    }

    @Override
    public void visit(Identifier identifier, int type) {

    }

    @Override
    public void visit(Length length) {

    }

    @Override
    public void visit(MethodCall methodCall) {

    }

    @Override
    public void visit(NewArray newArray) {

    }

    @Override
    public void visit(NewClass newClass) {

    }

    @Override
    public void visit(This instance) {

    }

    @Override
    public void visit(UnaryExpression unaryExpression) {

    }

    @Override
    public void visit(BooleanValue value) {

    }

    @Override
    public void visit(IntValue value) {
        try {
            Files.write(Paths.get(getJasminFileName()), BigInteger.valueOf(value.getConstant()).toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(StringValue value) {

    }

    @Override
    public void visit(Assign assign) {

    }

    @Override
    public void visit(Block block) {

    }

    @Override
    public void visit(Conditional conditional) {

    }

    @Override
    public void visit(MethodCallInMain methodCallInMain) {

    }

    @Override
    public void visit(While loop) {

    }

    @Override
    public void visit(Write write) {

    }
}
