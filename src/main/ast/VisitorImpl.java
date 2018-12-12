package ast;

import ast.Type.ArrayType.ArrayType;
import ast.Type.NoType;
import ast.Type.PrimitiveType.BooleanType;
import ast.Type.PrimitiveType.IntType;
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

            SymbolTable.push(new SymbolTable(SymbolTable.top));

            for(VarDeclaration vdec : classDeclaration.getVarDeclarations())
                vdec.accept(this, VarVisitType.InClass);
            for(MethodDeclaration mdec : classDeclaration.getMethodDeclarations())
                mdec.accept(this);

            Program.addClassSymbolTable(name, SymbolTable.top);
            SymbolTable.pop();
        }
        else if (Program.passNum == 2){
            Program.currentClass = classDeclaration.getName().getName();
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
            //TODO: Shadow when first we are declaring variable in method
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
        Expression instance = arrayCall.getInstance();
        // instance must be array type
        instance.accept(this);
        if(!(instance.getType() instanceof ArrayType)){
            //TODO: Is message below right for this error?
            Program.addError(
                    "line:"+arrayCall.getLineNum()+":unsupported operand type for "+instance.getType(),
                    PhaseNum.three
            );
            instance.setType(new NoType()); //TODO: right?
        }
        Expression index = arrayCall.getIndex();
        index.accept(this);
        if(!(index instanceof IntValue || index.getType() instanceof NoType)){
            Program.addError(
                    "line:"+arrayCall.getLineNum()+":unsupported operand type for "+index.getType(),
                    PhaseNum.three
            );
            index.setType(new NoType());
        }
        arrayCall.setType(new IntType());
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        Program.addMessage(binaryExpression.toString());
    	Expression left = binaryExpression.getLeft();
    	left.accept(this);
    	//TODO: Check rvalue and lvalue for left and right operands
    	Expression right = binaryExpression.getRight();
    	right.accept(this);
    	BinaryOperator.OperatorTypes operator = binaryExpression.getBinaryOperator();
    	if(operator.equals(BinaryOperator.OperatorTypes.assign)){
    	    if(isCompatibleForAssignment(left, right))
                binaryExpression.setType(left.getType());
    	    else
    	        binaryExpression.setType(new NoType());
        } else {
    	    if(operator.equals(BinaryOperator.OperatorTypes.eq) || operator.equals(BinaryOperator.OperatorTypes.neq)){
    	        if(Program.isPrimitiveType(left.getType().toString()) && left.getType().toString().equals(right.getType().toString())){
    	            binaryExpression.setType(new BooleanType());
                } else {
    	            Program.addError(
    	                    "line:"+binaryExpression.getLineNum()+":unsupported operand type for " + operator.name(),
                            PhaseNum.three
                    );
                }
            } else {
    	        if(left.getType().toString().equals("int") && right.getType().toString().equals("int")){
    	            binaryExpression.setType(new IntType());
                } else {
                    Program.addError(
                            "line:"+binaryExpression.getLineNum()+":unsupported operand type for " + operator.name(),
                            PhaseNum.three
                    );
                }
            }
        }
    }

    private boolean isCompatibleForAssignment(Expression left, Expression right) {
        System.out.println("ASSCOMP LINE "+left.getLineNum()+" "+left.getType()+" "+right.getType());

        if ((left.getType() instanceof NoType) || (right.getType() instanceof NoType))
            return true;

        /// TODO: check the rvalue
        if ((Program.isPrimitiveType(left.getType().toString())))
            return left.getType().toString().equals(right.getType().toString());

        try{
            ClassDeclaration leftClass = Program.getClass(((UserDefinedType)left.getType()).getName().getName());
            ClassDeclaration parent = Program.getClass(((UserDefinedType)right.getType()).getName().getName());
            do {
                if(parent.getName().getName().equals(leftClass.getName().getName()))
                    return true;
                parent = Program.getClass(parent.getParentName().getName());
            } while(parent.hasParent());
            return false;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public void visit(Identifier identifier) {
        try{
            SymbolTableItem item = SymbolTable.top.get(identifier.getName()+"@var");
            Type type = ((SymbolTableVariableItem)item).getType();
            identifier.setType(type);
        } catch (ItemNotFoundException e){
            try{
                SymbolTable.top.get(identifier.getName()+"@method");
            } catch (ItemNotFoundException e1){
                try{
                    SymbolTable.top.get(identifier.getName()+"@class");
                } catch (ItemNotFoundException e2){
                    identifier.setType(new NoType());
                    Program.addError(
                            "line:"+identifier.getLineNum()+":variable "+identifier.getName()+
                                    " is not declared",
                            PhaseNum.three
                    );
                }
            }
        }
        Program.addMessage(identifier.toString());
    }

    @Override
    public void visit(Length length) {
        //DONE! [Not Verified]
        Program.addMessage(length.toString());
    	Expression expression = length.getExpression();
    	expression.accept(this);
    	if(!(expression.getType() instanceof ArrayType) && !(expression.getType() instanceof NoType)){
    	    Program.addError(
                    "line:"+length.getLineNum()+":length is only allowed on arrays",
                    PhaseNum.three
            );
        }
    	length.setType(new IntType());
    }

    private boolean isMethodInClass(ClassDeclaration classDeclaration, String methodName){
        for(MethodDeclaration method : classDeclaration.getMethodDeclarations())
            if (method.getName().getName().equals(methodName))
                return true;
        return false;
    }

    @Override
    public void visit(MethodCall methodCall) {
    	Program.addMessage(methodCall.toString());

        String methodName = methodCall.getMethodName().getName();
        Expression instance = methodCall.getInstance();
        methodCall.getInstance().accept(this); // to fill return types from first

        if(instance instanceof NewClass){
            String className = ((NewClass) instance).getClassName().getName();
            try {
                ClassDeclaration classDeclaration = Program.getClass(className);
                if (isMethodInClass(classDeclaration, methodName))
                    methodCall.setType(classDeclaration.getMethodReturnType(methodName));
                else {
                    Program.addError(
                            "line:" + ((NewClass) instance).getClassName().getLineNum() +
                                    ":there is no method named " + methodName +
                                    " in class " + className
                            , PhaseNum.three);
                    methodCall.setType(new NoType());
                }
            } catch (Exception e){
                Program.addError(
                    "line:" + methodCall.getLineNum() +
                            ":class " + className + " is not declared"
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
                        try {
                            ClassDeclaration classDeclaration = Program.getClass(className);
                            if (isMethodInClass(classDeclaration, methodName)){
                                methodCall.setType(classDeclaration.getMethodReturnType(methodName));
                            }
                            else {
                                Program.addError(
                                        "line:" + instance.getLineNum() +
                                                ":there is no method named " + methodName +
                                                " in class " + className
                                        , PhaseNum.three);
                                methodCall.setType(new NoType());
                            }
                        } catch (Exception e){
                            Program.addError(
                                    "line:" + methodCall.getLineNum() +
                                            ":class " + className + " is not declared"
                                    , PhaseNum.three);
                        }
                    }
                }
            } catch (ItemNotFoundException e){
                Program.addError(
                        "line:" + methodCall.getMethodName().getLineNum() +
                                ":variable " + instanceVarName +
                                " is not declared"
                        , PhaseNum.three);
            }
        }
        else if(instance instanceof This){
            if(Program.passNum == 2){
                String className = Program.currentClass;
                try {
                    ClassDeclaration classObj = Program.getClass(className);
                    methodCall.setType(classObj.getMethodReturnType(methodName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if(instance instanceof MethodCall){
            String instanceTypeName = instance.getType().toString();
            if(Program.isPrimitiveType(instanceTypeName)){
                Program.addError(
                        "line:"+instance.getLineNum()+":methods are not allowed on "+instanceTypeName,
                        PhaseNum.three
                );
            } else {
                try {
                    ClassDeclaration instanceClass = Program.getClass(instanceTypeName);
                    try {
                        Type returnType = instanceClass.getMethodReturnType(methodName);
                        methodCall.setType(returnType);
                    }
                    catch (Exception e){
                        Program.addError(
                                "line:" + instance.getLineNum() +
                                        ":there is no method named " + methodName +
                                        " in class " + instanceTypeName,
                                PhaseNum.three
                        );
                        //TODO: NO TYPE?!
                        //methodCall.setReturnType(NoType);
                    }
                    ClassDeclaration classDeclaration = Program.getClass(instanceTypeName);
                    if (!isMethodInClass(classDeclaration, methodName)){
                        Program.addError(
                                "line:" + instance.getLineNum() +
                                        ":there is no method named " + methodName +
                                        " in class " + instanceTypeName,
                                PhaseNum.three
                        );
                    }
                } catch (Exception e){
                    // It's handled in visiting MethodDeclaration
                }
            }
        }

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
                        ":array length should not be zero or negative"
            , PhaseNum.two);
            ((IntValue)newArray.getExpression()).setConstant(0); // default value to 0 (?!)
        }
        else
            Program.addMessage(newArray.toString());
    	newArray.getExpression().accept(this);
    	newArray.setType(new ArrayType());
    }

    @Override
    public void visit(NewClass newClass) {
        Program.addMessage(newClass.toString());

		Identifier className = newClass.getClassName();
		newClass.setType(new UserDefinedType(className));
		className.accept(this);
    }

    @Override
    public void visit(This instance) {
        instance.setType(new UserDefinedType(new Identifier(Program.currentClass, instance.getLineNum())));
        Program.addMessage(instance.toString());
    }

    @Override
    public void visit(UnaryExpression unaryExpression) {
        Program.addMessage(unaryExpression.toString());

        Expression value = unaryExpression.getValue();
        value.accept(this);
        if(value.getType() instanceof IntType && unaryExpression.getUnaryOperator().equals(UnaryOperator.minus)){
            unaryExpression.setType(value.getType());
        } else if(value.getType() instanceof BooleanType && unaryExpression.getUnaryOperator().equals(UnaryOperator.not)) {
            unaryExpression.setType(value.getType());
        } else {
            unaryExpression.setType(new NoType());
            Program.addError(
    	            "line:"+unaryExpression.getLineNum()+":unsupported operand type for "+unaryExpression.getUnaryOperator(),
                    PhaseNum.three
            );
        }
    }
//TODO: Atfe Manteqi: && ||
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

		Expression lvalue = assign.getlValue();
		lvalue.accept(this);
		Expression rvalue = assign.getrValue();
		rvalue.accept(this);

		System.out.println(lvalue.getType()+" "+rvalue.getType()+" "+assign.getLineNum());
        if(!isCompatibleForAssignment(lvalue, rvalue))
            Program.addError(
                    "line:"+assign.getLineNum()+":unsupported operand type for "+BinaryOperator.OperatorTypes.assign,
                    PhaseNum.three
            );
    }

    @Override
    public void visit(Block block) {
        Program.addMessage(block.toString());

		for(Statement stm : block.getBody())
			stm.accept(this);

    }

    @Override
    public void visit(Conditional conditional) {
        Program.addMessage(conditional.toString());
		Expression condition = conditional.getExpression();
        if(condition instanceof BinaryExpression){
            if(!BinaryOperator.isBooleanOperator(((BinaryExpression) condition).getBinaryOperator())) Program.addError(
                    "line:" + conditional.getLineNum() + ":condition type must be boolean"
                    , PhaseNum.three);
        }
        else if(!(condition instanceof BooleanValue)) Program.addError(
                "line:" + conditional.getLineNum() + ":condition type must be boolean"
                , PhaseNum.three
        );
        /* TODO: if has errors, change type of condition? */
        condition.accept(this);
        conditional.getConsequenceBody().accept(this);
        if(conditional.hasAlternativeBody())
           conditional.getAlternativeBody().accept(this);
    }

    @Override
    public void visit(While loop) {
        Program.addMessage(loop.toString());
        Expression condition = loop.getCondition();
        if(condition instanceof BinaryExpression){
           if(!BinaryOperator.isBooleanOperator(((BinaryExpression) condition).getBinaryOperator())) Program.addError(
                   "line:" + loop.getLineNum() + ":condition type must be boolean"
                   , PhaseNum.three);
        }
        else if(!(condition instanceof BooleanValue)) Program.addError(
                "line:" + loop.getLineNum() + ":condition type must be boolean"
                , PhaseNum.three
        );
        //TODO: if has errors, change type of condition?
        condition.accept(this);
		loop.getBody().accept(this);
    }

    @Override
    public void visit(Write write) {
        Program.addMessage(write.toString());
		Expression argument = write.getArg();
		if(!Program.doesWritelnSupport(argument)){
            Program.addError(
                    "line:"+write.getLineNum()+":unsupported type for writeln",
                    PhaseNum.three
            );
        }
		argument.accept(this);
    }
}
