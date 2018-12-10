package ast;

import ast.Type.Type;
import ast.Type.UserDefinedType.UserDefinedType;
import ast.node.PhaseNum;
import ast.node.Program;
import ast.node.declaration.ClassDeclaration;
import ast.node.declaration.MethodDeclaration;
import ast.node.declaration.VarDeclaration;
import ast.node.expression.*;
import ast.node.expression.Value.BooleanValue;
import ast.node.expression.Value.IntValue;
import ast.node.expression.Value.StringValue;
import ast.node.statement.*;
import symbolTable.*;

import java.util.ArrayList;

public class VisitorImpl implements Visitor {

    @Override
    public void visit(Program program) {
        Program.addMessage(program.toString());

		SymbolTable.push(new SymbolTable());

        Program.passNum = 1;

        program.getMainClass().accept(this);

		for (ClassDeclaration classDeclaration : program.getClasses())
            classDeclaration.accept(this);

        for (ClassDeclaration c : program.getClasses())
            Program.addSymbolTableItems(Program.getClassSymbolTable(c.getName().getName()), c);

        Program.passNum = 2;

        program.getMainClass().accept(this);

        for (ClassDeclaration classDeclaration : program.getClasses())
            classDeclaration.accept(this);

		SymbolTable.pop();
	}

    @Override
    public void visit(ClassDeclaration classDeclaration) {
        String name = classDeclaration.getName().getName();
        if(Program.passNum == 1){

            try{
                SymbolTable.top.put(new SymbolTableClassItem(name));
            }
            catch(ItemAlreadyExistsException e){
                try {
                    SymbolTable.top.put(new SymbolTableClassItem(
                    "temp" + Program.getNewTempVar() +
                            "-" + name
                            )
                    ); // now what?!?
                }
                catch (ItemAlreadyExistsException e1){
                    // ?!?
                }
                Program.invalidate();

                Program.addError(
                    "line:" + classDeclaration.getLineNum() +
                            ":Redefinition of class " + name
                , PhaseNum.two);
            }

            SymbolTable.push(new SymbolTable());

            for(VarDeclaration vdec : classDeclaration.getVarDeclarations())
                vdec.accept(this, VarVisitType.InClass);
            for(MethodDeclaration mdec : classDeclaration.getMethodDeclarations())
                mdec.accept(this);

            Program.addClassSymbolTable(name, SymbolTable.top);
            SymbolTable.pop();
        }
        else if (Program.passNum == 2){
            Program.addMessage(classDeclaration.toString());

            if(classDeclaration.hasParent()){
                String parentName = classDeclaration.getParentName().getName();
                try {
                    SymbolTable.top.get(parentName.concat("@class"));
                }
                catch (ItemNotFoundException e){
                    Program.invalidate();
                    Program.addError(
                        "line:" + classDeclaration.getLineNum() +
                                ":class " + parentName + " is not declared"
                    , PhaseNum.three);
                }
            }

            SymbolTable.push(new SymbolTable(Program.getClassSymbolTable(name)));

            classDeclaration.getName().accept(this);
            if(classDeclaration.hasParent())
                classDeclaration.getParentName().accept(this);
            for(VarDeclaration vdec : classDeclaration.getVarDeclarations())
                vdec.accept(this, VarVisitType.InClass);
            for(MethodDeclaration mdec : classDeclaration.getMethodDeclarations())
                mdec.accept(this);

            SymbolTable.pop();
        }
    }

    @Override
    public void visit(MethodDeclaration methodDeclaration) {
    	String name = methodDeclaration.getName().getName();
        if(Program.passNum == 1){

            ArrayList<Type> argsType = new ArrayList<>();
            for(VarDeclaration var : methodDeclaration.getArgs())
                argsType.add(var.getType());

            try {
                SymbolTable.top.put(new SymbolTableMethodItem(name, argsType));
            }
            catch (ItemAlreadyExistsException e){
                try{
                    SymbolTable.top.put(new SymbolTableMethodItem(
                        "temp" + Program.getNewTempVar() +
                                "-" + name, argsType)
                    ); // now what?!?
                }
                catch (ItemAlreadyExistsException e1){
                    // ?!?
                }
                Program.invalidate();

                Program.addError(
                    "line:" + methodDeclaration.getLineNum() +
                            ":Redefinition of method " + name
                , PhaseNum.two);
            }
        }
        else if(Program.passNum == 2){
            Program.addMessage(methodDeclaration.toString());


            Type varType = methodDeclaration.getReturnType();
            setUserDefinedType(varType);

            SymbolTable.push(new SymbolTable(SymbolTable.top));

            methodDeclaration.getName().accept(this);
            for(VarDeclaration arg : methodDeclaration.getArgs())
                arg.accept(this, VarVisitType.InMethod);
            for(VarDeclaration localVar : methodDeclaration.getLocalVars())
                localVar.accept(this, VarVisitType.InMethod);
            for(Statement stm : methodDeclaration.getBody())
                stm.accept(this);
            methodDeclaration.getReturnValue().accept(this);

            SymbolTable.pop();
        }
    }

    private void setUserDefinedType(Type varType) {
        if(varType instanceof UserDefinedType){
            String className = ((UserDefinedType)varType).getName().getName();
            try{
                ((UserDefinedType) varType).setClassDeclaration(Program.getClass(className));
            } catch (Exception e){
                Program.invalidate();
                Program.addError(
                        "line:" + ((UserDefinedType) varType).getName().getLineNum() +
                                ":class " + className + " is not declared"
                        , PhaseNum.three);
            }
        }
    }

