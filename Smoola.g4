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
            $stmts = new ArrayList<Statement>;
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
//        {
//            $stm = $assign.assign;
//        }
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

    statementAssignment:
        exp = expression ';'
        {

        }
    ;

    expression returns [Expression exp]:
		expressionAssignment
//		{
//		    $exp = new Expression(); // !!!!!!!!!!!!
//		}
	;

    expressionAssignment returns [Expression exp]:
		lvalue = expressionOr '=' rvalue = expressionAssignment
		{
            $exp = new Assign($lvalue.exp, $rvalue.exp);
		}
	    |
	    or = expressionOr
	    {
	        $exp = $or.exp;
	    }
	;

    expressionOr returns [Expression exp]:
		(left = expressionAnd) (right = expressionOrTemp)
		{
		    $exp = new BinaryExpression($left.exp, $right.exp, $right.operatorType);
		}
	;

    expressionOrTemp returns [BinaryOperator operatorType, Expression exp]:
        { $operatorType = BinaryOperator.or; }
		'||' expand = expressionAnd tmp = expressionOrTemp
		{
		    if($tmp == null){

		    }
		}
	    | { $exp = null; }

	;

    expressionAnd returns [Expression exp]:
		(left = expressionEq) (right = expressionAndTemp)
		{
		    $exp = new BinaryExpression($left.exp, $right.exp, $right.operatorType);
		}
	;

    expressionAndTemp:
		'&&' expressionEq expressionAndTemp
	    |
	;

    expressionEq returns [Expression exp]:
		(left = expressionCmp) (right = expressionEqTemp)
		{

		}
	;

    expressionEqTemp returns [BinaryOperator operatorType, Experssion exp]:
		('==' { $operatorType = BinaryOperator.eq; } | '<>' { $operatorType = BinaryOperator.neq; }) expressionCmp expressionEqTemp
	    | { $exp = null; }
	;

    expressionCmp:
		expressionAdd expressionCmpTemp
	;

    expressionCmpTemp:
		('<' | '>') expressionAdd expressionCmpTemp
	    |
	;

    expressionAdd:
		expressionMult expressionAddTemp
	;

    expressionAddTemp:
		('+' | '-') expressionMult expressionAddTemp
	    |
	;

    expressionMult:
		expressionUnary expressionMultTemp
	;

    expressionMultTemp returns [Expression exp]:
		('*' | '/') expressionUnary expressionMultTemp
	    |
	;

    expressionUnary:
		('!' | '-') expressionUnary
	    |	expressionMem
	;

    expressionMem:
		expressionMethods expressionMemTemp
	;

    expressionMemTemp:
		'[' expression ']'
	    |
	;

	expressionMethods returns [Expression exp]:
	    (instane = expressionOther) (call = expressionMethodsTemp[$instance])
	    {

	    }
	;

	expressionMethodsTemp[Expression instance] returns [Expression exp]:
	    '.'
	        (
	            name = ID '(' ')'
	                {
	                    $exp = new MethodCall($instance, new Identifier($name.getText()));
	                }
	            | name = ID '(' (expression (',' expression)* ) ')'
	              {
	                $exp = new MethodCall($instance, new Identifier($name.getText()));
	              }
	            | 'length' { $exp = new Length($instance); } // argument????
	        )
	    expressionMethodsTemp[$exp]
	    |
	;

    expressionOther returns [Expression exp]:
		num = CONST_NUM { $exp = new IntValue(Integer.parseInt($num.getText()); }
        |	str = CONST_STR { $exp = new StringValue($str.getText(),  new StringType()); }
        |   'new ' 'int' '[' size = CONST_NUM ']'
            { $exp = new NewArray(new IntValue(Integer.parseInt($num.getText()))); }
        |   'new ' ID '(' ')' { $exp = new UserDefinedType(); } // set classdeclaration in symbol table
        |   'this' { $exp = new This(); }
        |   'true' { $exp = new BooleanValue(true, new BooleanType()); }
        |   'false' { $exp = new BooleanValue(false, new BooleanType()); }
        |	name = ID { $exp = new Identifier($name.getText()); }
        |   name = ID '[' index = expression ']'
            { $exp = new ArrayCall(new Identifier($name.getText()), $index.exp()); }
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
	        $t = UserDefinedType();
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