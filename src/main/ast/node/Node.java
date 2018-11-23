package ast.node;

import ast.Visitor;

public abstract class Node {
    public void accept(Visitor visitor) {}

	protected int lineNum;

	public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
