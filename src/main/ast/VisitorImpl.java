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

		program.createClassSymbolTableHierarchy();

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
                    String newName = "temp" + Program.getNewTempVar() + "-" + name;
                    // it will make problems if we have multiple classes with one name
//                    classDeclaration.setName(new Identifier(newName, classDeclaration.getLineNum()));
                    SymbolTable.top.put(new SymbolTableClassItem(newName)
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
                    Program.addError(
                        "line:" + classDeclaration.getLineNum() +
                                ":class " + parentName + " is not declared"
                    , PhaseNum.three);
                }
            }

            SymbolTable.push(new SymbolTable(Program.getClassSymbolTable(name)));

            classDeclaration.getName().accept(this);
            if(classDeclaration.hasParent())
                classDeclaration.getParentName().accept(this, 1); //TODO: CLEAN THIS MESS UP!
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
                    String newName = "temp" + Program.getNewTempVar() + "-" + name;
//                    methodDeclaration.setName(new Identifier(newName, methodDeclaration.getLineNum()));
                    SymbolTable.top.put(new SymbolTableMethodItem(newName, argsType)
                    ); // now what?!?
                }
                catch (ItemAlreadyExistsException e1){
                    // ?!?
                }
                Program.addError(
                    "line:" + methodDeclaration.getLineNum() +
                            ":Redefinition of method " + name
                , PhaseNum.two);
            }
        }
        else if(Program.passNum == 2){
            Program.addMessage(methodDeclaration.toString());

            Type varType = methodDeclaration.getReturnType();
            try{
                setUserDefinedType(varType);
            } catch (Exception e){
                //TODO:?
            }

            SymbolTable.push(new SymbolTable(SymbolTable.top));

            methodDeclaration.getName().accept(this, 1);
            for(VarDeclaration arg : methodDeclaration.getArgs())
                arg.accept(this, VarVisitType.InMethod);
            for(VarDeclaration localVar : methodDeclaration.getLocalVars())
                localVar.accept(this, VarVisitType.InMethod);
            for(Statement stm : methodDeclaration.getBody()){
                stm.accept(this);
            }
            Expression returnValue = methodDeclaration.getReturnValue();
            returnValue.accept(this);

            if(!canBeReturnValue(methodDeclaration.getReturnType(), returnValue.getType())){
                Program.addError(
                        "line:"+returnValue.getLineNum()+":return type must be "+methodDeclaration.getReturnType(),
                        PhaseNum.three
                );
            }

            SymbolTable.pop();
        }
    }

    private boolean canBeReturnValue(Type methodType, Type retValType) {
        if (methodType instanceof NoType || retValType instanceof NoType)
            return false;

        /// TODO: check the rvalue
        if ((Program.isPrimitiveType(methodType.toString())) || Program.isPrimitiveType(retValType.toString()))
            return methodType.toString().equals(retValType.toString());

        // now we are dealing with UserDefinedTypes
        try{
            ClassDeclaration leftClass = Program.getClass(((UserDefinedType)methodType).getName().getName());
            ClassDeclaration parent = Program.getClass(((UserDefinedType)retValType).getName().getName());
            return checkPolymorphism(leftClass, parent);
        } catch (Exception e){
            return false;
        }
    }

    private boolean checkPolymorphism(ClassDeclaration leftClass, ClassDeclaration parent) throws Exception {
        while(true){
            if(parent.getName().getName().equals(leftClass.getName().getName()))
                return true;
            if(!parent.hasParent())
                break;
            parent = Program.getClass(parent.getParentName().getName());
        }
        return false;
    }

    private void setUserDefinedType(Type varType) throws Exception {
        if(varType instanceof UserDefinedType){
            String className = ((UserDefinedType)varType).getName().getName();
            ((UserDefinedType) varType).setClassDeclaration(Program.getClass(className));
        }
    }

    @Override
    public void visit(VarDeclaration varDeclaration, VarVisitType visitType) {
        String name = varDeclaration.getIdentifier().getName();
        if(Program.passNum == 1) {
            putVariableItemToTopSymbolTable(varDeclaration, name);
        }
        else if(Program.passNum == 2){
            Program.addMessage(varDeclaration.toString());

            // if we're in accepting a local variable of a method,
            // we should try to put it in symbol table
            if(visitType == VarVisitType.InMethod)
                putVariableItemToTopSymbolTable(varDeclaration, name);
            varDeclaration.getIdentifier().accept(this); //in chi? :D
            Type varType = varDeclaration.getType();
            try{
                setUserDefinedType(varType);
            } catch (Exception e){
                Program.addError(
                        "line:" + ((UserDefinedType) varType).getName().getLineNum() +
                                ":class " + ((UserDefinedType) varType).getName().getName() + " is not declared"
                        , PhaseNum.three);
                varDeclaration.setType(new NoType());
            }
            //TODO: Shadow when first we are declaring variable in method
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
        arrayCall.setLvalue(true);
        if(!(instance.getType() instanceof ArrayType))
            operandError(arrayCall, instance);
        Expression index = arrayCall.getIndex();
        index.accept(this);
        if(!(index.getType() instanceof IntType|| index.getType() instanceof NoType))
            operandError(arrayCall, index);
        arrayCall.setType(new IntType());
    }

    private void operandError(ArrayCall arrayCall, Expression expression) {
        if(!(expression.getType() instanceof NoType) && expression.getType() != null)
            Program.addError(
                    "line:"+arrayCall.getLineNum()+":unsupported operand type for "+expression.getType(),
                    PhaseNum.three
            );
        expression.setType(new NoType());
    }

    @Override
    public void visit(BinaryExpression binaryExpression) {
        Program.addMessage(binaryExpression.toString());
    	Expression left = binaryExpression.getLeft();
    	if(left instanceof Identifier)
    	    ((Identifier) left).accept(this, 1);
    	else left.accept(this);
    	//TODO: Check rvalue and lvalue for left and right operands
    	Expression right = binaryExpression.getRight();
        if(right instanceof Identifier)
            ((Identifier) right).accept(this, 1);
        else {
            right.accept(this);
        }
    	BinaryOperator.OperatorTypes operator = binaryExpression.getBinaryOperator();
    	if(operator.equals(BinaryOperator.OperatorTypes.assign)){
    	    if(isCompatibleForAssignment(left, right))
				binaryExpression.setType(left.getType());
    	    else
    	        binaryExpression.setType(new NoType());

			binaryExpression.setLvalue(left.isLvalue());

		} else {
    	    if(operator.equals(BinaryOperator.OperatorTypes.eq) || operator.equals(BinaryOperator.OperatorTypes.neq)){
    	        if((Program.isPrimitiveType(left.getType().toString())
                        && left.getType().toString().equals(right.getType().toString()))
                        || left.getType() instanceof NoType || right.getType() instanceof NoType
                ) {
    	            binaryExpression.setType(new BooleanType());
                } else {
    	            binaryExpression.setType(new NoType());
    	            Program.addError(
    	                    "line:"+binaryExpression.getLineNum()+":unsupported operand type for " + operator.name(),
                            PhaseNum.three
                    );
                }
            } else {
    	        if(operator.equals(BinaryOperator.OperatorTypes.and) || operator.equals(BinaryOperator.OperatorTypes.or)){
    	            if((left.getType().toString().equals("bool") && right.getType().toString().equals("bool"))
                            || left.getType() instanceof NoType || right.getType() instanceof NoType
                    ){
                        binaryExpression.setType(new BooleanType());
                    } else {
                        binaryExpression.setType(new NoType());
                        Program.addError(
                                "line:"+binaryExpression.getLineNum()+":unsupported operand type for " + operator.name(),
                                PhaseNum.three
                        );
                    }
                } else { // */+-<>
                    if((left.getType().toString().equals("int") && right.getType().toString().equals("int"))
                            || left.getType() instanceof NoType || right.getType() instanceof NoType
                    ){
                        if(operator.equals(BinaryOperator.OperatorTypes.gt) || operator.equals(BinaryOperator.OperatorTypes.lt))
                            binaryExpression.setType(new BooleanType());
                        else{
                            binaryExpression.setType(new IntType());
                        }
                    } else {
                        binaryExpression.setType(new NoType());
                        Program.addError(
                                "line:"+binaryExpression.getLineNum()+":unsupported operand type for " + operator.name(),
                                PhaseNum.three
                        );
                    }
                }
            }
            binaryExpression.setLvalue(false);
        }
    }

    private boolean isCompatibleForAssignment(Expression left, Expression right) {
        if (left.getType() instanceof NoType || right.getType() instanceof NoType)
            return true;

        if (checkIfTypesAreNull(left)) return true;
        if (checkIfTypesAreNull(right)) return true;

        if ((Program.isPrimitiveType(left.getType().toString())))
            return left.getType().toString().equals(right.getType().toString());

        try{
            ClassDeclaration leftClass = Program.getClass(((UserDefinedType)left.getType()).getName().getName());
            ClassDeclaration parent = Program.getClass(((UserDefinedType)right.getType()).getName().getName());
            return checkPolymorphism(leftClass, parent);
        } catch (Exception e){
            return false;
        }
    }

    private boolean checkIfTypesAreNull(Expression expression) {
        if(expression.getType() == null && expression instanceof Identifier){
            String varName = ((Identifier)expression).getName();
            expression.setType(new NoType());
            try{
                SymbolTable.top.put(new SymbolTableVariableItem(varName, expression.getType()));
            } catch (ItemAlreadyExistsException e1) {
                // WTF!
            }
            Program.addError(
                    "line:"+expression.getLineNum()+":variable "+varName+" is not declared",
                    PhaseNum.three
            );
            return true;
        }
        return false;
    }

    public void visit(Identifier identifier, int mode){
        try{
            SymbolTable.top.get(identifier.getName()+"@class");
            SymbolTable.top.get(identifier.getName()+"@method");
        } catch (ItemNotFoundException e){
            identifier.setType(new NoType());
            //NO ERROR MESSAGE ADDING?
        }
    }

    @Override
    public void visit(Identifier identifier) {
        Program.addMessage(identifier.toString());
        try{
            identifier.setLvalue(true);     // Identifier is Lvalue
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
                    try{
                        SymbolTable.top.put(new SymbolTableVariableItem(identifier.getName(), identifier.getType()));
                    } catch (ItemAlreadyExistsException e3) {
                        // WTF!
                    }
                    Program.addError(
                            "line:"+identifier.getLineNum()+":variable "+identifier.getName()+
                                    " is not declared",
                            PhaseNum.three
                    );
                }
            }
        }
        //TODO: Age identifier esme class bashe, mgie variable not declared. toye parente class mige, ke ezafas. toye newClass ham mige. ke nabayad bege.
        //TODO: Baraye in 2 halat, chon nameshon hamishe identifiere, vase visite identifier argumente 2vom bezarim? ke modesho mosakhas kone
    }

    @Override
    public void visit(Length length) {
        Program.addMessage(length.toString());
    	Expression expression = length.getExpression();
    	expression.accept(this);
    	length.setLvalue(false);
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
    	methodCall.setLvalue(false);
        String methodName = methodCall.getMethodName().getName();
        Expression instance = methodCall.getInstance();
        instance.accept(this); // to fill return types from first
        methodCall.getMethodName().accept(this, 1);
        for(Expression arg : methodCall.getArgs())
            arg.accept(this);

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
                methodCall.setType(new NoType());
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
                            methodCall.setType(new NoType());
                            Program.addError(
                                    "line:" + methodCall.getLineNum() +
                                            ":class " + className + " is not declared"
                                    , PhaseNum.three);
                        }
                    } else {
                        methodCall.setType(new NoType());
                        //TODO: ANYTHING ELSE?
                    }
                }
            } catch (ItemNotFoundException e){
                instance.setType(new NoType());
                try{
                    SymbolTable.top.put(new SymbolTableVariableItem(instanceVarName, instance.getType()));
                } catch (ItemAlreadyExistsException e1) {
                    // WTF!
                }
                Program.addError(
                        "line:" + methodCall.getMethodName().getLineNum() +
                                ":variable " + instanceVarName +
                                " is not declared"
                        , PhaseNum.three);
                methodCall.setType(new NoType());
            }
        }
        else if(instance instanceof This){
            String className = Program.currentClass;
            try {
                ClassDeclaration classObj = Program.getClass(className);
                setMethodType(methodCall, methodName, instance, className, classObj);
            } catch (Exception e) {
                e.printStackTrace();
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
                    setMethodType(methodCall, methodName, instance, instanceTypeName, instanceClass);
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
    }

    private void setMethodType(MethodCall methodCall, String methodName, Expression instance, String className, ClassDeclaration classObj) {
        try{
            Type returnType = classObj.getMethodReturnType(methodName);
            methodCall.setType(returnType);
        } catch (Exception e){
            //method not found :shrug:
            Program.addError(
                    "line:" + instance.getLineNum() +
                            ":there is no method named " + methodName +
                            " in class " + className,
                    PhaseNum.three
            );
            methodCall.setType(new NoType());
        }
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
    	newArray.setLvalue(false);
    }

    @Override
    public void visit(NewClass newClass) {
        Program.addMessage(newClass.toString());

		Identifier className = newClass.getClassName();
        className.accept(this, 1); //TODO: CLEAN THIS MESS UP!
        try{
            Program.getClass(className.getName());
            newClass.setType(new UserDefinedType(className));
        } catch (Exception e){
            newClass.setType(new NoType());
        }
        newClass.setLvalue(false);

    }

    @Override
    public void visit(This instance) {
        Program.addMessage(instance.toString());
        instance.setType(new UserDefinedType(new Identifier(Program.currentClass, instance.getLineNum())));
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
        } else{
            unaryExpression.setType(new NoType());
            if(!(value.getType() instanceof NoType))
                Program.addError(
                        "line:"+unaryExpression.getLineNum()+":unsupported operand type for "+unaryExpression.getUnaryOperator(),
                        PhaseNum.three
                );
        }

        unaryExpression.setLvalue(false);
    }
