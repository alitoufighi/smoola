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
import ast.Type.Type;
import symbolTable.*;

import java.util.ArrayList;

public class VisitorImpl implements Visitor {

    @Override
    public void visit(Program program) {
        Program.addMessage(program.toString());

		SymbolTable.push(new SymbolTable());

		program.getMainClass().accept(this);

		for (ClassDeclaration classDeclaration : program.getClasses())
			classDeclaration.accept(this);

		SymbolTable.pop();
	}

    @Override
    public void visit(ClassDeclaration classDeclaration) {
    	String name = classDeclaration.getName().getName();

    	Program.addMessage(classDeclaration.toString());
        try{
			SymbolTable.top.put(new SymbolTableClassItem(name));
        }
		catch(ItemAlreadyExistsException e){
            try{
                SymbolTable.top.put(new SymbolTableClassItem(
                        new StringBuilder("temp").append(Program.getNewTempVar())
                                .append("-").append(name).toString())
                ); // now what?!?
            }
            catch (ItemAlreadyExistsException e1){
                // ?!?
            }
            Program.invalidate();

            Program.addError(
                    new StringBuilder("line:").append(classDeclaration.getLineNum())
                            .append(":Redefinition of class ").append(name).toString()
            );
		}
        SymbolTable.push(new SymbolTable());

        classDeclaration.getName().accept(this);
        if(classDeclaration.hasParent()) {
            classDeclaration.getParentName().accept(this);
        }
        for(VarDeclaration vdec : classDeclaration.getVarDeclarations())
            vdec.accept(this);
        for(MethodDeclaration mdec : classDeclaration.getMethodDeclarations())
            mdec.accept(this);

        SymbolTable.pop();
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration) {
    	String name = methodDeclaration.getName().getName();

    	Program.addMessage(methodDeclaration.toString());
        ArrayList<Type> argsType = new ArrayList<>();
        for(VarDeclaration var : methodDeclaration.getArgs())
            argsType.add(var.getType());

        try {

            SymbolTable.top.put(new SymbolTableMethodItem(name, argsType));
        }
		catch (ItemAlreadyExistsException e){
            try{
                SymbolTable.top.put(new SymbolTableMethodItem(
                        new StringBuilder("temp").append(Program.getNewTempVar())
                                .append("-").append(name).toString(), argsType)
                ); // now what?!?
            }
            catch (ItemAlreadyExistsException e1){
                // ?!?
            }

            Program.invalidate();

            Program.addError(
                    new StringBuilder("line:").append(methodDeclaration.getLineNum())
                            .append(":Redefinition of method ").append(name).toString()
            );
        }
        SymbolTable.push(SymbolTable.top);

        for(VarDeclaration arg : methodDeclaration.getArgs())
            arg.accept(this);
        for(VarDeclaration localVar : methodDeclaration.getLocalVars())
            localVar.accept(this);
        methodDeclaration.getName().accept(this);
        for(Statement stm : methodDeclaration.getBody())
            stm.accept(this);
        methodDeclaration.getReturnValue().accept(this);

        SymbolTable.pop();
    }

    @Override
    public void visit(VarDeclaration varDeclaration) {
		String name = varDeclaration.getIdentifier().getName();
		Type type = varDeclaration.getType();
		Program.addMessage(varDeclaration.toString());
        try{
			SymbolTable.top.put(new SymbolTableVariableItem(name, type));

		}
		catch (ItemAlreadyExistsException e){
            try {
                SymbolTable.top.put(new SymbolTableVariableItem(
                        new StringBuilder("temp").append(Program.getNewTempVar())
                                .append("-").append(name).toString(), type)
                ); // now what?!?
            }
            catch (ItemAlreadyExistsException e1){
                // ?!?
            }
            Program.invalidate();

            Program.addError(
                new StringBuilder("line:").append(varDeclaration.getLineNum())
                        .append(":Redefinition of variable ").append(name).toString()
            );
        }
        varDeclaration.getIdentifier().accept(this);
    }

    @Override
    public void visit(ArrayCall arrayCall) {
        Program.addMessage(arrayCall.toString());
		arrayCall.getIndex().accept(this);
		arrayCall.getInstance().accept(this);
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        Program.addMessage(binaryExpression.toString());
    	binaryExpression.getLeft().accept(this);
    	binaryExpression.getRight().accept(this);
    }

    @Override
    public void visit(Identifier identifier) {
        Program.addMessage(identifier.toString());
    }

    @Override
    public void visit(Length length) {
        Program.addMessage(length.toString());
    	length.getExpression().accept(this);
    }

    @Override
    public void visit(MethodCall methodCall) {
    	Program.addMessage(methodCall.toString());

		methodCall.getMethodName().accept(this);
		methodCall.getInstance().accept(this);

		for(Expression arg : methodCall.getArgs())
			arg.accept(this);
    }

    @Override
    public void visit(NewArray newArray) {
        int arraySize = ((IntValue)newArray.getExpression()).getConstant();
        if(arraySize <= 0) {
            Program.invalidate();
            Program.addError(
                    new StringBuilder("line:").append(newArray.getLineNum())
                            .append(":Array length should not be zero or negative").toString()
            );
        }
        else
            Program.addMessage(newArray.toString());
    	newArray.getExpression().accept(this);
    }

    @Override
    public void visit(NewClass newClass) {
        Program.addMessage(newClass.toString());
		newClass.getClassName().accept(this);
    }

    @Override
    public void visit(This instance) {
        Program.addMessage(instance.toString());
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
        Program.addMessage(unaryExpression.toString());

    	unaryExpression.getValue().accept(this);
    }

    @Override
    public void visit(BooleanValue value) {
        Program.addMessage(value.toString());
    }

    @Override
    public void visit(IntValue value) {
        Program.addMessage(value.toString());
    }

    @Override
    public void visit(StringValue value) {
        Program.addMessage(value.toString());
    }

    @Override
    public void visit(Assign assign) {
        Program.addMessage(assign.toString());

		assign.getlValue().accept(this);
		assign.getrValue().accept(this);
    }

    @Override
    public void visit(Block block) {
        Program.addMessage(block.toString());

		SymbolTable.push(SymbolTable.top);

		for(Statement stm : block.getBody())
			stm.accept(this);

		SymbolTable.pop();
    }

    @Override
    public void visit(Conditional conditional) {
        Program.addMessage(conditional.toString());
		conditional.getExpression().accept(this);
		conditional.getAlternativeBody().accept(this);
		conditional.getConsequenceBody().accept(this);
    }

    @Override
    public void visit(While loop) {
        Program.addMessage(loop.toString());
		loop.getCondition().accept(this);
		loop.getBody().accept(this);
    }

    @Override
    public void visit(Write write) {
        Program.addMessage(write.toString());
		write.getArg().accept(this);
    }
}