    @Override
    public void visit(VarDeclaration varDeclaration, VarVisitType visitType) {
        String name = varDeclaration.getIdentifier().getName();
        if(Program.passNum == 1)
            putVariableItemToTopSymbolTable(varDeclaration, name);
        else if(Program.passNum == 2){
            Type varType = varDeclaration.getType();
            setUserDefinedType(varType);

            // if we're in accepting a local variable of a method,
            // we should try to put it in symbol table
            if(visitType == VarVisitType.InMethod)
                putVariableItemToTopSymbolTable(varDeclaration, name);

            Program.addMessage(varDeclaration.toString());

            varDeclaration.getIdentifier().accept(this);
        }
    }

    private void putVariableItemToTopSymbolTable(VarDeclaration varDeclaration, String name) {
        Type type = varDeclaration.getType();
        try{
            SymbolTable.top.put(new SymbolTableVariableItem(name, type));
        }
        catch (ItemAlreadyExistsException e){
            try {
                SymbolTable.top.put(new SymbolTableVariableItem(
                        "temp" + Program.getNewTempVar() +
                                "-" + name, type)
                ); // now what?!?
            }
            catch (ItemAlreadyExistsException e1){
                // ?!?
            }
            Program.invalidate();
            Program.addError(
                "line:" + varDeclaration.getLineNum() +
                        ":Redefinition of variable " + name
            , PhaseNum.two);
        }
    }

    @Override
    public void visit(ArrayCall arrayCall) {
        Program.addMessage(arrayCall.toString());
        arrayCall.getInstance().accept(this);
        arrayCall.getIndex().accept(this);
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

        // 5. in phase 3:
        // a.b()
        // a is instance, b is method name
        // if instance instanceof NewClass, when creating a new object
        // if instance identifiere, bayad bbinim typesh chie!
        // if instance methodCalle, returnType?!
        String methodName = methodCall.getMethodName().getName();
        Expression instance = methodCall.getInstance();
        if(instance instanceof NewClass){
            String className = ((NewClass) instance).getClassName().getName();
            try{
                Program.getClass(className);

            } catch (Exception e){
                Program.invalidate();

                Program.addError(
                "line:" + ((NewClass) instance).getClassName().getLineNum() +
                        ":there is no method named " + methodName +
                        " in class " + className
                , PhaseNum.three);
            }
        }
        else if(instance instanceof Identifier){
            String instanceVarName = ((Identifier) instance).getName(); // variable of instance

            try{
                SymbolTableItem item = SymbolTable.top.get(instanceVarName.concat("@var")); //this may raise exception

                if(item instanceof SymbolTableVariableItem){
                    Type instanceType = ((SymbolTableVariableItem) item).getType();
                    if(instanceType instanceof UserDefinedType){
                        String className = ((UserDefinedType) instanceType).getName().getName();
                        try{
                            Program.getClass(className);

                        } catch (Exception e){
                            Program.invalidate();

                            Program.addError(
                                    "line:" + instance.getLineNum() +
                                            ":there is no method named " + methodName +
                                            " in class " + className
                                    , PhaseNum.three);
                        }
//                        String className = ((NewClass) instance).getClassName().getName();
                    }

                }

            } catch (ItemNotFoundException e){
                Program.invalidate();

                Program.addError(
                        "line:" + methodCall.getMethodName().getLineNum() +
                                ":variable " + instanceVarName +
                                " is not declared"
                        , PhaseNum.three);
            }
        }

        methodCall.getInstance().accept(this);
        methodCall.getMethodName().accept(this);
		for(Expression arg : methodCall.getArgs())
			arg.accept(this);
    }

    @Override
    public void visit(NewArray newArray) {
        int arraySize = ((IntValue)newArray.getExpression()).getConstant();
        if(arraySize <= 0) {
            Program.invalidate();
            Program.addError(
                "line:" + newArray.getLineNum() +
                        ":Array length should not be zero or negative"
            , PhaseNum.two);
            ((IntValue)newArray.getExpression()).setConstant(0); // default value to 0 (?!)
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

//		SymbolTable.push(new SymbolTable(SymbolTable.top));

		for(Statement stm : block.getBody())
			stm.accept(this);

//		SymbolTable.pop();
    }

    @Override
    public void visit(Conditional conditional) {
        Program.addMessage(conditional.toString());
		conditional.getExpression().accept(this);
        conditional.getConsequenceBody().accept(this);
        if(conditional.hasAlternativeBody())
           conditional.getAlternativeBody().accept(this);
    }

    @Override
    public void visit(While loop) {
        Program.addMessage(loop.toString());
        Expression condition =  loop.getCondition();
        if(condition instanceof BinaryExpression){
           if(!BinaryOperator.isBooleanOperator(((BinaryExpression) condition).getBinaryOperator())){
               Program.addError(
                       "line:" + loop.getLineNum() + ":condition type must be boolean"
                       , PhaseNum.three);
           }
        }
        else if(!(condition instanceof BooleanValue)){
            Program.addError(
                    "line:" + loop.getLineNum() + ":condition type must be boolean"
                    , PhaseNum.three
            );
        }
        condition.accept(this);
		loop.getBody().accept(this);
    }

    @Override
    public void visit(Write write) {
        Program.addMessage(write.toString());
		write.getArg().accept(this);
    }
}
