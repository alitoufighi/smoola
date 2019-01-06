package symbolTable;

import ast.Type.Type;

public class SymbolTableVariableItem extends SymbolTableItem {
    static private int indexCounter = 1;
    private int index;
    protected Type type;

    public SymbolTableVariableItem(String name, Type type) {
        this.name = name;
        this.type = type;
        this.index = indexCounter++;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String getKey() {
        return name.concat("@").concat("var");
    }

    public int getIndex() {
        return index;
    }

    public void resetIndex() { indexCounter = 1; }
}