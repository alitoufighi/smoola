package ast.node.expression;

import ast.Visitor;

import java.util.ArrayList;

public class MethodCall extends Expression {
    private Expression instance;
    private Identifier methodName;
    private ArrayList<Expression> args = new ArrayList<>();

    public MethodCall(Expression instance, Identifier methodName) {
        this.instance = instance;
        this.methodName = methodName;
        this.lineNum = instance.getLineNum();
    }

    public Expression getInstance() {
        return instance;
    }

    public Identifier getMethodName() {
        return methodName;
    }

    public ArrayList<Expression> getArgs() {
        return args;
    }

    public void addArg(Expression arg) {
        this.args.add(arg);
    }

    @Override
    public String toString() {
        return "MethodCall";
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
