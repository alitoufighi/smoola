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
import symbolTable.ItemNotFoundException;
import symbolTable.SymbolTable;
import symbolTable.SymbolTableVariableItem;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CodeGeneratorVisitorImpl implements Visitor {
    private String getJasminFileName() {
        return Program.currentClass.concat(".j");
    }

    private int indent = 0;

    private void addInstruction(String instruction){
        try{
            Files.write(Paths.get(getJasminFileName()),
                    (getIndent() + instruction + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e){
            System.out.println("Exception occurred.");
        }
    }

    private String getIndent() {
        return IntStream.range(0, indent).mapToObj(i -> "\t").collect(Collectors.joining());
    }

    private void incIndent() { indent++; }
    private void decIndent() { indent--; }

    @Override
    public void visit(Program program) {
        program.getMainClass().accept(this);
        for (ClassDeclaration classDeclaration : program.getClasses())
            classDeclaration.accept(this);
    }

    @Override
    public void visit(ClassDeclaration classDeclaration) {
        classDeclaration.getName().accept(this);
        Program.currentClass = classDeclaration.getName().getName();

        addInstruction(".class public "+classDeclaration.getName().getName());
        addInstruction(".super "+classDeclaration.getParentObjectString());

        // constructor
        addInstruction(".method public <init>()V");
        incIndent();
        addInstruction("aload_0");
        addInstruction("invokespecial "+classDeclaration.getParentObjectString()+"/<init>()V");
        addInstruction("return");
        decIndent();
        addInstruction(".end method\n");

//        SymbolTable.push(Program.getClassSymbolTable(classDeclaration.getName().getName()));
        SymbolTable.push(classDeclaration.getSymbolTable());

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

        SymbolTable.push(methodDeclaration.getSymbolTable());
//        for(VarDeclaration arg : methodDeclaration.getArgs())
//            arg.accept(this, VarVisitType.InMethod);
//        for(VarDeclaration localVar : methodDeclaration.getLocalVars())
//            localVar.accept(this, VarVisitType.InMethod);
        incIndent();
        for(Statement stm : methodDeclaration.getBody())
            stm.accept(this);
        decIndent();
    }

    @Override
    public void visit(VarDeclaration varDeclaration, VarVisitType visitType) {

    }

    @Override
    public void visit(ArrayCall arrayCall) {

    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        binaryExpression.getLeft().accept(this);
        binaryExpression.getRight().accept(this);
        switch (binaryExpression.getBinaryOperator()){
            case mult:
                addInstruction("imul");
                break;
            case add:
                addInstruction("iadd");
                break;
        }
        System.out.println("!");
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
        addInstruction("iconst_"+value.getConstant());
    }

    @Override
    public void visit(StringValue value) {
        addInstruction("ldc " + value.getConstant());
    }

    @Override
    public void visit(Assign assign) {
        assign.getlValue().accept(this);
        assign.getrValue().accept(this);
        try{
            int leftIndex = ((SymbolTableVariableItem)SymbolTable.top.get(((Identifier)assign.getlValue()).getName()+"@var")).getIndex();
            addInstruction("istore_" + leftIndex);
        } catch (ItemNotFoundException e){
            System.out.println("WHAT THE FUCK");
        }
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