//TODO: Atfe Manteqi: && ||
    @Override
    public void visit(BooleanValue value) {
        Program.addMessage(value.toString());
    }

    @Override
    public void visit(IntValue value) {
        Program.addMessage(value.toString());
        value.setLvalue(false);
    }

    @Override
    public void visit(StringValue value) {
        Program.addMessage(value.toString());
        value.setLvalue(false);
    }

    @Override
    public void visit(Assign assign) {
        Program.addMessage(assign.toString());

		Expression lvalue = assign.getlValue();
		lvalue.accept(this);
		Expression rvalue = assign.getrValue();
		rvalue.accept(this);

        if(!isCompatibleForAssignment(lvalue, rvalue)) {
            Program.addError(
                    "line:"+assign.getLineNum()+":unsupported operand type for "+BinaryOperator.OperatorTypes.assign,
                    PhaseNum.three
            );
        }

        else if (!lvalue.isLvalue()) {
			Program.addError(
					"line:"+assign.getLineNum()+":left side of assignment must be a valid lvalue",
					PhaseNum.three
			);
		}
    }

    @Override
    public void visit(Block block) {
        Program.addMessage(block.toString());

		for(Statement stm : block.getBody())
			stm.accept(this);
    }

    private void checkForWrongCondition(Statement statement, Expression condition){
        if(condition instanceof BinaryExpression){
            if(!BinaryOperator.isBooleanOperator(((BinaryExpression) condition).getBinaryOperator())) Program.addError(
                    "line:" + statement.getLineNum() + ":condition type must be boolean"
                    , PhaseNum.three);
        }
        else if(!(condition instanceof BooleanValue)) Program.addError(
                "line:" + statement.getLineNum() + ":condition type must be boolean"
                , PhaseNum.three
        );
    }

    @Override
    public void visit(Conditional conditional) {
        Program.addMessage(conditional.toString());
		Expression condition = conditional.getExpression();
        condition.accept(this);
        checkForWrongCondition(conditional, condition);
        /* TODO: if has errors, change type of condition? */
        conditional.getConsequenceBody().accept(this);
        if(conditional.hasAlternativeBody())
           conditional.getAlternativeBody().accept(this);
    }

    @Override
    public void visit(MethodCallInMain methodCallInMain) {
        //TODO: implement appropriate visit functionality
    }

    @Override
    public void visit(While loop) {
        Program.addMessage(loop.toString());
        Expression condition = loop.getCondition();
        condition.accept(this);
        checkForWrongCondition(loop, condition);

        //TODO: if has errors, change type of condition?
		loop.getBody().accept(this);
    }

    @Override
    public void visit(Write write) {
        Program.addMessage(write.toString());
		Expression argument = write.getArg();
        argument.accept(this);
        if(!Program.doesWritelnSupport(argument)){
            argument.setType(new NoType());
            Program.addError(
                    "line:"+write.getLineNum()+":unsupported type for writeln",
                    PhaseNum.three
            );
        }
    }
}
