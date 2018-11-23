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
//    	try {
//			SymbolTable.push(new SymbolTable());
//		}
//		catch(ItemAlreadyExistsException) {
//
//		}
		program.getMainClass().accept(this);

		for (ClassDeclaration classDeclaration : program.getClasses())
			classDeclaration.accept(this);

		System.out.println("program visited!");
	}

    @Override
    public void visit(ClassDeclaration classDeclaration) {
    	String name = classDeclaration.getName().getName();
    	try{
			System.out.println(classDeclaration.toString());
			SymbolTable.top.put(new SymbolTableClassItem(name));
			classDeclaration.getName().accept(this);
			classDeclaration.getParentName().accept(this);
			for(VarDeclaration vdec : classDeclaration.getVarDeclarations())
				vdec.accept(this);
			for(MethodDeclaration mdec : classDeclaration.getMethodDeclarations())
				mdec.accept(this);
		}
		catch(ItemAlreadyExistsException e){
//			SymbolTable.top.put(new SymbolTableClassItem(name.concat("-temp"))); // ?!?
			System.out.println(
					new StringBuilder("line:").append(classDeclaration.getLineNum())
							.append(":Redefinition of class ").append(name)
			);
		}
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration) {
    	String name = methodDeclaration.getName().getName();
		ArrayList<Type> argsType = new ArrayList<>();
    	for(VarDeclaration var : methodDeclaration.getArgs())
    		argsType.add(var.getType());

    	try{
			System.out.println(methodDeclaration.toString());
			SymbolTable.top.put(new SymbolTableMethodItem(name, argsType));
			for(VarDeclaration arg : methodDeclaration.getArgs())
				arg.accept(this);
			for(VarDeclaration localVar : methodDeclaration.getLocalVars())
				localVar.accept(this);
    		methodDeclaration.getName().accept(this);
			for(Statement stm : methodDeclaration.getBody()){
				stm.accept(this);
			}
			methodDeclaration.getReturnValue().accept(this);
    	}
		catch (ItemAlreadyExistsException e){
    		System.out.println(
    				new StringBuilder("line: ").append(methodDeclaration.getLineNum())
							.append(":Redefinition of method ").append(name)
			);
		}
    }

    @Override
    public void visit(VarDeclaration varDeclaration) {
		String name = varDeclaration.getIdentifier().getName();
		Type type = varDeclaration.getType();
		try{
			System.out.println(varDeclaration.toString());
			SymbolTable.top.put(new SymbolTableVariableItem(name, type));
			varDeclaration.getIdentifier().accept(this);
		}
		catch (ItemAlreadyExistsException e){
			System.out.println(
					new StringBuilder("line: ").append(varDeclaration.getLineNum())
							.append(":Redefinition of variable ").append(name)
			);
		}
    }

    @Override
    public void visit(ArrayCall arrayCall) {
    	System.out.println(arrayCall.toString());
		arrayCall.getIndex().accept(this);
		arrayCall.getInstance().accept(this);
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
    	System.out.println(binaryExpression.toString());
    	binaryExpression.getLeft().accept(this);
    	binaryExpression.getRight().accept(this);
    }

    @Override
    public void visit(Identifier identifier) {
    	System.out.println(identifier.toString());
    }

    @Override
    public void visit(Length length) {
    	System.out.println(length.toString());
    	length.getExpression().accept(this);
    }

    @Override
    public void visit(MethodCall methodCall) {
    	System.out.println(methodCall.toString());
		methodCall.getMethodName().accept(this);
		methodCall.getInstance().accept(this);
		for(Expression arg : methodCall.getArgs())
			arg.accept(this);
    }

    @Override
    public void visit(NewArray newArray) {
    	System.out.println(newArray.toString());
    	newArray.getExpression().accept(this);
    }

    @Override
    public void visit(NewClass newClass) {
		System.out.println(newClass.toString());
		newClass.getClassName().accept(this);
    }

    @Override
    public void visit(This instance) {
		System.out.println(instance.toString());
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
    	System.out.println(unaryExpression.toString());
    	unaryExpression.getValue().accept(this);
    }

    @Override
    public void visit(BooleanValue value) {
		System.out.println(value.toString());
    }

    @Override
    public void visit(IntValue value) {
		System.out.println(value.toString());
    }

    @Override
    public void visit(StringValue value) {
		System.out.println(value.toString());
    }

    @Override
    public void visit(Assign assign) {
		System.out.println(assign.toString());
		assign.getlValue().accept(this);
		assign.getrValue().accept(this);
    }

    @Override
    public void visit(Block block) {
		System.out.println(block.toString());
		for(Statement stm : block.getBody())
			stm.accept(this);
    }

    @Override
    public void visit(Conditional conditional) {
		System.out.println(conditional.toString());
		conditional.getExpression().accept(this);
		conditional.getAlternativeBody().accept(this);
		conditional.getConsequenceBody().accept(this);
    }

    @Override
    public void visit(While loop) {
		System.out.println(loop.toString());
		loop.getCondition().accept(this);
		loop.getBody().accept(this);
    }

    @Override
    public void visit(Write write) {
		System.out.println(write.toString());
		write.getArg().accept(this);
    }
}
