import ast.CodeGeneratorVisitorImpl;
import ast.VisitorImpl;
import ast.node.PhaseNum;
import ast.node.Program;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class MySmoola {
	public static void main(String[] args) throws IOException {
		CharStream reader = CharStreams.fromFileName(args[0]);
		SmoolaLexer lexer = new SmoolaLexer(reader);   // SmoolaLexer in your project
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		SmoolaParser parser = new SmoolaParser(tokens);   // SmoolaParser in your project
		Program p = parser.program().p; // program is the name of the start rule

		VisitorImpl visitor = new VisitorImpl();
		CodeGeneratorVisitorImpl codeGeneratorVisitor = new CodeGeneratorVisitorImpl();
		p.accept(visitor);

		if(!p.isValid(PhaseNum.two))
			p.printErrors(PhaseNum.two);
		else if(!p.isValid(PhaseNum.three))
			p.printErrors(PhaseNum.three);
		else
			p.printMessages();
		p.accept(codeGeneratorVisitor);
	}
}
