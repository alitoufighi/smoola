package ast.Type;

public abstract class Type {
    public abstract String toString();

    public boolean equals(Type obj) {
        return this.toString().equals(obj.toString());
    }
}
