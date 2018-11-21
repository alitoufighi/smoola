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
            String parentname = null; // wtf?
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
            Identifier name = new Identifier($name.getText()));
            $m = new MethodDeclaration(name);
        }
        (
            '(' ')'
            |
            (
                '('
                id = ID
                {
                    Identifier var_id = new Identifier($id.getText());
                }
                ':'
                t = type
                {
                    $m.addArg(new VarDeclaration(var_id, $t.t));
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
        statements
        'return'
        rv = expression { $m.setReturnValue($rv.exp); }
        ';'
        '}'
    ;

    statements:
        (statement)*
    ;
    statement:
        statementBlock |
        statementCondition |
        statementLoop |
        statementWrite |
        statementAssignment
    ;
    statementBlock:
        '{'  statements '}'
    ;
    statementCondition:
        'if' '('expression')' 'then' statement ('else' statement)?
    ;
    statementLoop:
        'while' '(' expression ')' statement
    ;
    statementWrite:
        'writeln(' expression ')' ';'
    ;
    statementAssignment:
        expression ';'
    ;

    expression returns [Expression exp]:
		expressionAssignment
		{
		    $exp = new Expression(); // !!!!!!!!!!!!
		}
	;

    expressionAssignment:
		expressionOr '=' expressionAssignment
	    |	expressionOr
	;

    expressionOr:
		expressionAnd expressionOrTemp
	;

    expressionOrTemp:
		'||' expressionAnd expressionOrTemp
	    |
	;

    expressionAnd:
		expressionEq expressionAndTemp
	;

    expressionAndTemp:
		'&&' expressionEq expressionAndTemp
	    |
	;

    expressionEq:
		expressionCmp expressionEqTemp
	;

    expressionEqTemp:
		('==' | '<>') expressionCmp expressionEqTemp
	    |
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

    expressionMultTemp:
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
	expressionMethods:
	    expressionOther expressionMethodsTemp
	;
	expressionMethodsTemp:
	    '.' (ID '(' ')' | ID '(' (expression (',' expression)*) ')' | 'length') expressionMethodsTemp
	    |
	;
    expressionOther:
		CONST_NUM
        |	CONST_STR
        |   'new ' 'int' '[' expression ']'
        |   'new ' ID '(' ')'
        |   'this'
        |   'true'
        |   'false'
        |	ID
        |   ID '[' expression ']'
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