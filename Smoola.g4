grammar Smoola;

//@members{
//   void print(Object obj){
//        System.out.println(obj);
//   }
//}

@parser::header {
    import ast.node.declaration.*;
    import ast.node.expression.*;
    import ast.node.statement.*;
    import ast.Type.ArrayType.*;
    import ast.Type.PrimitiveType.*;
    import ast.Type.UserDefinedType.*;
    import ast.Type.*;
    import ast.VisitorImpl;
}

    program:
        {
            ArrayList<ClassDeclaration> classes = new ArrayList<>();
            VisitorImpl visitor = new VisitorImpl();
        }
        y = mainClass
        {
           classes.add($x.c);
        }
        ( x = classDeclaration
            {
                classes.add($x.c);
            }
        )* EOF

        {
            print("SALAM");

            for(int i = 0; i < classes.size(); ++i){
                ClassDeclaration item = classes.get(i);
                String class_name = item.getName().getName();
                String parent_name = item.getParentName().getName();

            }
        }
    ;

    mainClass returns [ClassDeclaration c]:
        // name should be checked later
        'class' class_name = ID '{' 'def' method_name = ID '(' ')' ':' 'int' '{' st = statements 'return' rv = expression ';' '}' '}'
        {
            String classname = $class_name.getText();
            String parentname = null;
            $c = new ClassDeclaration(new Identifier(classname), new Identifier(parentname));
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
            String parentname = (has_parent) ? $pname.getText() : null; // wtf?
            $c = new ClassDeclaration(new Identifier(classname), new Identifier(parentname));
        }
        '{'
        (var = varDeclaration { $c.addVarDeclaration($var.var); })*
        (method = methodDeclaration { $c.addMethodDeclaration($method.m); })*
        '}'
    ;

    varDeclaration returns [VarDeclaration var]:
        'var' (id = ID)':' (t = type) ';'
        {
            Identifier var_id = new Identifier($id.getText());
            $var = new VarDeclaration(var_id, $t.t);
        }
    ;

    methodDeclaration returns [MethodDeclaration m]:
    {
//        private Expression returnValue;
//        private Type returnType;
//        private Identifier name;
//        private ArrayList<VarDeclaration> args = new ArrayList<>();
//        private ArrayList<VarDeclaration> localVars = new ArrayList<>();
//        private ArrayList<Statement> body = new ArrayList<>();
    }

        'def' name = ID
        {
            Identifier name = new Identifier($name.getText());
            $m = new MethodDeclaration(name);
        }
        (
            '(' ')'
            |
            (
                '('
                id = ID
                {
                    Identifier var0_id = new Identifier($id.getText());
                }
                ':'
                t = type
                {
                    $m.addArg(new VarDeclaration(var0_id, $t.t));
                }
                (
                    ','
                    id = ID
                    {
                        Identifier var_id = new Identifier($id.getText());
                    }
                    ':'
                    t = type
                    {
                        $m.addArg(new VarDeclaration(var_id, $t.t));
                    }
                )*
                ')'
            )
        )
        ':'
        ret_type = type
        {
            $m.setReturnType($ret_type.t);
        }
        '{'
        (
            var = varDeclaration
            {
                $m.addLocalVar($var.var);
            }
        )*
        stmts = statements
        {
            $m.setBody($stmts.stmts);
        }
        'return'
        rv = expression { $m.setReturnValue($rv.exp); }
        ';'
        '}'
    ;

    statements returns [ArrayList<Statement> stmts]:
        {
            $stmts = new ArrayList<Statement>();
        }
        ( stm = statement { $stmts.add($stm.stm); })*
    ;

    statement returns [Statement stm]:
        blk = statementBlock
        {
            $stm = $blk.blk;
        }
        |
        cond = statementCondition
        {
            $stm = $cond.cond;
        }
        |
        loop = statementLoop
        {
            $stm = $loop.loop;
        }
        |
        write = statementWrite
        {
            $stm = $write.write;
        }
        |
        assign = statementAssignment
        {
            $stm = $assign.assign;
        }
    ;

    statementBlock returns [Block blk]:
        '{'  stmts = statements '}'
        {
            $blk = new Block($stmts.stmts);
        }
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
        {
            $loop = new While($exp.exp, $stm.stm);
        }
    ;

    statementWrite returns [Write write]:
        'writeln(' arg = expression ')' ';'
        {
            $write = new Write($arg.exp);
        }
    ;

    statementAssignment returns [Assign assign]:
        exp = expression ';'
        {
            BinaryExpression xp = $exp.exp;
            $assign = new Assign(xp.getLeft(), xp.getRight());
        }
    ;

    expression returns [Expression exp]:
		xp = expressionAssignment
		{
		    $exp = $xp.exp;
		}
	;

    expressionAssignment returns [Expression exp]:
		(lvalue = expressionOr) '=' (rvalue = expressionAssignment)
		{
            $exp = new BinaryExpression($lvalue.exp, $rvalue.exp, BinaryOperator.assign);
        }
	    |
	    or = expressionOr
	    {
	        $exp = $or.exp;
	    }
	;

    expressionOr returns [Expression exp]:
		(left = expressionAnd) (right = expressionOrTemp[$left.exp])
		{
		    $exp = $right.exp;
		}
	;

    expressionOrTemp[Expression lvalue] returns [Expression exp]:
		'||'
		rvalue = expressionAnd
		{
		    BinaryExpression tmp = new BinaryExpression($lvalue.exp, $rvalue.exp, BinaryOperator.or);
		}
		rv = expressionOrTemp[tmp]
		{
		    $exp = $rv.exp;
		}
	    | { $exp = $lvalue; }

	;

    expressionAnd returns [Expression exp]:
		(left = expressionEq) (right = expressionAndTemp[$left.exp])
		{
		    $exp = $right.exp;
		}
	;

    expressionAndTemp[Expression lvalue] returns [Expression exp]:
		'&&'
		rvalue = expressionEq
		{
		    BinaryExpression tmp = new BinaryExpression($lvalue.exp, $rvalue.exp, BinaryOperator.and);
		}
		rv = expressionAndTemp[tmp]
		{
		    $exp = $rv.exp;
		}
	    |   { $exp = $lvalue; }
	;

    expressionEq returns [Expression exp]:
		(left = expressionCmp) (right = expressionEqTemp[$left.exp])
		{
            $exp = $right.exp;
		}
	;

    expressionEqTemp[Expression lvalue] returns  [Experssion exp]:
		('==' { operatorType = BinaryOperator.eq; } | '<>' { operatorType = BinaryOperator.neq; })
		rvalue = expressionCmp
		{
		    BinaryExpression tmp = new BinaryExpression(lvalue, $rvalue.exp, operatorType);
		}
		rv = expressionEqTemp[tmp]
		{
		    $exp = $rv.exp;
		}
	    | { $exp = lvalue; }
	;

    expressionCmp returns [Expression exp]:
		(left = expressionAdd) (right = expressionCmpTemp[$left.exp])
		{
		    $exp = $right.exp;
		}
	;

    expressionCmpTemp[Expression lvalue] returns [Expression exp]:
		('<' { operatorType = BinaryOperator.lt } | '>' { oepratorType = Bianryoperator.gt})
		rvalue = expressionAdd
		{
		    BinaryExpression tmp = new BinaryExpression(lvalue, $rvalue.exp, operatorType);
		}
		rv = expressionCmpTemp[tmp]
		{
		    $exp = $rv.exp;
		}
	    | { $exp = lvalue; }
	;

    expressionAdd returns [Expression exp]:
		(left = expressionMult) (right = expressionAddTemp[$left.exp])
		{
		    $exp = $right.exp;
		}
	;

    expressionAddTemp[Expression lvalue] returns [Expression exp]:
		(
		    '+'
//		        { BinaryOperator operatorType = BinaryOperator.add }
		    | '-'
//		        { BinaryOperator operatorType = BinaryOperator.sub; }
		)
		rvalue = expressionMult
