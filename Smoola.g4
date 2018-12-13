grammar Smoola;

@members{
   void print(Object obj){
        System.out.println(obj);
   }
}

@parser::header {
    import ast.node.declaration.*;
    import ast.node.expression.*;
    import ast.node.statement.*;
    import ast.Type.ArrayType.*;
    import ast.Type.PrimitiveType.*;
    import ast.Type.UserDefinedType.*;
    import ast.Type.*;
    import ast.VisitorImpl;
    import ast.node.expression.Value.*;
    import ast.node.Program;
    import symbolTable.SymbolTable;
    import ast.node.PhaseNum;
    import ast.node.expression.BinaryOperator.OperatorTypes;
}

    program returns [Program p]:
        { $p = new Program(); }
        y = mainClass { $p.setMainClass($y.c); } ( x = classDeclaration { $p.addClass($x.c); } )* EOF
    ;

    mainClass returns [ClassDeclaration c]:
        // name should be checked later
        'class' class_name = ID '{' 'def' method_name = ID '(' ')' ':' 'int' '{' st = statements 'return' rv = expression ';' '}' '}'
        {
            String classname = $class_name.getText();
            String parentname = null;

            $c = new ClassDeclaration(new Identifier(classname, $class_name.getLine()),
                new Identifier(parentname, $class_name.getLine())
                );
            $c.setLineNum($class_name.getLine());

            MethodDeclaration mainMethod = new MethodDeclaration(
                new Identifier($method_name.getText(),
                    $method_name.getLine())
            );

            mainMethod.setReturnValue($rv.exp);
            mainMethod.setReturnType(new IntType());
            mainMethod.setBody($st.stmts);
            mainMethod.setLineNum($method_name.getLine());
            $c.addMethodDeclaration(mainMethod);
        }
    ;

    classDeclaration returns [ClassDeclaration c]:
        {
            boolean has_parent = false;
            ArrayList<VarDeclaration> vars;
        }

        'class' name = ID
        ('extends' pname = ID { has_parent = true; })?
        {
            String classname = $name.getText();
            String parentname = (has_parent) ? $pname.getText() : null;
            $c = new ClassDeclaration(
                new Identifier(classname, $name.getLine()),
                (has_parent)? new Identifier(parentname, $pname.getLine()) : new Identifier("Object", 0)
            );
        }
        '{'
        (var = varDeclaration { $c.addVarDeclaration($var.var); })*
        (method = methodDeclaration { $c.addMethodDeclaration($method.m); })*
        '}'
    ;

    varDeclaration returns [VarDeclaration var]:
        'var' (id = ID)':' (t = type) ';'
        {
            Identifier var_id = new Identifier($id.getText(), $id.getLine());
            $var = new VarDeclaration(var_id, $t.t);
            $var.setLineNum($id.getLine());
        }
    ;

    methodDeclaration returns [MethodDeclaration m]:
        'def' name = ID
        {
            Identifier name = new Identifier($name.getText(), $name.getLine());
            $m = new MethodDeclaration(name);
            $m.setLineNum($name.getLine());
        }
        (
            '(' ')'
            |
            (
                '('
                id = ID { Identifier var0_id = new Identifier($id.getText(), $id.getLine()); }
                ':'
                t = type { $m.addArg(new VarDeclaration(var0_id, $t.t)); }
                (
                    ','
                    id = ID { Identifier var_id = new Identifier($id.getText(), $id.getLine()); }
                    ':'
                    t = type { $m.addArg(new VarDeclaration(var_id, $t.t)); }
                )*
                ')'
            )
        )
        ':'
        ret_type = type { $m.setReturnType($ret_type.t); }
        '{'
        (
            var = varDeclaration { $m.addLocalVar($var.var); }
        )*
        stmts = statements { $m.setBody($stmts.stmts); }
        'return'
        rv = expression { $m.setReturnValue($rv.exp); }
        ';'
        '}'
    ;

    statements returns [ArrayList<Statement> stmts]:
        { $stmts = new ArrayList<Statement>(); }
        ( stm = statement { $stmts.add($stm.stm); })*
    ;

    statement returns [Statement stm]:
        blk = statementBlock { $stm = $blk.blk; }
        |
        cond = statementCondition { $stm = $cond.cond; }
        |
        loop = statementLoop { $stm = $loop.loop; }
        |
        write = statementWrite { $stm = $write.write; }
        |
        assign = statementAssignment { $stm = $assign.assign; }
    ;

    statementBlock returns [Block blk]:
        '{'  stmts = statements '}' { $blk = new Block($stmts.stmts); }
    ;

    statementCondition returns [Conditional cond]:
        { boolean has_alt = false; }
        'if' '(' exp = expression')' 'then' stmc = statement ('else' stma = statement { has_alt = true; })?
        {
            $cond = new Conditional($exp.exp, $stmc.stm);
            if(has_alt)
                $cond.setAlternativeBody($stma.stm);
        }
    ;

    statementLoop returns [While loop]:
        'while' '(' exp = expression ')' stm = statement
        { $loop = new While($exp.exp, $stm.stm); }
    ;

    statementWrite returns [Write write]:
        'writeln(' arg = expression ')' ';'
        { $write = new Write($arg.exp); }
    ;

    statementAssignment returns [Statement assign]:
        exp = expression semicolon = ';'
        {
            if ($exp.exp instanceof BinaryExpression) {
                if (((BinaryExpression)$exp.exp).getBinaryOperator() == BinaryOperator.OperatorTypes.assign)
                    $assign = new Assign(((BinaryExpression)$exp.exp).getLeft(), ((BinaryExpression)$exp.exp).getRight());
            }
            else
                print("Error at line "+ $semicolon.getLine());
        }
    ;

    expression returns [Expression exp]:
		xp = expressionAssignment
		{ $exp = $xp.exp; }
	;

    expressionAssignment returns [Expression exp]:
		(lvalue = expressionOr) '=' (rvalue = expressionAssignment)
		{ $exp = new BinaryExpression($lvalue.exp, $rvalue.exp, BinaryOperator.OperatorTypes.assign); }
	    |
	    or = expressionOr { $exp = $or.exp; }
	;

    expressionOr returns [Expression exp]:
		(left = expressionAnd) (right = expressionOrTemp[$left.exp])
		{ $exp = $right.exp; }
	;

    expressionOrTemp[Expression lvalue] returns [Expression exp]:
		'||'
		rvalue = expressionAnd
		{ BinaryExpression tmp = new BinaryExpression(lvalue, $rvalue.exp, BinaryOperator.OperatorTypes.or); }
		rv = expressionOrTemp[tmp]
		{ $exp = $rv.exp; }
	    | { $exp = lvalue; }
	;

    expressionAnd returns [Expression exp]:
		(left = expressionEq) (right = expressionAndTemp[$left.exp])
		{ $exp = $right.exp; }
	;

    expressionAndTemp[Expression lvalue] returns [Expression exp]:
		'&&'
		rvalue = expressionEq
		{ BinaryExpression tmp = new BinaryExpression(lvalue, $rvalue.exp, BinaryOperator.OperatorTypes.and); }
		rv = expressionAndTemp[tmp]
		{ $exp = $rv.exp; }
	    | { $exp = lvalue; }
	;

    expressionEq returns [Expression exp]:
		(left = expressionCmp) (right = expressionEqTemp[$left.exp])
		{ $exp = $right.exp; }
	;

    expressionEqTemp[Expression lvalue] returns [Expression exp]:
        { BinaryOperator.OperatorTypes operatorType; }
		('==' { operatorType = BinaryOperator.OperatorTypes.eq; } | '<>' { operatorType = BinaryOperator.OperatorTypes.neq; })
		rvalue = expressionCmp { BinaryExpression tmp = new BinaryExpression(lvalue, $rvalue.exp, operatorType); }
		rv = expressionEqTemp[tmp] { $exp = $rv.exp; }
	    | { $exp = lvalue; }
	;

    expressionCmp returns [Expression exp]:
		(left = expressionAdd) (right = expressionCmpTemp[$left.exp])
		{ $exp = $right.exp; }
	;

    expressionCmpTemp[Expression lvalue] returns [Expression exp]:
        { BinaryOperator.OperatorTypes operatorType; }
		('<' { operatorType = BinaryOperator.OperatorTypes.lt; } | '>' { operatorType = BinaryOperator.OperatorTypes.gt; })
		rvalue = expressionAdd
		{ BinaryExpression tmp = new BinaryExpression(lvalue, $rvalue.exp, operatorType); }
		rv = expressionCmpTemp[tmp]
		{ $exp = $rv.exp; }
	    | { $exp = lvalue; }
	;

    expressionAdd returns [Expression exp]:
		(left = expressionMult) (right = expressionAddTemp[$left.exp])
		{ $exp = $right.exp; }
	;

    expressionAddTemp[Expression lvalue] returns [Expression exp]:
		{ BinaryOperator.OperatorTypes operatorType; }
		('+' { operatorType = BinaryOperator.OperatorTypes.add; } | '-' { operatorType = BinaryOperator.OperatorTypes.sub; })
		rvalue = expressionMult { BinaryExpression tmp = new BinaryExpression(lvalue, $rvalue.exp, operatorType); }
		rv = expressionAddTemp[tmp] { $exp = $rv.exp; }
	    | { $exp = lvalue; }
	;

    expressionMult returns [Expression exp]:
		(left = expressionUnary) (right = expressionMultTemp[$left.exp])
		{ $exp = $right.exp; }
	    ;

    expressionMultTemp[Expression lvalue] returns [Expression exp]:
        { BinaryOperator.OperatorTypes operatorType; }
		('*' { operatorType = BinaryOperator.OperatorTypes.mult; } | '/' { operatorType = BinaryOperator.OperatorTypes.div; })
		rvalue = expressionUnary
		{ BinaryExpression tmp = new BinaryExpression(lvalue, $rvalue.exp, operatorType); }
		rv = expressionMultTemp[tmp]
		{ $exp = $rv.exp; }
	    | { $exp = lvalue; }
	;

    expressionUnary returns [Expression exp]:
        { UnaryOperator unaryOperator; }
		('!' { unaryOperator = UnaryOperator.not; } | '-' { unaryOperator = UnaryOperator.minus; }) value = expressionUnary
		{ $exp = new UnaryExpression(unaryOperator, $value.exp); }
	    |	expMem = expressionMem
	    { $exp = $expMem.exp; }
	;

    expressionMem returns [Expression exp]:
		(instance = expressionMethods) (result = expressionMemTemp[$instance.exp])
		{ $exp = $result.exp; }
	;

    expressionMemTemp[Expression instance] returns [Expression exp]:
		'[' index = expression ']'
		{ $exp = new ArrayCall(instance, $index.exp); }
	    | { $exp = instance; }
	;

	expressionMethods returns [Expression exp]:
	    (instance = expressionOther) (call = expressionMethodsTemp[$instance.exp])
	    { $exp = $call.exp; }
	;

	expressionMethodsTemp[Expression instance] returns [Expression exp]:
         { Expression e = null; }
	     '.'
                (
	            name = ID '(' ')'
	                { e = new MethodCall(instance, new Identifier($name.getText(), $name.getLine())); }
	            | name = ID { e = new MethodCall(instance, new Identifier($name.getText(), $name.getLine())); }
                    '(' (exp1 = expression { ((MethodCall)e).addArg($exp1.exp); })
                    (',' exp2 = expression { ((MethodCall)e).addArg($exp2.exp); })* ')'
	            | 'length' { e = new Length(instance); }
	            )
	    tmp = expressionMethodsTemp[e] { $exp = $tmp.exp; }
	    | { $exp = instance; }
	;

    expressionOther returns [Expression exp]:
		num = CONST_NUM { $exp = new IntValue(Integer.parseInt($num.getText()), new IntType(), $num.getLine()); }
        |	str = CONST_STR { $exp = new StringValue($str.getText(), new StringType(), $str.getLine()); }
        |   'new ' 'int' '[' size = CONST_NUM ']'
            {
                $exp = new NewArray();
                $exp.setLineNum($size.getLine());
                ((NewArray)$exp).setExpression(new IntValue(Integer.parseInt($size.getText()), new IntType(), $size.getLine()));
            }
        |   'new ' name = ID '(' ')' { $exp = new NewClass(new Identifier($name.getText(), $name.getLine())); } // set classdeclaration in symbol table
        |   e = 'this' { $exp = new This($e.getLine()); }
        |   e = 'true' { $exp = new BooleanValue(true, new BooleanType(), $e.getLine()); }
        |   e = 'false' { $exp = new BooleanValue(false, new BooleanType(), $e.getLine()); }
        |	name = ID { $exp = new Identifier($name.getText(), $name.getLine()); }
        |   name = ID '[' index = expression ']'
            { $exp = new ArrayCall(new Identifier($name.getText(), $name.getLine()), $index.exp); }
        |	'(' xp = expression ')' { $exp = $xp.exp; }
	;

	type returns [Type t]:
	    'int' { $t = new IntType(); }
	    |
	    'boolean' { $t = new BooleanType(); }
	    |
	    'string' { $t = new StringType(); }
	    |
	    'int' '[' ']' { $t = new ArrayType(); }
	    |
	    name = ID { $t = new UserDefinedType(new Identifier($name.getText(), $name.getLine())); }
	;

    CONST_NUM:
		[0-9]+
	;

    CONST_STR:
		'"' ~('\r' | '\n' | '"')* '"'
	;

    NL:
		'\r'? '\n' -> skip
	;

    ID:
		[a-zA-Z_][a-zA-Z0-9_]*
	;

    COMMENT:
		'#'(~[\r\n])* -> skip
	;

    WS:
    	[ \t] -> skip
    ;