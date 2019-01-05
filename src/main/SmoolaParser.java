// Generated from /home/felagund/Desktop/Fall97/PLC/smoola/Smoola.g4 by ANTLR 4.7

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

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SmoolaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, CONST_NUM=42, CONST_STR=43, NL=44, ID=45, 
		COMMENT=46, WS=47;
	public static final int
		RULE_program = 0, RULE_mainClass = 1, RULE_classDeclaration = 2, RULE_varDeclaration = 3, 
		RULE_methodDeclaration = 4, RULE_mainStatements = 5, RULE_mainStatement = 6, 
		RULE_mainStatementAssignment = 7, RULE_methodCall = 8, RULE_statements = 9, 
		RULE_statement = 10, RULE_statementBlock = 11, RULE_statementCondition = 12, 
		RULE_statementLoop = 13, RULE_statementWrite = 14, RULE_statementAssignment = 15, 
		RULE_expression = 16, RULE_expressionAssignment = 17, RULE_expressionOr = 18, 
		RULE_expressionOrTemp = 19, RULE_expressionAnd = 20, RULE_expressionAndTemp = 21, 
		RULE_expressionEq = 22, RULE_expressionEqTemp = 23, RULE_expressionCmp = 24, 
		RULE_expressionCmpTemp = 25, RULE_expressionAdd = 26, RULE_expressionAddTemp = 27, 
		RULE_expressionMult = 28, RULE_expressionMultTemp = 29, RULE_expressionUnary = 30, 
		RULE_expressionMem = 31, RULE_expressionMemTemp = 32, RULE_expressionMethods = 33, 
		RULE_expressionMethodsTemp = 34, RULE_expressionOther = 35, RULE_type = 36;
	public static final String[] ruleNames = {
		"program", "mainClass", "classDeclaration", "varDeclaration", "methodDeclaration", 
		"mainStatements", "mainStatement", "mainStatementAssignment", "methodCall", 
		"statements", "statement", "statementBlock", "statementCondition", "statementLoop", 
		"statementWrite", "statementAssignment", "expression", "expressionAssignment", 
		"expressionOr", "expressionOrTemp", "expressionAnd", "expressionAndTemp", 
		"expressionEq", "expressionEqTemp", "expressionCmp", "expressionCmpTemp", 
		"expressionAdd", "expressionAddTemp", "expressionMult", "expressionMultTemp", 
		"expressionUnary", "expressionMem", "expressionMemTemp", "expressionMethods", 
		"expressionMethodsTemp", "expressionOther", "type"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'class'", "'{'", "'def'", "'('", "')'", "':'", "'int'", "'return'", 
		"';'", "'}'", "'extends'", "'var'", "','", "'new'", "'if'", "'then'", 
		"'else'", "'while'", "'writeln('", "'='", "'||'", "'&&'", "'=='", "'<>'", 
		"'<'", "'>'", "'+'", "'-'", "'*'", "'/'", "'!'", "'['", "']'", "'.'", 
		"'length'", "'new '", "'this'", "'true'", "'false'", "'boolean'", "'string'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, "CONST_NUM", "CONST_STR", "NL", "ID", 
		"COMMENT", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Smoola.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	   void print(Object obj){
	        System.out.println(obj);
	   }

	public SmoolaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public Program p;
		public MainClassContext y;
		public ClassDeclarationContext x;
		public TerminalNode EOF() { return getToken(SmoolaParser.EOF, 0); }
		public MainClassContext mainClass() {
			return getRuleContext(MainClassContext.class,0);
		}
		public List<ClassDeclarationContext> classDeclaration() {
			return getRuleContexts(ClassDeclarationContext.class);
		}
		public ClassDeclarationContext classDeclaration(int i) {
			return getRuleContext(ClassDeclarationContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((ProgramContext)_localctx).p =  new Program(); 
			setState(75);
			((ProgramContext)_localctx).y = mainClass();
			 _localctx.p.setMainClass(((ProgramContext)_localctx).y.c); 
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(77);
				((ProgramContext)_localctx).x = classDeclaration();
				 _localctx.p.addClass(((ProgramContext)_localctx).x.c); 
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(85);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainClassContext extends ParserRuleContext {
		public ClassDeclaration c;
		public Token class_name;
		public Token method_name;
		public MainStatementsContext st;
		public ExpressionContext rv;
		public List<TerminalNode> ID() { return getTokens(SmoolaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(SmoolaParser.ID, i);
		}
		public MainStatementsContext mainStatements() {
			return getRuleContext(MainStatementsContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MainClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainClass; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterMainClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitMainClass(this);
		}
	}

	public final MainClassContext mainClass() throws RecognitionException {
		MainClassContext _localctx = new MainClassContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_mainClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(T__0);
			setState(88);
			((MainClassContext)_localctx).class_name = match(ID);
			setState(89);
			match(T__1);
			setState(90);
			match(T__2);
			setState(91);
			((MainClassContext)_localctx).method_name = match(ID);
			setState(92);
			match(T__3);
			setState(93);
			match(T__4);
			setState(94);
			match(T__5);
			setState(95);
			match(T__6);
			setState(96);
			match(T__1);
			setState(97);
			((MainClassContext)_localctx).st = mainStatements();
			setState(98);
			match(T__7);
			setState(99);
			((MainClassContext)_localctx).rv = expression();
			setState(100);
			match(T__8);
			setState(101);
			match(T__9);
			setState(102);
			match(T__9);

			            String classname = ((MainClassContext)_localctx).class_name.getText();
			            String parentname = null;

			            ((MainClassContext)_localctx).c =  new ClassDeclaration(new Identifier(classname, ((MainClassContext)_localctx).class_name.getLine()),
			                new Identifier("Object", 0)
			                );
			            _localctx.c.setLineNum(((MainClassContext)_localctx).class_name.getLine());

			            MethodDeclaration mainMethod = new MethodDeclaration(
			                new Identifier(((MainClassContext)_localctx).method_name.getText(),
			                    ((MainClassContext)_localctx).method_name.getLine())
			            );
			            mainMethod.setMainMethod();
			            mainMethod.setReturnValue(((MainClassContext)_localctx).rv.exp);
			            mainMethod.setReturnType(new IntType());
			            mainMethod.setBody(((MainClassContext)_localctx).st.stmts);
			            mainMethod.setLineNum(((MainClassContext)_localctx).method_name.getLine());
			            _localctx.c.addMethodDeclaration(mainMethod);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public ClassDeclaration c;
		public Token name;
		public Token pname;
		public VarDeclarationContext var;
		public MethodDeclarationContext method;
		public List<TerminalNode> ID() { return getTokens(SmoolaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(SmoolaParser.ID, i);
		}
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public List<MethodDeclarationContext> methodDeclaration() {
			return getRuleContexts(MethodDeclarationContext.class);
		}
		public MethodDeclarationContext methodDeclaration(int i) {
			return getRuleContext(MethodDeclarationContext.class,i);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterClassDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitClassDeclaration(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{

			            boolean has_parent = false;
			            ArrayList<VarDeclaration> vars;
			        
			setState(106);
			match(T__0);
			setState(107);
			((ClassDeclarationContext)_localctx).name = match(ID);
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__10) {
				{
				setState(108);
				match(T__10);
				setState(109);
				((ClassDeclarationContext)_localctx).pname = match(ID);
				 has_parent = true; 
				}
			}


			            String classname = ((ClassDeclarationContext)_localctx).name.getText();
			            String parentname = (has_parent) ? ((ClassDeclarationContext)_localctx).pname.getText() : null;
			            ((ClassDeclarationContext)_localctx).c =  new ClassDeclaration(
			                new Identifier(classname, ((ClassDeclarationContext)_localctx).name.getLine()),
			                (has_parent)? new Identifier(parentname, ((ClassDeclarationContext)_localctx).pname.getLine()) : new Identifier("Object", 0)
			            );
			        
			setState(114);
			match(T__1);
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(115);
				((ClassDeclarationContext)_localctx).var = varDeclaration();
				 _localctx.c.addVarDeclaration(((ClassDeclarationContext)_localctx).var.var); 
				}
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(128);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(123);
				((ClassDeclarationContext)_localctx).method = methodDeclaration();
				 _localctx.c.addMethodDeclaration(((ClassDeclarationContext)_localctx).method.m); 
				}
				}
				setState(130);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(131);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclarationContext extends ParserRuleContext {
		public VarDeclaration var;
		public Token id;
		public TypeContext t;
		public TerminalNode ID() { return getToken(SmoolaParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterVarDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitVarDeclaration(this);
		}
	}

	public final VarDeclarationContext varDeclaration() throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_varDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			match(T__11);
			{
			setState(134);
			((VarDeclarationContext)_localctx).id = match(ID);
			}
			setState(135);
			match(T__5);
			{
			setState(136);
			((VarDeclarationContext)_localctx).t = type();
			}
			setState(137);
			match(T__8);

			            Identifier var_id = new Identifier(((VarDeclarationContext)_localctx).id.getText(), ((VarDeclarationContext)_localctx).id.getLine());
			            ((VarDeclarationContext)_localctx).var =  new VarDeclaration(var_id, ((VarDeclarationContext)_localctx).t.t);
			            _localctx.var.setLineNum(((VarDeclarationContext)_localctx).id.getLine());
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclarationContext extends ParserRuleContext {
		public MethodDeclaration m;
		public Token name;
		public Token id;
		public TypeContext t;
		public TypeContext ret_type;
		public VarDeclarationContext var;
		public StatementsContext stmts;
		public ExpressionContext rv;
		public List<TerminalNode> ID() { return getTokens(SmoolaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(SmoolaParser.ID, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public MethodDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterMethodDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitMethodDeclaration(this);
		}
	}

	public final MethodDeclarationContext methodDeclaration() throws RecognitionException {
		MethodDeclarationContext _localctx = new MethodDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_methodDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(T__2);
			setState(141);
			((MethodDeclarationContext)_localctx).name = match(ID);

			            Identifier name = new Identifier(((MethodDeclarationContext)_localctx).name.getText(), ((MethodDeclarationContext)_localctx).name.getLine());
			            ((MethodDeclarationContext)_localctx).m =  new MethodDeclaration(name);
			            _localctx.m.setLineNum(((MethodDeclarationContext)_localctx).name.getLine());
			        
			setState(165);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(143);
				match(T__3);
				setState(144);
				match(T__4);
				}
				break;
			case 2:
				{
				{
				setState(145);
				match(T__3);
				setState(146);
				((MethodDeclarationContext)_localctx).id = match(ID);
				 Identifier var0_id = new Identifier(((MethodDeclarationContext)_localctx).id.getText(), ((MethodDeclarationContext)_localctx).id.getLine()); 
				setState(148);
				match(T__5);
				setState(149);
				((MethodDeclarationContext)_localctx).t = type();
				 _localctx.m.addArg(new VarDeclaration(var0_id, ((MethodDeclarationContext)_localctx).t.t)); 
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__12) {
					{
					{
					setState(151);
					match(T__12);
					setState(152);
					((MethodDeclarationContext)_localctx).id = match(ID);
					 Identifier var_id = new Identifier(((MethodDeclarationContext)_localctx).id.getText(), ((MethodDeclarationContext)_localctx).id.getLine()); 
					setState(154);
					match(T__5);
					setState(155);
					((MethodDeclarationContext)_localctx).t = type();
					 _localctx.m.addArg(new VarDeclaration(var_id, ((MethodDeclarationContext)_localctx).t.t)); 
					}
					}
					setState(162);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(163);
				match(T__4);
				}
				}
				break;
			}
			setState(167);
			match(T__5);
			setState(168);
			((MethodDeclarationContext)_localctx).ret_type = type();
			 _localctx.m.setReturnType(((MethodDeclarationContext)_localctx).ret_type.t); 
			setState(170);
			match(T__1);
			setState(176);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11) {
				{
				{
				setState(171);
				((MethodDeclarationContext)_localctx).var = varDeclaration();
				 _localctx.m.addLocalVar(((MethodDeclarationContext)_localctx).var.var); 
				}
				}
				setState(178);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(179);
			((MethodDeclarationContext)_localctx).stmts = statements();
			 _localctx.m.setBody(((MethodDeclarationContext)_localctx).stmts.stmts); 
			setState(181);
			match(T__7);
			setState(182);
			((MethodDeclarationContext)_localctx).rv = expression();
			 _localctx.m.setReturnValue(((MethodDeclarationContext)_localctx).rv.exp); 
			setState(184);
			match(T__8);
			setState(185);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainStatementsContext extends ParserRuleContext {
		public ArrayList<Statement> stmts;
		public MainStatementContext stm;
		public List<MainStatementContext> mainStatement() {
			return getRuleContexts(MainStatementContext.class);
		}
		public MainStatementContext mainStatement(int i) {
			return getRuleContext(MainStatementContext.class,i);
		}
		public MainStatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainStatements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterMainStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitMainStatements(this);
		}
	}

	public final MainStatementsContext mainStatements() throws RecognitionException {
		MainStatementsContext _localctx = new MainStatementsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_mainStatements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((MainStatementsContext)_localctx).stmts =  new ArrayList<Statement>(); 
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__3) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__27) | (1L << T__30) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << CONST_NUM) | (1L << CONST_STR) | (1L << ID))) != 0)) {
				{
				{
				setState(188);
				((MainStatementsContext)_localctx).stm = mainStatement();
				 _localctx.stmts.add(((MainStatementsContext)_localctx).stm.stm); 
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainStatementContext extends ParserRuleContext {
		public Statement stm;
		public StatementBlockContext blk;
		public StatementConditionContext cond;
		public StatementLoopContext loop;
		public StatementWriteContext write;
		public MainStatementAssignmentContext assign;
		public StatementBlockContext statementBlock() {
			return getRuleContext(StatementBlockContext.class,0);
		}
		public StatementConditionContext statementCondition() {
			return getRuleContext(StatementConditionContext.class,0);
		}
		public StatementLoopContext statementLoop() {
			return getRuleContext(StatementLoopContext.class,0);
		}
		public StatementWriteContext statementWrite() {
			return getRuleContext(StatementWriteContext.class,0);
		}
		public MainStatementAssignmentContext mainStatementAssignment() {
			return getRuleContext(MainStatementAssignmentContext.class,0);
		}
		public MainStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterMainStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitMainStatement(this);
		}
	}

	public final MainStatementContext mainStatement() throws RecognitionException {
		MainStatementContext _localctx = new MainStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_mainStatement);
		try {
			setState(211);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(196);
				((MainStatementContext)_localctx).blk = statementBlock();
				 ((MainStatementContext)_localctx).stm =  ((MainStatementContext)_localctx).blk.blk; 
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(199);
				((MainStatementContext)_localctx).cond = statementCondition();
				 ((MainStatementContext)_localctx).stm =  ((MainStatementContext)_localctx).cond.cond; 
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 3);
				{
				setState(202);
				((MainStatementContext)_localctx).loop = statementLoop();
				 ((MainStatementContext)_localctx).stm =  ((MainStatementContext)_localctx).loop.loop; 
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 4);
				{
				setState(205);
				((MainStatementContext)_localctx).write = statementWrite();
				 ((MainStatementContext)_localctx).stm =  ((MainStatementContext)_localctx).write.write; 
				}
				break;
			case T__3:
			case T__27:
			case T__30:
			case T__35:
			case T__36:
			case T__37:
			case T__38:
			case CONST_NUM:
			case CONST_STR:
			case ID:
				enterOuterAlt(_localctx, 5);
				{
				setState(208);
				((MainStatementContext)_localctx).assign = mainStatementAssignment();
				 ((MainStatementContext)_localctx).stm =  ((MainStatementContext)_localctx).assign.stm; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainStatementAssignmentContext extends ParserRuleContext {
		public Statement stm;
		public ExpressionContext exp;
		public Token semicolon;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MainStatementAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainStatementAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterMainStatementAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitMainStatementAssignment(this);
		}
	}

	public final MainStatementAssignmentContext mainStatementAssignment() throws RecognitionException {
		MainStatementAssignmentContext _localctx = new MainStatementAssignmentContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_mainStatementAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			((MainStatementAssignmentContext)_localctx).exp = expression();
			setState(214);
			((MainStatementAssignmentContext)_localctx).semicolon = match(T__8);

			            if (((MainStatementAssignmentContext)_localctx).exp.exp instanceof BinaryExpression &&
			                ((BinaryExpression)((MainStatementAssignmentContext)_localctx).exp.exp).getBinaryOperator() == BinaryOperator.OperatorTypes.assign)
			                    ((MainStatementAssignmentContext)_localctx).stm =  new Assign(((BinaryExpression)((MainStatementAssignmentContext)_localctx).exp.exp).getLeft(), ((BinaryExpression)((MainStatementAssignmentContext)_localctx).exp.exp).getRight());
			            else if(((MainStatementAssignmentContext)_localctx).exp.exp instanceof MethodCall){
			                ((MainStatementAssignmentContext)_localctx).stm =  new MethodCallInMain(((MethodCall)((MainStatementAssignmentContext)_localctx).exp.exp).getInstance(), ((MethodCall)((MainStatementAssignmentContext)_localctx).exp.exp).getMethodName());
			                ((MethodCallInMain)_localctx.stm).setArgs(((MethodCall)((MainStatementAssignmentContext)_localctx).exp.exp).getArgs());
			                _localctx.stm.setLineNum(((MainStatementAssignmentContext)_localctx).semicolon.getLine());
			            } else {
			                ((MainStatementAssignmentContext)_localctx).stm =  new DummyStatement(((MainStatementAssignmentContext)_localctx).semicolon.getLine());
			            }
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodCallContext extends ParserRuleContext {
		public Statement stm;
		public Token name;
		public ExpressionMethodsTempContext expressionMethodsTemp() {
			return getRuleContext(ExpressionMethodsTempContext.class,0);
		}
		public TerminalNode ID() { return getToken(SmoolaParser.ID, 0); }
		public MethodCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitMethodCall(this);
		}
	}

	public final MethodCallContext methodCall() throws RecognitionException {
		MethodCallContext _localctx = new MethodCallContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_methodCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			 Expression exp; 
			setState(218);
			match(T__13);
			setState(219);
			((MethodCallContext)_localctx).name = match(ID);
			 exp = new NewClass(new Identifier(((MethodCallContext)_localctx).name.getText(), ((MethodCallContext)_localctx).name.getLine())); 
			setState(221);
			match(T__3);
			setState(222);
			match(T__4);
			setState(223);
			expressionMethodsTemp(exp);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementsContext extends ParserRuleContext {
		public ArrayList<Statement> stmts;
		public StatementContext stm;
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitStatements(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_statements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			 ((StatementsContext)_localctx).stmts =  new ArrayList<Statement>(); 
			setState(231);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__3) | (1L << T__14) | (1L << T__17) | (1L << T__18) | (1L << T__27) | (1L << T__30) | (1L << T__35) | (1L << T__36) | (1L << T__37) | (1L << T__38) | (1L << CONST_NUM) | (1L << CONST_STR) | (1L << ID))) != 0)) {
				{
				{
				setState(226);
				((StatementsContext)_localctx).stm = statement();
				 _localctx.stmts.add(((StatementsContext)_localctx).stm.stm); 
				}
				}
				setState(233);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public Statement stm;
		public StatementBlockContext blk;
		public StatementConditionContext cond;
		public StatementLoopContext loop;
		public StatementWriteContext write;
		public StatementAssignmentContext assign;
		public StatementBlockContext statementBlock() {
			return getRuleContext(StatementBlockContext.class,0);
		}
		public StatementConditionContext statementCondition() {
			return getRuleContext(StatementConditionContext.class,0);
		}
		public StatementLoopContext statementLoop() {
			return getRuleContext(StatementLoopContext.class,0);
		}
		public StatementWriteContext statementWrite() {
			return getRuleContext(StatementWriteContext.class,0);
		}
		public StatementAssignmentContext statementAssignment() {
			return getRuleContext(StatementAssignmentContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_statement);
		try {
			setState(249);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__1:
				enterOuterAlt(_localctx, 1);
				{
				setState(234);
				((StatementContext)_localctx).blk = statementBlock();
				 ((StatementContext)_localctx).stm =  ((StatementContext)_localctx).blk.blk; 
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(237);
				((StatementContext)_localctx).cond = statementCondition();
				 ((StatementContext)_localctx).stm =  ((StatementContext)_localctx).cond.cond; 
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 3);
				{
				setState(240);
				((StatementContext)_localctx).loop = statementLoop();
				 ((StatementContext)_localctx).stm =  ((StatementContext)_localctx).loop.loop; 
				}
				break;
			case T__18:
				enterOuterAlt(_localctx, 4);
				{
				setState(243);
				((StatementContext)_localctx).write = statementWrite();
				 ((StatementContext)_localctx).stm =  ((StatementContext)_localctx).write.write; 
				}
				break;
			case T__3:
			case T__27:
			case T__30:
			case T__35:
			case T__36:
			case T__37:
			case T__38:
			case CONST_NUM:
			case CONST_STR:
			case ID:
				enterOuterAlt(_localctx, 5);
				{
				setState(246);
				((StatementContext)_localctx).assign = statementAssignment();
				 ((StatementContext)_localctx).stm =  ((StatementContext)_localctx).assign.assign; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementBlockContext extends ParserRuleContext {
		public Block blk;
		public StatementsContext stmts;
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public StatementBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterStatementBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitStatementBlock(this);
		}
	}

	public final StatementBlockContext statementBlock() throws RecognitionException {
		StatementBlockContext _localctx = new StatementBlockContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_statementBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251);
			match(T__1);
			setState(252);
			((StatementBlockContext)_localctx).stmts = statements();
			setState(253);
			match(T__9);
			 ((StatementBlockContext)_localctx).blk =  new Block(((StatementBlockContext)_localctx).stmts.stmts); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementConditionContext extends ParserRuleContext {
		public Conditional cond;
		public ExpressionContext exp;
		public StatementContext stmc;
		public StatementContext stma;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StatementConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementCondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterStatementCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitStatementCondition(this);
		}
	}

	public final StatementConditionContext statementCondition() throws RecognitionException {
		StatementConditionContext _localctx = new StatementConditionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_statementCondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			 boolean has_alt = false; 
			setState(257);
			match(T__14);
			setState(258);
			match(T__3);
			setState(259);
			((StatementConditionContext)_localctx).exp = expression();
			setState(260);
			match(T__4);
			setState(261);
			match(T__15);
			setState(262);
			((StatementConditionContext)_localctx).stmc = statement();
			setState(267);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(263);
				match(T__16);
				setState(264);
				((StatementConditionContext)_localctx).stma = statement();
				 has_alt = true; 
				}
				break;
			}

			            ((StatementConditionContext)_localctx).cond =  new Conditional(((StatementConditionContext)_localctx).exp.exp, ((StatementConditionContext)_localctx).stmc.stm);
			            if(has_alt)
			                _localctx.cond.setAlternativeBody(((StatementConditionContext)_localctx).stma.stm);
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementLoopContext extends ParserRuleContext {
		public While loop;
		public ExpressionContext exp;
		public StatementContext stm;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public StatementLoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementLoop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterStatementLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitStatementLoop(this);
		}
	}

	public final StatementLoopContext statementLoop() throws RecognitionException {
		StatementLoopContext _localctx = new StatementLoopContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_statementLoop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			match(T__17);
			setState(272);
			match(T__3);
			setState(273);
			((StatementLoopContext)_localctx).exp = expression();
			setState(274);
			match(T__4);
			setState(275);
			((StatementLoopContext)_localctx).stm = statement();
			 ((StatementLoopContext)_localctx).loop =  new While(((StatementLoopContext)_localctx).exp.exp, ((StatementLoopContext)_localctx).stm.stm); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementWriteContext extends ParserRuleContext {
		public Write write;
		public ExpressionContext arg;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementWriteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementWrite; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterStatementWrite(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitStatementWrite(this);
		}
	}

	public final StatementWriteContext statementWrite() throws RecognitionException {
		StatementWriteContext _localctx = new StatementWriteContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_statementWrite);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278);
			match(T__18);
			setState(279);
			((StatementWriteContext)_localctx).arg = expression();
			setState(280);
			match(T__4);
			setState(281);
			match(T__8);
			 ((StatementWriteContext)_localctx).write =  new Write(((StatementWriteContext)_localctx).arg.exp); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementAssignmentContext extends ParserRuleContext {
		public Statement assign;
		public ExpressionContext exp;
		public Token semicolon;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public StatementAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterStatementAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitStatementAssignment(this);
		}
	}

	public final StatementAssignmentContext statementAssignment() throws RecognitionException {
		StatementAssignmentContext _localctx = new StatementAssignmentContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_statementAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(284);
			((StatementAssignmentContext)_localctx).exp = expression();
			setState(285);
			((StatementAssignmentContext)_localctx).semicolon = match(T__8);

			            if (((StatementAssignmentContext)_localctx).exp.exp instanceof BinaryExpression &&
			                ((BinaryExpression)((StatementAssignmentContext)_localctx).exp.exp).getBinaryOperator() == BinaryOperator.OperatorTypes.assign)
			                    ((StatementAssignmentContext)_localctx).assign =  new Assign(((BinaryExpression)((StatementAssignmentContext)_localctx).exp.exp).getLeft(), ((BinaryExpression)((StatementAssignmentContext)_localctx).exp.exp).getRight());
			            else
			                ((StatementAssignmentContext)_localctx).assign =  new DummyStatement(((StatementAssignmentContext)_localctx).semicolon.getLine());
			        
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionAssignmentContext xp;
		public ExpressionAssignmentContext expressionAssignment() {
			return getRuleContext(ExpressionAssignmentContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(288);
			((ExpressionContext)_localctx).xp = expressionAssignment();
			 ((ExpressionContext)_localctx).exp =  ((ExpressionContext)_localctx).xp.exp; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionAssignmentContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionOrContext lvalue;
		public ExpressionAssignmentContext rvalue;
		public ExpressionOrContext or;
		public ExpressionOrContext expressionOr() {
			return getRuleContext(ExpressionOrContext.class,0);
		}
		public ExpressionAssignmentContext expressionAssignment() {
			return getRuleContext(ExpressionAssignmentContext.class,0);
		}
		public ExpressionAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionAssignment(this);
		}
	}

	public final ExpressionAssignmentContext expressionAssignment() throws RecognitionException {
		ExpressionAssignmentContext _localctx = new ExpressionAssignmentContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_expressionAssignment);
		try {
			setState(299);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(291);
				((ExpressionAssignmentContext)_localctx).lvalue = expressionOr();
				}
				setState(292);
				match(T__19);
				{
				setState(293);
				((ExpressionAssignmentContext)_localctx).rvalue = expressionAssignment();
				}
				 ((ExpressionAssignmentContext)_localctx).exp =  new BinaryExpression(((ExpressionAssignmentContext)_localctx).lvalue.exp, ((ExpressionAssignmentContext)_localctx).rvalue.exp, BinaryOperator.OperatorTypes.assign); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(296);
				((ExpressionAssignmentContext)_localctx).or = expressionOr();
				 ((ExpressionAssignmentContext)_localctx).exp =  ((ExpressionAssignmentContext)_localctx).or.exp; 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionOrContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionAndContext left;
		public ExpressionOrTempContext right;
		public ExpressionAndContext expressionAnd() {
			return getRuleContext(ExpressionAndContext.class,0);
		}
		public ExpressionOrTempContext expressionOrTemp() {
			return getRuleContext(ExpressionOrTempContext.class,0);
		}
		public ExpressionOrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionOr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionOr(this);
		}
	}

	public final ExpressionOrContext expressionOr() throws RecognitionException {
		ExpressionOrContext _localctx = new ExpressionOrContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_expressionOr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(301);
			((ExpressionOrContext)_localctx).left = expressionAnd();
			}
			{
			setState(302);
			((ExpressionOrContext)_localctx).right = expressionOrTemp(((ExpressionOrContext)_localctx).left.exp);
			}
			 ((ExpressionOrContext)_localctx).exp =  ((ExpressionOrContext)_localctx).right.exp; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionOrTempContext extends ParserRuleContext {
		public Expression lvalue;
		public Expression exp;
		public ExpressionAndContext rvalue;
		public ExpressionOrTempContext rv;
		public ExpressionAndContext expressionAnd() {
			return getRuleContext(ExpressionAndContext.class,0);
		}
		public ExpressionOrTempContext expressionOrTemp() {
			return getRuleContext(ExpressionOrTempContext.class,0);
		}
		public ExpressionOrTempContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExpressionOrTempContext(ParserRuleContext parent, int invokingState, Expression lvalue) {
			super(parent, invokingState);
			this.lvalue = lvalue;
		}
		@Override public int getRuleIndex() { return RULE_expressionOrTemp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionOrTemp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionOrTemp(this);
		}
	}

	public final ExpressionOrTempContext expressionOrTemp(Expression lvalue) throws RecognitionException {
		ExpressionOrTempContext _localctx = new ExpressionOrTempContext(_ctx, getState(), lvalue);
		enterRule(_localctx, 38, RULE_expressionOrTemp);
		try {
			setState(312);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__20:
				enterOuterAlt(_localctx, 1);
				{
				setState(305);
				match(T__20);
				setState(306);
				((ExpressionOrTempContext)_localctx).rvalue = expressionAnd();
				 BinaryExpression tmp = new BinaryExpression(lvalue, ((ExpressionOrTempContext)_localctx).rvalue.exp, BinaryOperator.OperatorTypes.or); 
				setState(308);
				((ExpressionOrTempContext)_localctx).rv = expressionOrTemp(tmp);
				 ((ExpressionOrTempContext)_localctx).exp =  ((ExpressionOrTempContext)_localctx).rv.exp; 
				}
				break;
			case T__4:
			case T__8:
			case T__12:
			case T__19:
			case T__32:
				enterOuterAlt(_localctx, 2);
				{
				 ((ExpressionOrTempContext)_localctx).exp =  lvalue; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionAndContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionEqContext left;
		public ExpressionAndTempContext right;
		public ExpressionEqContext expressionEq() {
			return getRuleContext(ExpressionEqContext.class,0);
		}
		public ExpressionAndTempContext expressionAndTemp() {
			return getRuleContext(ExpressionAndTempContext.class,0);
		}
		public ExpressionAndContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionAnd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionAnd(this);
		}
	}

	public final ExpressionAndContext expressionAnd() throws RecognitionException {
		ExpressionAndContext _localctx = new ExpressionAndContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_expressionAnd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(314);
			((ExpressionAndContext)_localctx).left = expressionEq();
			}
			{
			setState(315);
			((ExpressionAndContext)_localctx).right = expressionAndTemp(((ExpressionAndContext)_localctx).left.exp);
			}
			 ((ExpressionAndContext)_localctx).exp =  ((ExpressionAndContext)_localctx).right.exp; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionAndTempContext extends ParserRuleContext {
		public Expression lvalue;
		public Expression exp;
		public ExpressionEqContext rvalue;
		public ExpressionAndTempContext rv;
		public ExpressionEqContext expressionEq() {
			return getRuleContext(ExpressionEqContext.class,0);
		}
		public ExpressionAndTempContext expressionAndTemp() {
			return getRuleContext(ExpressionAndTempContext.class,0);
		}
		public ExpressionAndTempContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExpressionAndTempContext(ParserRuleContext parent, int invokingState, Expression lvalue) {
			super(parent, invokingState);
			this.lvalue = lvalue;
		}
		@Override public int getRuleIndex() { return RULE_expressionAndTemp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionAndTemp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionAndTemp(this);
		}
	}

	public final ExpressionAndTempContext expressionAndTemp(Expression lvalue) throws RecognitionException {
		ExpressionAndTempContext _localctx = new ExpressionAndTempContext(_ctx, getState(), lvalue);
		enterRule(_localctx, 42, RULE_expressionAndTemp);
		try {
			setState(325);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__21:
				enterOuterAlt(_localctx, 1);
				{
				setState(318);
				match(T__21);
				setState(319);
				((ExpressionAndTempContext)_localctx).rvalue = expressionEq();
				 BinaryExpression tmp = new BinaryExpression(lvalue, ((ExpressionAndTempContext)_localctx).rvalue.exp, BinaryOperator.OperatorTypes.and); 
				setState(321);
				((ExpressionAndTempContext)_localctx).rv = expressionAndTemp(tmp);
				 ((ExpressionAndTempContext)_localctx).exp =  ((ExpressionAndTempContext)_localctx).rv.exp; 
				}
				break;
			case T__4:
			case T__8:
			case T__12:
			case T__19:
			case T__20:
			case T__32:
				enterOuterAlt(_localctx, 2);
				{
				 ((ExpressionAndTempContext)_localctx).exp =  lvalue; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionEqContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionCmpContext left;
		public ExpressionEqTempContext right;
		public ExpressionCmpContext expressionCmp() {
			return getRuleContext(ExpressionCmpContext.class,0);
		}
		public ExpressionEqTempContext expressionEqTemp() {
			return getRuleContext(ExpressionEqTempContext.class,0);
		}
		public ExpressionEqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionEq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionEq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionEq(this);
		}
	}

	public final ExpressionEqContext expressionEq() throws RecognitionException {
		ExpressionEqContext _localctx = new ExpressionEqContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_expressionEq);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(327);
			((ExpressionEqContext)_localctx).left = expressionCmp();
			}
			{
			setState(328);
			((ExpressionEqContext)_localctx).right = expressionEqTemp(((ExpressionEqContext)_localctx).left.exp);
			}
			 ((ExpressionEqContext)_localctx).exp =  ((ExpressionEqContext)_localctx).right.exp; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionEqTempContext extends ParserRuleContext {
		public Expression lvalue;
		public Expression exp;
		public ExpressionCmpContext rvalue;
		public ExpressionEqTempContext rv;
		public ExpressionCmpContext expressionCmp() {
			return getRuleContext(ExpressionCmpContext.class,0);
		}
		public ExpressionEqTempContext expressionEqTemp() {
			return getRuleContext(ExpressionEqTempContext.class,0);
		}
		public ExpressionEqTempContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExpressionEqTempContext(ParserRuleContext parent, int invokingState, Expression lvalue) {
			super(parent, invokingState);
			this.lvalue = lvalue;
		}
		@Override public int getRuleIndex() { return RULE_expressionEqTemp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionEqTemp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionEqTemp(this);
		}
	}

	public final ExpressionEqTempContext expressionEqTemp(Expression lvalue) throws RecognitionException {
		ExpressionEqTempContext _localctx = new ExpressionEqTempContext(_ctx, getState(), lvalue);
		enterRule(_localctx, 46, RULE_expressionEqTemp);
		try {
			setState(344);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__22:
			case T__23:
				enterOuterAlt(_localctx, 1);
				{
				 BinaryOperator.OperatorTypes operatorType; 
				setState(336);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__22:
					{
					setState(332);
					match(T__22);
					 operatorType = BinaryOperator.OperatorTypes.eq; 
					}
					break;
				case T__23:
					{
					setState(334);
					match(T__23);
					 operatorType = BinaryOperator.OperatorTypes.neq; 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(338);
				((ExpressionEqTempContext)_localctx).rvalue = expressionCmp();
				 BinaryExpression tmp = new BinaryExpression(lvalue, ((ExpressionEqTempContext)_localctx).rvalue.exp, operatorType); 
				setState(340);
				((ExpressionEqTempContext)_localctx).rv = expressionEqTemp(tmp);
				 ((ExpressionEqTempContext)_localctx).exp =  ((ExpressionEqTempContext)_localctx).rv.exp; 
				}
				break;
			case T__4:
			case T__8:
			case T__12:
			case T__19:
			case T__20:
			case T__21:
			case T__32:
				enterOuterAlt(_localctx, 2);
				{
				 ((ExpressionEqTempContext)_localctx).exp =  lvalue; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionCmpContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionAddContext left;
		public ExpressionCmpTempContext right;
		public ExpressionAddContext expressionAdd() {
			return getRuleContext(ExpressionAddContext.class,0);
		}
		public ExpressionCmpTempContext expressionCmpTemp() {
			return getRuleContext(ExpressionCmpTempContext.class,0);
		}
		public ExpressionCmpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionCmp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionCmp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionCmp(this);
		}
	}

	public final ExpressionCmpContext expressionCmp() throws RecognitionException {
		ExpressionCmpContext _localctx = new ExpressionCmpContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_expressionCmp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(346);
			((ExpressionCmpContext)_localctx).left = expressionAdd();
			}
			{
			setState(347);
			((ExpressionCmpContext)_localctx).right = expressionCmpTemp(((ExpressionCmpContext)_localctx).left.exp);
			}
			 ((ExpressionCmpContext)_localctx).exp =  ((ExpressionCmpContext)_localctx).right.exp; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionCmpTempContext extends ParserRuleContext {
		public Expression lvalue;
		public Expression exp;
		public ExpressionAddContext rvalue;
		public ExpressionCmpTempContext rv;
		public ExpressionAddContext expressionAdd() {
			return getRuleContext(ExpressionAddContext.class,0);
		}
		public ExpressionCmpTempContext expressionCmpTemp() {
			return getRuleContext(ExpressionCmpTempContext.class,0);
		}
		public ExpressionCmpTempContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExpressionCmpTempContext(ParserRuleContext parent, int invokingState, Expression lvalue) {
			super(parent, invokingState);
			this.lvalue = lvalue;
		}
		@Override public int getRuleIndex() { return RULE_expressionCmpTemp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionCmpTemp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionCmpTemp(this);
		}
	}

	public final ExpressionCmpTempContext expressionCmpTemp(Expression lvalue) throws RecognitionException {
		ExpressionCmpTempContext _localctx = new ExpressionCmpTempContext(_ctx, getState(), lvalue);
		enterRule(_localctx, 50, RULE_expressionCmpTemp);
		try {
			setState(363);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__24:
			case T__25:
				enterOuterAlt(_localctx, 1);
				{
				 BinaryOperator.OperatorTypes operatorType; 
				setState(355);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__24:
					{
					setState(351);
					match(T__24);
					 operatorType = BinaryOperator.OperatorTypes.lt; 
					}
					break;
				case T__25:
					{
					setState(353);
					match(T__25);
					 operatorType = BinaryOperator.OperatorTypes.gt; 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(357);
				((ExpressionCmpTempContext)_localctx).rvalue = expressionAdd();
				 BinaryExpression tmp = new BinaryExpression(lvalue, ((ExpressionCmpTempContext)_localctx).rvalue.exp, operatorType); 
				setState(359);
				((ExpressionCmpTempContext)_localctx).rv = expressionCmpTemp(tmp);
				 ((ExpressionCmpTempContext)_localctx).exp =  ((ExpressionCmpTempContext)_localctx).rv.exp; 
				}
				break;
			case T__4:
			case T__8:
			case T__12:
			case T__19:
			case T__20:
			case T__21:
			case T__22:
			case T__23:
			case T__32:
				enterOuterAlt(_localctx, 2);
				{
				 ((ExpressionCmpTempContext)_localctx).exp =  lvalue; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionAddContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionMultContext left;
		public ExpressionAddTempContext right;
		public ExpressionMultContext expressionMult() {
			return getRuleContext(ExpressionMultContext.class,0);
		}
		public ExpressionAddTempContext expressionAddTemp() {
			return getRuleContext(ExpressionAddTempContext.class,0);
		}
		public ExpressionAddContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionAdd; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionAdd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionAdd(this);
		}
	}

	public final ExpressionAddContext expressionAdd() throws RecognitionException {
		ExpressionAddContext _localctx = new ExpressionAddContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_expressionAdd);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(365);
			((ExpressionAddContext)_localctx).left = expressionMult();
			}
			{
			setState(366);
			((ExpressionAddContext)_localctx).right = expressionAddTemp(((ExpressionAddContext)_localctx).left.exp);
			}
			 ((ExpressionAddContext)_localctx).exp =  ((ExpressionAddContext)_localctx).right.exp; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionAddTempContext extends ParserRuleContext {
		public Expression lvalue;
		public Expression exp;
		public ExpressionMultContext rvalue;
		public ExpressionAddTempContext rv;
		public ExpressionMultContext expressionMult() {
			return getRuleContext(ExpressionMultContext.class,0);
		}
		public ExpressionAddTempContext expressionAddTemp() {
			return getRuleContext(ExpressionAddTempContext.class,0);
		}
		public ExpressionAddTempContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExpressionAddTempContext(ParserRuleContext parent, int invokingState, Expression lvalue) {
			super(parent, invokingState);
			this.lvalue = lvalue;
		}
		@Override public int getRuleIndex() { return RULE_expressionAddTemp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionAddTemp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionAddTemp(this);
		}
	}

	public final ExpressionAddTempContext expressionAddTemp(Expression lvalue) throws RecognitionException {
		ExpressionAddTempContext _localctx = new ExpressionAddTempContext(_ctx, getState(), lvalue);
		enterRule(_localctx, 54, RULE_expressionAddTemp);
		try {
			setState(382);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__26:
			case T__27:
				enterOuterAlt(_localctx, 1);
				{
				 BinaryOperator.OperatorTypes operatorType; 
				setState(374);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__26:
					{
					setState(370);
					match(T__26);
					 operatorType = BinaryOperator.OperatorTypes.add; 
					}
					break;
				case T__27:
					{
					setState(372);
					match(T__27);
					 operatorType = BinaryOperator.OperatorTypes.sub; 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(376);
				((ExpressionAddTempContext)_localctx).rvalue = expressionMult();
				 BinaryExpression tmp = new BinaryExpression(lvalue, ((ExpressionAddTempContext)_localctx).rvalue.exp, operatorType); 
				setState(378);
				((ExpressionAddTempContext)_localctx).rv = expressionAddTemp(tmp);
				 ((ExpressionAddTempContext)_localctx).exp =  ((ExpressionAddTempContext)_localctx).rv.exp; 
				}
				break;
			case T__4:
			case T__8:
			case T__12:
			case T__19:
			case T__20:
			case T__21:
			case T__22:
			case T__23:
			case T__24:
			case T__25:
			case T__32:
				enterOuterAlt(_localctx, 2);
				{
				 ((ExpressionAddTempContext)_localctx).exp =  lvalue; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionMultContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionUnaryContext left;
		public ExpressionMultTempContext right;
		public ExpressionUnaryContext expressionUnary() {
			return getRuleContext(ExpressionUnaryContext.class,0);
		}
		public ExpressionMultTempContext expressionMultTemp() {
			return getRuleContext(ExpressionMultTempContext.class,0);
		}
		public ExpressionMultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionMult; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionMult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionMult(this);
		}
	}

	public final ExpressionMultContext expressionMult() throws RecognitionException {
		ExpressionMultContext _localctx = new ExpressionMultContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_expressionMult);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(384);
			((ExpressionMultContext)_localctx).left = expressionUnary();
			}
			{
			setState(385);
			((ExpressionMultContext)_localctx).right = expressionMultTemp(((ExpressionMultContext)_localctx).left.exp);
			}
			 ((ExpressionMultContext)_localctx).exp =  ((ExpressionMultContext)_localctx).right.exp; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionMultTempContext extends ParserRuleContext {
		public Expression lvalue;
		public Expression exp;
		public ExpressionUnaryContext rvalue;
		public ExpressionMultTempContext rv;
		public ExpressionUnaryContext expressionUnary() {
			return getRuleContext(ExpressionUnaryContext.class,0);
		}
		public ExpressionMultTempContext expressionMultTemp() {
			return getRuleContext(ExpressionMultTempContext.class,0);
		}
		public ExpressionMultTempContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExpressionMultTempContext(ParserRuleContext parent, int invokingState, Expression lvalue) {
			super(parent, invokingState);
			this.lvalue = lvalue;
		}
		@Override public int getRuleIndex() { return RULE_expressionMultTemp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionMultTemp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionMultTemp(this);
		}
	}

	public final ExpressionMultTempContext expressionMultTemp(Expression lvalue) throws RecognitionException {
		ExpressionMultTempContext _localctx = new ExpressionMultTempContext(_ctx, getState(), lvalue);
		enterRule(_localctx, 58, RULE_expressionMultTemp);
		try {
			setState(401);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__28:
			case T__29:
				enterOuterAlt(_localctx, 1);
				{
				 BinaryOperator.OperatorTypes operatorType; 
				setState(393);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__28:
					{
					setState(389);
					match(T__28);
					 operatorType = BinaryOperator.OperatorTypes.mult; 
					}
					break;
				case T__29:
					{
					setState(391);
					match(T__29);
					 operatorType = BinaryOperator.OperatorTypes.div; 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(395);
				((ExpressionMultTempContext)_localctx).rvalue = expressionUnary();
				 BinaryExpression tmp = new BinaryExpression(lvalue, ((ExpressionMultTempContext)_localctx).rvalue.exp, operatorType); 
				setState(397);
				((ExpressionMultTempContext)_localctx).rv = expressionMultTemp(tmp);
				 ((ExpressionMultTempContext)_localctx).exp =  ((ExpressionMultTempContext)_localctx).rv.exp; 
				}
				break;
			case T__4:
			case T__8:
			case T__12:
			case T__19:
			case T__20:
			case T__21:
			case T__22:
			case T__23:
			case T__24:
			case T__25:
			case T__26:
			case T__27:
			case T__32:
				enterOuterAlt(_localctx, 2);
				{
				 ((ExpressionMultTempContext)_localctx).exp =  lvalue; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionUnaryContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionUnaryContext value;
		public ExpressionMemContext expMem;
		public ExpressionUnaryContext expressionUnary() {
			return getRuleContext(ExpressionUnaryContext.class,0);
		}
		public ExpressionMemContext expressionMem() {
			return getRuleContext(ExpressionMemContext.class,0);
		}
		public ExpressionUnaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionUnary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionUnary(this);
		}
	}

	public final ExpressionUnaryContext expressionUnary() throws RecognitionException {
		ExpressionUnaryContext _localctx = new ExpressionUnaryContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_expressionUnary);
		try {
			setState(416);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__27:
			case T__30:
				enterOuterAlt(_localctx, 1);
				{
				 UnaryOperator unaryOperator; 
				setState(408);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__30:
					{
					setState(404);
					match(T__30);
					 unaryOperator = UnaryOperator.not; 
					}
					break;
				case T__27:
					{
					setState(406);
					match(T__27);
					 unaryOperator = UnaryOperator.minus; 
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(410);
				((ExpressionUnaryContext)_localctx).value = expressionUnary();
				 ((ExpressionUnaryContext)_localctx).exp =  new UnaryExpression(unaryOperator, ((ExpressionUnaryContext)_localctx).value.exp); 
				}
				break;
			case T__3:
			case T__35:
			case T__36:
			case T__37:
			case T__38:
			case CONST_NUM:
			case CONST_STR:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(413);
				((ExpressionUnaryContext)_localctx).expMem = expressionMem();
				 ((ExpressionUnaryContext)_localctx).exp =  ((ExpressionUnaryContext)_localctx).expMem.exp; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionMemContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionMethodsContext instance;
		public ExpressionMemTempContext result;
		public ExpressionMethodsContext expressionMethods() {
			return getRuleContext(ExpressionMethodsContext.class,0);
		}
		public ExpressionMemTempContext expressionMemTemp() {
			return getRuleContext(ExpressionMemTempContext.class,0);
		}
		public ExpressionMemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionMem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionMem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionMem(this);
		}
	}

	public final ExpressionMemContext expressionMem() throws RecognitionException {
		ExpressionMemContext _localctx = new ExpressionMemContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_expressionMem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(418);
			((ExpressionMemContext)_localctx).instance = expressionMethods();
			}
			{
			setState(419);
			((ExpressionMemContext)_localctx).result = expressionMemTemp(((ExpressionMemContext)_localctx).instance.exp);
			}
			 ((ExpressionMemContext)_localctx).exp =  ((ExpressionMemContext)_localctx).result.exp; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionMemTempContext extends ParserRuleContext {
		public Expression instance;
		public Expression exp;
		public ExpressionContext index;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionMemTempContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExpressionMemTempContext(ParserRuleContext parent, int invokingState, Expression instance) {
			super(parent, invokingState);
			this.instance = instance;
		}
		@Override public int getRuleIndex() { return RULE_expressionMemTemp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionMemTemp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionMemTemp(this);
		}
	}

	public final ExpressionMemTempContext expressionMemTemp(Expression instance) throws RecognitionException {
		ExpressionMemTempContext _localctx = new ExpressionMemTempContext(_ctx, getState(), instance);
		enterRule(_localctx, 64, RULE_expressionMemTemp);
		try {
			setState(428);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__31:
				enterOuterAlt(_localctx, 1);
				{
				setState(422);
				match(T__31);
				setState(423);
				((ExpressionMemTempContext)_localctx).index = expression();
				setState(424);
				match(T__32);
				 ((ExpressionMemTempContext)_localctx).exp =  new ArrayCall(instance, ((ExpressionMemTempContext)_localctx).index.exp); 
				}
				break;
			case T__4:
			case T__8:
			case T__12:
			case T__19:
			case T__20:
			case T__21:
			case T__22:
			case T__23:
			case T__24:
			case T__25:
			case T__26:
			case T__27:
			case T__28:
			case T__29:
			case T__32:
				enterOuterAlt(_localctx, 2);
				{
				 ((ExpressionMemTempContext)_localctx).exp =  instance; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionMethodsContext extends ParserRuleContext {
		public Expression exp;
		public ExpressionOtherContext instance;
		public ExpressionMethodsTempContext call;
		public ExpressionOtherContext expressionOther() {
			return getRuleContext(ExpressionOtherContext.class,0);
		}
		public ExpressionMethodsTempContext expressionMethodsTemp() {
			return getRuleContext(ExpressionMethodsTempContext.class,0);
		}
		public ExpressionMethodsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionMethods; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionMethods(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionMethods(this);
		}
	}

	public final ExpressionMethodsContext expressionMethods() throws RecognitionException {
		ExpressionMethodsContext _localctx = new ExpressionMethodsContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_expressionMethods);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(430);
			((ExpressionMethodsContext)_localctx).instance = expressionOther();
			}
			{
			setState(431);
			((ExpressionMethodsContext)_localctx).call = expressionMethodsTemp(((ExpressionMethodsContext)_localctx).instance.exp);
			}
			 ((ExpressionMethodsContext)_localctx).exp =  ((ExpressionMethodsContext)_localctx).call.exp; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionMethodsTempContext extends ParserRuleContext {
		public Expression instance;
		public Expression exp;
		public Token name;
		public ExpressionContext exp1;
		public ExpressionContext exp2;
		public ExpressionMethodsTempContext tmp;
		public ExpressionMethodsTempContext expressionMethodsTemp() {
			return getRuleContext(ExpressionMethodsTempContext.class,0);
		}
		public TerminalNode ID() { return getToken(SmoolaParser.ID, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionMethodsTempContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExpressionMethodsTempContext(ParserRuleContext parent, int invokingState, Expression instance) {
			super(parent, invokingState);
			this.instance = instance;
		}
		@Override public int getRuleIndex() { return RULE_expressionMethodsTemp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionMethodsTemp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionMethodsTemp(this);
		}
	}

	public final ExpressionMethodsTempContext expressionMethodsTemp(Expression instance) throws RecognitionException {
		ExpressionMethodsTempContext _localctx = new ExpressionMethodsTempContext(_ctx, getState(), instance);
		enterRule(_localctx, 68, RULE_expressionMethodsTemp);
		int _la;
		try {
			setState(465);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__33:
				enterOuterAlt(_localctx, 1);
				{
				 Expression e = null; 
				setState(435);
				match(T__33);
				setState(459);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
				case 1:
					{
					setState(436);
					((ExpressionMethodsTempContext)_localctx).name = match(ID);
					setState(437);
					match(T__3);
					setState(438);
					match(T__4);
					 e = new MethodCall(instance, new Identifier(((ExpressionMethodsTempContext)_localctx).name.getText(), ((ExpressionMethodsTempContext)_localctx).name.getLine())); 
					}
					break;
				case 2:
					{
					setState(440);
					((ExpressionMethodsTempContext)_localctx).name = match(ID);
					 e = new MethodCall(instance, new Identifier(((ExpressionMethodsTempContext)_localctx).name.getText(), ((ExpressionMethodsTempContext)_localctx).name.getLine())); 
					setState(442);
					match(T__3);
					{
					setState(443);
					((ExpressionMethodsTempContext)_localctx).exp1 = expression();
					 ((MethodCall)e).addArg(((ExpressionMethodsTempContext)_localctx).exp1.exp); 
					}
					setState(452);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__12) {
						{
						{
						setState(446);
						match(T__12);
						setState(447);
						((ExpressionMethodsTempContext)_localctx).exp2 = expression();
						 ((MethodCall)e).addArg(((ExpressionMethodsTempContext)_localctx).exp2.exp); 
						}
						}
						setState(454);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(455);
					match(T__4);
					}
					break;
				case 3:
					{
					setState(457);
					match(T__34);
					 e = new Length(instance); 
					}
					break;
				}
				setState(461);
				((ExpressionMethodsTempContext)_localctx).tmp = expressionMethodsTemp(e);
				 ((ExpressionMethodsTempContext)_localctx).exp =  ((ExpressionMethodsTempContext)_localctx).tmp.exp; 
				}
				break;
			case EOF:
			case T__4:
			case T__8:
			case T__12:
			case T__19:
			case T__20:
			case T__21:
			case T__22:
			case T__23:
			case T__24:
			case T__25:
			case T__26:
			case T__27:
			case T__28:
			case T__29:
			case T__31:
			case T__32:
				enterOuterAlt(_localctx, 2);
				{
				 ((ExpressionMethodsTempContext)_localctx).exp =  instance; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionOtherContext extends ParserRuleContext {
		public Expression exp;
		public Token num;
		public Token str;
		public Token size;
		public Token name;
		public Token e;
		public ExpressionContext index;
		public ExpressionContext xp;
		public TerminalNode CONST_NUM() { return getToken(SmoolaParser.CONST_NUM, 0); }
		public TerminalNode CONST_STR() { return getToken(SmoolaParser.CONST_STR, 0); }
		public TerminalNode ID() { return getToken(SmoolaParser.ID, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionOtherContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionOther; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterExpressionOther(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitExpressionOther(this);
		}
	}

	public final ExpressionOtherContext expressionOther() throws RecognitionException {
		ExpressionOtherContext _localctx = new ExpressionOtherContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_expressionOther);
		try {
			setState(501);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(467);
				((ExpressionOtherContext)_localctx).num = match(CONST_NUM);
				 ((ExpressionOtherContext)_localctx).exp =  new IntValue(Integer.parseInt(((ExpressionOtherContext)_localctx).num.getText()), new IntType(), ((ExpressionOtherContext)_localctx).num.getLine()); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(469);
				((ExpressionOtherContext)_localctx).str = match(CONST_STR);
				 ((ExpressionOtherContext)_localctx).exp =  new StringValue(((ExpressionOtherContext)_localctx).str.getText(), new StringType(), ((ExpressionOtherContext)_localctx).str.getLine()); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(471);
				match(T__35);
				setState(472);
				match(T__6);
				setState(473);
				match(T__31);
				setState(474);
				((ExpressionOtherContext)_localctx).size = match(CONST_NUM);
				setState(475);
				match(T__32);

				                ((ExpressionOtherContext)_localctx).exp =  new NewArray();
				                _localctx.exp.setLineNum(((ExpressionOtherContext)_localctx).size.getLine());
				                ((NewArray)_localctx.exp).setExpression(new IntValue(Integer.parseInt(((ExpressionOtherContext)_localctx).size.getText()), new IntType(), ((ExpressionOtherContext)_localctx).size.getLine()));
				            
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(477);
				match(T__35);
				setState(478);
				((ExpressionOtherContext)_localctx).name = match(ID);
				setState(479);
				match(T__3);
				setState(480);
				match(T__4);
				 ((ExpressionOtherContext)_localctx).exp =  new NewClass(new Identifier(((ExpressionOtherContext)_localctx).name.getText(), ((ExpressionOtherContext)_localctx).name.getLine())); 
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(482);
				((ExpressionOtherContext)_localctx).e = match(T__36);
				 ((ExpressionOtherContext)_localctx).exp =  new This(((ExpressionOtherContext)_localctx).e.getLine()); 
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(484);
				((ExpressionOtherContext)_localctx).e = match(T__37);
				 ((ExpressionOtherContext)_localctx).exp =  new BooleanValue(true, new BooleanType(), ((ExpressionOtherContext)_localctx).e.getLine()); 
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(486);
				((ExpressionOtherContext)_localctx).e = match(T__38);
				 ((ExpressionOtherContext)_localctx).exp =  new BooleanValue(false, new BooleanType(), ((ExpressionOtherContext)_localctx).e.getLine()); 
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(488);
				((ExpressionOtherContext)_localctx).name = match(ID);
				 ((ExpressionOtherContext)_localctx).exp =  new Identifier(((ExpressionOtherContext)_localctx).name.getText(), ((ExpressionOtherContext)_localctx).name.getLine()); 
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(490);
				((ExpressionOtherContext)_localctx).name = match(ID);
				setState(491);
				match(T__31);
				setState(492);
				((ExpressionOtherContext)_localctx).index = expression();
				setState(493);
				match(T__32);
				 ((ExpressionOtherContext)_localctx).exp =  new ArrayCall(new Identifier(((ExpressionOtherContext)_localctx).name.getText(), ((ExpressionOtherContext)_localctx).name.getLine()), ((ExpressionOtherContext)_localctx).index.exp); 
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(496);
				match(T__3);
				setState(497);
				((ExpressionOtherContext)_localctx).xp = expression();
				setState(498);
				match(T__4);
				 ((ExpressionOtherContext)_localctx).exp =  ((ExpressionOtherContext)_localctx).xp.exp; 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public Type t;
		public Token name;
		public TerminalNode ID() { return getToken(SmoolaParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SmoolaListener ) ((SmoolaListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_type);
		try {
			setState(515);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(503);
				match(T__6);
				 ((TypeContext)_localctx).t =  new IntType(); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(505);
				match(T__39);
				 ((TypeContext)_localctx).t =  new BooleanType(); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(507);
				match(T__40);
				 ((TypeContext)_localctx).t =  new StringType(); 
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(509);
				match(T__6);
				setState(510);
				match(T__31);
				setState(511);
				match(T__32);
				 ((TypeContext)_localctx).t =  new ArrayType(); 
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(513);
				((TypeContext)_localctx).name = match(ID);
				 ((TypeContext)_localctx).t =  new UserDefinedType(new Identifier(((TypeContext)_localctx).name.getText(), ((TypeContext)_localctx).name.getLine())); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\61\u0208\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\2\3\2\3\2\3\2\7\2S\n\2\f"+
		"\2\16\2V\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4r\n\4\3\4\3\4\3\4"+
		"\3\4\3\4\7\4y\n\4\f\4\16\4|\13\4\3\4\3\4\3\4\7\4\u0081\n\4\f\4\16\4\u0084"+
		"\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u00a1\n\6\f\6\16\6\u00a4"+
		"\13\6\3\6\3\6\5\6\u00a8\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6\u00b1\n\6"+
		"\f\6\16\6\u00b4\13\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\7"+
		"\7\u00c2\n\7\f\7\16\7\u00c5\13\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\5\b\u00d6\n\b\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\7\13\u00e8\n\13\f\13\16\13\u00eb\13"+
		"\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00fc"+
		"\n\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\5\16\u010e\n\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u012e\n\23\3\24\3\24\3\24\3\24"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u013b\n\25\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u0148\n\27\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\3\31\5\31\u0153\n\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\5\31\u015b\n\31\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\5\33\u0166"+
		"\n\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u016e\n\33\3\34\3\34\3\34\3\34"+
		"\3\35\3\35\3\35\3\35\3\35\5\35\u0179\n\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\5\35\u0181\n\35\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3\37\5\37\u018c"+
		"\n\37\3\37\3\37\3\37\3\37\3\37\3\37\5\37\u0194\n\37\3 \3 \3 \3 \3 \5 "+
		"\u019b\n \3 \3 \3 \3 \3 \3 \5 \u01a3\n \3!\3!\3!\3!\3\"\3\"\3\"\3\"\3"+
		"\"\3\"\5\"\u01af\n\"\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3"+
		"$\3$\3$\3$\7$\u01c5\n$\f$\16$\u01c8\13$\3$\3$\3$\3$\5$\u01ce\n$\3$\3$"+
		"\3$\3$\5$\u01d4\n$\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%"+
		"\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\5%\u01f8\n%\3&\3&"+
		"\3&\3&\3&\3&\3&\3&\3&\3&\3&\3&\5&\u0206\n&\3&\2\2\'\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJ\2\2\2\u0213\2L\3"+
		"\2\2\2\4Y\3\2\2\2\6k\3\2\2\2\b\u0087\3\2\2\2\n\u008e\3\2\2\2\f\u00bd\3"+
		"\2\2\2\16\u00d5\3\2\2\2\20\u00d7\3\2\2\2\22\u00db\3\2\2\2\24\u00e3\3\2"+
		"\2\2\26\u00fb\3\2\2\2\30\u00fd\3\2\2\2\32\u0102\3\2\2\2\34\u0111\3\2\2"+
		"\2\36\u0118\3\2\2\2 \u011e\3\2\2\2\"\u0122\3\2\2\2$\u012d\3\2\2\2&\u012f"+
		"\3\2\2\2(\u013a\3\2\2\2*\u013c\3\2\2\2,\u0147\3\2\2\2.\u0149\3\2\2\2\60"+
		"\u015a\3\2\2\2\62\u015c\3\2\2\2\64\u016d\3\2\2\2\66\u016f\3\2\2\28\u0180"+
		"\3\2\2\2:\u0182\3\2\2\2<\u0193\3\2\2\2>\u01a2\3\2\2\2@\u01a4\3\2\2\2B"+
		"\u01ae\3\2\2\2D\u01b0\3\2\2\2F\u01d3\3\2\2\2H\u01f7\3\2\2\2J\u0205\3\2"+
		"\2\2LM\b\2\1\2MN\5\4\3\2NT\b\2\1\2OP\5\6\4\2PQ\b\2\1\2QS\3\2\2\2RO\3\2"+
		"\2\2SV\3\2\2\2TR\3\2\2\2TU\3\2\2\2UW\3\2\2\2VT\3\2\2\2WX\7\2\2\3X\3\3"+
		"\2\2\2YZ\7\3\2\2Z[\7/\2\2[\\\7\4\2\2\\]\7\5\2\2]^\7/\2\2^_\7\6\2\2_`\7"+
		"\7\2\2`a\7\b\2\2ab\7\t\2\2bc\7\4\2\2cd\5\f\7\2de\7\n\2\2ef\5\"\22\2fg"+
		"\7\13\2\2gh\7\f\2\2hi\7\f\2\2ij\b\3\1\2j\5\3\2\2\2kl\b\4\1\2lm\7\3\2\2"+
		"mq\7/\2\2no\7\r\2\2op\7/\2\2pr\b\4\1\2qn\3\2\2\2qr\3\2\2\2rs\3\2\2\2s"+
		"t\b\4\1\2tz\7\4\2\2uv\5\b\5\2vw\b\4\1\2wy\3\2\2\2xu\3\2\2\2y|\3\2\2\2"+
		"zx\3\2\2\2z{\3\2\2\2{\u0082\3\2\2\2|z\3\2\2\2}~\5\n\6\2~\177\b\4\1\2\177"+
		"\u0081\3\2\2\2\u0080}\3\2\2\2\u0081\u0084\3\2\2\2\u0082\u0080\3\2\2\2"+
		"\u0082\u0083\3\2\2\2\u0083\u0085\3\2\2\2\u0084\u0082\3\2\2\2\u0085\u0086"+
		"\7\f\2\2\u0086\7\3\2\2\2\u0087\u0088\7\16\2\2\u0088\u0089\7/\2\2\u0089"+
		"\u008a\7\b\2\2\u008a\u008b\5J&\2\u008b\u008c\7\13\2\2\u008c\u008d\b\5"+
		"\1\2\u008d\t\3\2\2\2\u008e\u008f\7\5\2\2\u008f\u0090\7/\2\2\u0090\u00a7"+
		"\b\6\1\2\u0091\u0092\7\6\2\2\u0092\u00a8\7\7\2\2\u0093\u0094\7\6\2\2\u0094"+
		"\u0095\7/\2\2\u0095\u0096\b\6\1\2\u0096\u0097\7\b\2\2\u0097\u0098\5J&"+
		"\2\u0098\u00a2\b\6\1\2\u0099\u009a\7\17\2\2\u009a\u009b\7/\2\2\u009b\u009c"+
		"\b\6\1\2\u009c\u009d\7\b\2\2\u009d\u009e\5J&\2\u009e\u009f\b\6\1\2\u009f"+
		"\u00a1\3\2\2\2\u00a0\u0099\3\2\2\2\u00a1\u00a4\3\2\2\2\u00a2\u00a0\3\2"+
		"\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a5\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a5"+
		"\u00a6\7\7\2\2\u00a6\u00a8\3\2\2\2\u00a7\u0091\3\2\2\2\u00a7\u0093\3\2"+
		"\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00aa\7\b\2\2\u00aa\u00ab\5J&\2\u00ab\u00ac"+
		"\b\6\1\2\u00ac\u00b2\7\4\2\2\u00ad\u00ae\5\b\5\2\u00ae\u00af\b\6\1\2\u00af"+
		"\u00b1\3\2\2\2\u00b0\u00ad\3\2\2\2\u00b1\u00b4\3\2\2\2\u00b2\u00b0\3\2"+
		"\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b5\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b5"+
		"\u00b6\5\24\13\2\u00b6\u00b7\b\6\1\2\u00b7\u00b8\7\n\2\2\u00b8\u00b9\5"+
		"\"\22\2\u00b9\u00ba\b\6\1\2\u00ba\u00bb\7\13\2\2\u00bb\u00bc\7\f\2\2\u00bc"+
		"\13\3\2\2\2\u00bd\u00c3\b\7\1\2\u00be\u00bf\5\16\b\2\u00bf\u00c0\b\7\1"+
		"\2\u00c0\u00c2\3\2\2\2\u00c1\u00be\3\2\2\2\u00c2\u00c5\3\2\2\2\u00c3\u00c1"+
		"\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\r\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c6"+
		"\u00c7\5\30\r\2\u00c7\u00c8\b\b\1\2\u00c8\u00d6\3\2\2\2\u00c9\u00ca\5"+
		"\32\16\2\u00ca\u00cb\b\b\1\2\u00cb\u00d6\3\2\2\2\u00cc\u00cd\5\34\17\2"+
		"\u00cd\u00ce\b\b\1\2\u00ce\u00d6\3\2\2\2\u00cf\u00d0\5\36\20\2\u00d0\u00d1"+
		"\b\b\1\2\u00d1\u00d6\3\2\2\2\u00d2\u00d3\5\20\t\2\u00d3\u00d4\b\b\1\2"+
		"\u00d4\u00d6\3\2\2\2\u00d5\u00c6\3\2\2\2\u00d5\u00c9\3\2\2\2\u00d5\u00cc"+
		"\3\2\2\2\u00d5\u00cf\3\2\2\2\u00d5\u00d2\3\2\2\2\u00d6\17\3\2\2\2\u00d7"+
		"\u00d8\5\"\22\2\u00d8\u00d9\7\13\2\2\u00d9\u00da\b\t\1\2\u00da\21\3\2"+
		"\2\2\u00db\u00dc\b\n\1\2\u00dc\u00dd\7\20\2\2\u00dd\u00de\7/\2\2\u00de"+
		"\u00df\b\n\1\2\u00df\u00e0\7\6\2\2\u00e0\u00e1\7\7\2\2\u00e1\u00e2\5F"+
		"$\2\u00e2\23\3\2\2\2\u00e3\u00e9\b\13\1\2\u00e4\u00e5\5\26\f\2\u00e5\u00e6"+
		"\b\13\1\2\u00e6\u00e8\3\2\2\2\u00e7\u00e4\3\2\2\2\u00e8\u00eb\3\2\2\2"+
		"\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\25\3\2\2\2\u00eb\u00e9"+
		"\3\2\2\2\u00ec\u00ed\5\30\r\2\u00ed\u00ee\b\f\1\2\u00ee\u00fc\3\2\2\2"+
		"\u00ef\u00f0\5\32\16\2\u00f0\u00f1\b\f\1\2\u00f1\u00fc\3\2\2\2\u00f2\u00f3"+
		"\5\34\17\2\u00f3\u00f4\b\f\1\2\u00f4\u00fc\3\2\2\2\u00f5\u00f6\5\36\20"+
		"\2\u00f6\u00f7\b\f\1\2\u00f7\u00fc\3\2\2\2\u00f8\u00f9\5 \21\2\u00f9\u00fa"+
		"\b\f\1\2\u00fa\u00fc\3\2\2\2\u00fb\u00ec\3\2\2\2\u00fb\u00ef\3\2\2\2\u00fb"+
		"\u00f2\3\2\2\2\u00fb\u00f5\3\2\2\2\u00fb\u00f8\3\2\2\2\u00fc\27\3\2\2"+
		"\2\u00fd\u00fe\7\4\2\2\u00fe\u00ff\5\24\13\2\u00ff\u0100\7\f\2\2\u0100"+
		"\u0101\b\r\1\2\u0101\31\3\2\2\2\u0102\u0103\b\16\1\2\u0103\u0104\7\21"+
		"\2\2\u0104\u0105\7\6\2\2\u0105\u0106\5\"\22\2\u0106\u0107\7\7\2\2\u0107"+
		"\u0108\7\22\2\2\u0108\u010d\5\26\f\2\u0109\u010a\7\23\2\2\u010a\u010b"+
		"\5\26\f\2\u010b\u010c\b\16\1\2\u010c\u010e\3\2\2\2\u010d\u0109\3\2\2\2"+
		"\u010d\u010e\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0110\b\16\1\2\u0110\33"+
		"\3\2\2\2\u0111\u0112\7\24\2\2\u0112\u0113\7\6\2\2\u0113\u0114\5\"\22\2"+
		"\u0114\u0115\7\7\2\2\u0115\u0116\5\26\f\2\u0116\u0117\b\17\1\2\u0117\35"+
		"\3\2\2\2\u0118\u0119\7\25\2\2\u0119\u011a\5\"\22\2\u011a\u011b\7\7\2\2"+
		"\u011b\u011c\7\13\2\2\u011c\u011d\b\20\1\2\u011d\37\3\2\2\2\u011e\u011f"+
		"\5\"\22\2\u011f\u0120\7\13\2\2\u0120\u0121\b\21\1\2\u0121!\3\2\2\2\u0122"+
		"\u0123\5$\23\2\u0123\u0124\b\22\1\2\u0124#\3\2\2\2\u0125\u0126\5&\24\2"+
		"\u0126\u0127\7\26\2\2\u0127\u0128\5$\23\2\u0128\u0129\b\23\1\2\u0129\u012e"+
		"\3\2\2\2\u012a\u012b\5&\24\2\u012b\u012c\b\23\1\2\u012c\u012e\3\2\2\2"+
		"\u012d\u0125\3\2\2\2\u012d\u012a\3\2\2\2\u012e%\3\2\2\2\u012f\u0130\5"+
		"*\26\2\u0130\u0131\5(\25\2\u0131\u0132\b\24\1\2\u0132\'\3\2\2\2\u0133"+
		"\u0134\7\27\2\2\u0134\u0135\5*\26\2\u0135\u0136\b\25\1\2\u0136\u0137\5"+
		"(\25\2\u0137\u0138\b\25\1\2\u0138\u013b\3\2\2\2\u0139\u013b\b\25\1\2\u013a"+
		"\u0133\3\2\2\2\u013a\u0139\3\2\2\2\u013b)\3\2\2\2\u013c\u013d\5.\30\2"+
		"\u013d\u013e\5,\27\2\u013e\u013f\b\26\1\2\u013f+\3\2\2\2\u0140\u0141\7"+
		"\30\2\2\u0141\u0142\5.\30\2\u0142\u0143\b\27\1\2\u0143\u0144\5,\27\2\u0144"+
		"\u0145\b\27\1\2\u0145\u0148\3\2\2\2\u0146\u0148\b\27\1\2\u0147\u0140\3"+
		"\2\2\2\u0147\u0146\3\2\2\2\u0148-\3\2\2\2\u0149\u014a\5\62\32\2\u014a"+
		"\u014b\5\60\31\2\u014b\u014c\b\30\1\2\u014c/\3\2\2\2\u014d\u0152\b\31"+
		"\1\2\u014e\u014f\7\31\2\2\u014f\u0153\b\31\1\2\u0150\u0151\7\32\2\2\u0151"+
		"\u0153\b\31\1\2\u0152\u014e\3\2\2\2\u0152\u0150\3\2\2\2\u0153\u0154\3"+
		"\2\2\2\u0154\u0155\5\62\32\2\u0155\u0156\b\31\1\2\u0156\u0157\5\60\31"+
		"\2\u0157\u0158\b\31\1\2\u0158\u015b\3\2\2\2\u0159\u015b\b\31\1\2\u015a"+
		"\u014d\3\2\2\2\u015a\u0159\3\2\2\2\u015b\61\3\2\2\2\u015c\u015d\5\66\34"+
		"\2\u015d\u015e\5\64\33\2\u015e\u015f\b\32\1\2\u015f\63\3\2\2\2\u0160\u0165"+
		"\b\33\1\2\u0161\u0162\7\33\2\2\u0162\u0166\b\33\1\2\u0163\u0164\7\34\2"+
		"\2\u0164\u0166\b\33\1\2\u0165\u0161\3\2\2\2\u0165\u0163\3\2\2\2\u0166"+
		"\u0167\3\2\2\2\u0167\u0168\5\66\34\2\u0168\u0169\b\33\1\2\u0169\u016a"+
		"\5\64\33\2\u016a\u016b\b\33\1\2\u016b\u016e\3\2\2\2\u016c\u016e\b\33\1"+
		"\2\u016d\u0160\3\2\2\2\u016d\u016c\3\2\2\2\u016e\65\3\2\2\2\u016f\u0170"+
		"\5:\36\2\u0170\u0171\58\35\2\u0171\u0172\b\34\1\2\u0172\67\3\2\2\2\u0173"+
		"\u0178\b\35\1\2\u0174\u0175\7\35\2\2\u0175\u0179\b\35\1\2\u0176\u0177"+
		"\7\36\2\2\u0177\u0179\b\35\1\2\u0178\u0174\3\2\2\2\u0178\u0176\3\2\2\2"+
		"\u0179\u017a\3\2\2\2\u017a\u017b\5:\36\2\u017b\u017c\b\35\1\2\u017c\u017d"+
		"\58\35\2\u017d\u017e\b\35\1\2\u017e\u0181\3\2\2\2\u017f\u0181\b\35\1\2"+
		"\u0180\u0173\3\2\2\2\u0180\u017f\3\2\2\2\u01819\3\2\2\2\u0182\u0183\5"+
		"> \2\u0183\u0184\5<\37\2\u0184\u0185\b\36\1\2\u0185;\3\2\2\2\u0186\u018b"+
		"\b\37\1\2\u0187\u0188\7\37\2\2\u0188\u018c\b\37\1\2\u0189\u018a\7 \2\2"+
		"\u018a\u018c\b\37\1\2\u018b\u0187\3\2\2\2\u018b\u0189\3\2\2\2\u018c\u018d"+
		"\3\2\2\2\u018d\u018e\5> \2\u018e\u018f\b\37\1\2\u018f\u0190\5<\37\2\u0190"+
		"\u0191\b\37\1\2\u0191\u0194\3\2\2\2\u0192\u0194\b\37\1\2\u0193\u0186\3"+
		"\2\2\2\u0193\u0192\3\2\2\2\u0194=\3\2\2\2\u0195\u019a\b \1\2\u0196\u0197"+
		"\7!\2\2\u0197\u019b\b \1\2\u0198\u0199\7\36\2\2\u0199\u019b\b \1\2\u019a"+
		"\u0196\3\2\2\2\u019a\u0198\3\2\2\2\u019b\u019c\3\2\2\2\u019c\u019d\5>"+
		" \2\u019d\u019e\b \1\2\u019e\u01a3\3\2\2\2\u019f\u01a0\5@!\2\u01a0\u01a1"+
		"\b \1\2\u01a1\u01a3\3\2\2\2\u01a2\u0195\3\2\2\2\u01a2\u019f\3\2\2\2\u01a3"+
		"?\3\2\2\2\u01a4\u01a5\5D#\2\u01a5\u01a6\5B\"\2\u01a6\u01a7\b!\1\2\u01a7"+
		"A\3\2\2\2\u01a8\u01a9\7\"\2\2\u01a9\u01aa\5\"\22\2\u01aa\u01ab\7#\2\2"+
		"\u01ab\u01ac\b\"\1\2\u01ac\u01af\3\2\2\2\u01ad\u01af\b\"\1\2\u01ae\u01a8"+
		"\3\2\2\2\u01ae\u01ad\3\2\2\2\u01afC\3\2\2\2\u01b0\u01b1\5H%\2\u01b1\u01b2"+
		"\5F$\2\u01b2\u01b3\b#\1\2\u01b3E\3\2\2\2\u01b4\u01b5\b$\1\2\u01b5\u01cd"+
		"\7$\2\2\u01b6\u01b7\7/\2\2\u01b7\u01b8\7\6\2\2\u01b8\u01b9\7\7\2\2\u01b9"+
		"\u01ce\b$\1\2\u01ba\u01bb\7/\2\2\u01bb\u01bc\b$\1\2\u01bc\u01bd\7\6\2"+
		"\2\u01bd\u01be\5\"\22\2\u01be\u01bf\b$\1\2\u01bf\u01c6\3\2\2\2\u01c0\u01c1"+
		"\7\17\2\2\u01c1\u01c2\5\"\22\2\u01c2\u01c3\b$\1\2\u01c3\u01c5\3\2\2\2"+
		"\u01c4\u01c0\3\2\2\2\u01c5\u01c8\3\2\2\2\u01c6\u01c4\3\2\2\2\u01c6\u01c7"+
		"\3\2\2\2\u01c7\u01c9\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c9\u01ca\7\7\2\2\u01ca"+
		"\u01ce\3\2\2\2\u01cb\u01cc\7%\2\2\u01cc\u01ce\b$\1\2\u01cd\u01b6\3\2\2"+
		"\2\u01cd\u01ba\3\2\2\2\u01cd\u01cb\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d0"+
		"\5F$\2\u01d0\u01d1\b$\1\2\u01d1\u01d4\3\2\2\2\u01d2\u01d4\b$\1\2\u01d3"+
		"\u01b4\3\2\2\2\u01d3\u01d2\3\2\2\2\u01d4G\3\2\2\2\u01d5\u01d6\7,\2\2\u01d6"+
		"\u01f8\b%\1\2\u01d7\u01d8\7-\2\2\u01d8\u01f8\b%\1\2\u01d9\u01da\7&\2\2"+
		"\u01da\u01db\7\t\2\2\u01db\u01dc\7\"\2\2\u01dc\u01dd\7,\2\2\u01dd\u01de"+
		"\7#\2\2\u01de\u01f8\b%\1\2\u01df\u01e0\7&\2\2\u01e0\u01e1\7/\2\2\u01e1"+
		"\u01e2\7\6\2\2\u01e2\u01e3\7\7\2\2\u01e3\u01f8\b%\1\2\u01e4\u01e5\7\'"+
		"\2\2\u01e5\u01f8\b%\1\2\u01e6\u01e7\7(\2\2\u01e7\u01f8\b%\1\2\u01e8\u01e9"+
		"\7)\2\2\u01e9\u01f8\b%\1\2\u01ea\u01eb\7/\2\2\u01eb\u01f8\b%\1\2\u01ec"+
		"\u01ed\7/\2\2\u01ed\u01ee\7\"\2\2\u01ee\u01ef\5\"\22\2\u01ef\u01f0\7#"+
		"\2\2\u01f0\u01f1\b%\1\2\u01f1\u01f8\3\2\2\2\u01f2\u01f3\7\6\2\2\u01f3"+
		"\u01f4\5\"\22\2\u01f4\u01f5\7\7\2\2\u01f5\u01f6\b%\1\2\u01f6\u01f8\3\2"+
		"\2\2\u01f7\u01d5\3\2\2\2\u01f7\u01d7\3\2\2\2\u01f7\u01d9\3\2\2\2\u01f7"+
		"\u01df\3\2\2\2\u01f7\u01e4\3\2\2\2\u01f7\u01e6\3\2\2\2\u01f7\u01e8\3\2"+
		"\2\2\u01f7\u01ea\3\2\2\2\u01f7\u01ec\3\2\2\2\u01f7\u01f2\3\2\2\2\u01f8"+
		"I\3\2\2\2\u01f9\u01fa\7\t\2\2\u01fa\u0206\b&\1\2\u01fb\u01fc\7*\2\2\u01fc"+
		"\u0206\b&\1\2\u01fd\u01fe\7+\2\2\u01fe\u0206\b&\1\2\u01ff\u0200\7\t\2"+
		"\2\u0200\u0201\7\"\2\2\u0201\u0202\7#\2\2\u0202\u0206\b&\1\2\u0203\u0204"+
		"\7/\2\2\u0204\u0206\b&\1\2\u0205\u01f9\3\2\2\2\u0205\u01fb\3\2\2\2\u0205"+
		"\u01fd\3\2\2\2\u0205\u01ff\3\2\2\2\u0205\u0203\3\2\2\2\u0206K\3\2\2\2"+
		"!Tqz\u0082\u00a2\u00a7\u00b2\u00c3\u00d5\u00e9\u00fb\u010d\u012d\u013a"+
		"\u0147\u0152\u015a\u0165\u016d\u0178\u0180\u018b\u0193\u019a\u01a2\u01ae"+
		"\u01c6\u01cd\u01d3\u01f7\u0205";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}