//		{
//		    BinaryExpression tmp = new BinaryExpression(lvalue, $rvalue.tmp, operatorType);
//		}
		rv = expressionAddTemp[tmp]
		{
		    $exp = $rv.exp;
		}
	    | { $exp = lvalue; }
	;

    expressionMult returns [Expression exp]:
		(left = expressionUnary) (right = expressionMultTemp[$left.exp])
		{
		    $exp = $right.exp;
		}
	    ;

    expressionMultTemp[Expression lvalue] returns [Expression exp]:
		(
		    '*' { BinaryOperator operatorType = BinaryOperator.mult; }
		    | '/' { BinaryOperator operatorType = BinaryOperator.div; }
		)
		rvalue = expressionUnary
		{
		    BinaryExpression tmp = new BinaryExpression(lvalue, $rvalue.exp, operatorType);
		}
		rv = expressionMultTemp[tmp]
		{
		    $exp = $rv.exp;
		}
	    | { $exp = lvalue; }
	;

    expressionUnary returns [Expression exp]:
		('!' | '-') expressionUnary
	    |	expressionMem
	;

    expressionMem returns [Expression exp]:
		(instance = expressionMethods) (result = expressionMemTemp[$instance.exp])
		{ $exp = $result.exp; }
	;

    expressionMemTemp[Expression instance] returns [Expression exp]:
		'[' index = expression ']'
		{
		    $exp = new ArrayCall($instance, $index.exp);
		}
	    | { $exp = $instance; }
	;

	expressionMethods returns [Expression exp]:
	    (instance = expressionOther) (call = expressionMethodsTemp[$instance.exp])
	    {
            $exp = $call.exp;
	    }
	;

	expressionMethodsTemp[Expression instance] returns [Expression exp]:
	    {
	        Expression e;
	    }
	     '.'
	        (
	            name = ID '(' ')'
	                {
	                    e = new MethodCall($instance, new Identifier($name.getText()));
	                }
	            | name = ID { e = new MethodCall($instance, new Identifier($name.getText())); }
	                    '(' (exp1 = expression { e.addArg($exp1.exp); }
	                    (',' exp2 = expression { e.addArg($exp2.exp); })* ) ')'
	            | 'length' { e = new Length($instance); }
	        )
	    tmp = expressionMethodsTemp[e]
        {
            $exp = $tmp.exp;
        }

	    |   { $exp = $instance; }
	;

    expressionOther returns [Expression exp]:
		num = CONST_NUM { $exp = new IntValue(Integer.parseInt($num.getText()); }
        |	str = CONST_STR { $exp = new StringValue($str.getText(),  new StringType()); }
        |   'new ' 'int' '[' size = CONST_NUM ']'
            { $exp = new NewArray(new IntValue(Integer.parseInt($size.text))); }
        |   'new ' ID '(' ')' { $exp = new UserDefinedType(); } // set classdeclaration in symbol table
        |   'this' { $exp = new This(); }
        |   'true' { $exp = new BooleanValue(true, new BooleanType()); }
        |   'false' { $exp = new BooleanValue(false, new BooleanType()); }
        |	name = ID { $exp = new Identifier($name.getText()); }
        |   name = ID '[' index = expression ']'
            { $exp = new ArrayCall(new Identifier($name.getText()), $index.exp); }
        |	'(' expression ')'
	;

	type returns [Type t]:
	    'int'
	    {
	        $t = new IntType();
	    }
	    |
	    'boolean'
	    {
	        $t = new BooleanType();
	    }
	    |
	    'string'
	    {
	        $t = new StringType();
	    }
	    |
	    'int' '[' ']'
	    {
	        $t = new ArrayType(); // size will be set after initialization
	    }
	    |
	    ID
	    {
	        $t = new UserDefinedType();
	    }
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