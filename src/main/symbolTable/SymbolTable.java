package symbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

	private SymbolTable pre;
	private HashMap<String, SymbolTableItem> items = new HashMap<>();

	// Static members region

	public static SymbolTable top;
	
	private static Stack<SymbolTable> stack = new Stack<>();

	// Use it in pass 1 scope start
	public static void push(SymbolTable symbolTable) {
		if(top != null)
			stack.push(top);
		top = symbolTable;
	}

	// replaces variable item in top symbol table (should only do once :D) (place a boolean for shadowed, in var dec?
	public void shadowVariable(String name, SymbolTableVariableItem item){
		items.remove(name+"@var");
		try {
			put(item);
		} catch (ItemAlreadyExistsException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, SymbolTableItem> getItems() { return items; }

	// Use it in pass1 scope end
	public static SymbolTable pop() {
		return top = stack.pop();
	}

	// End of static members region

	public SymbolTable() {
		pre = null;
	}

	// this is a copy constructor
	public SymbolTable(SymbolTable st){
		this.pre = st;
        this.items = new HashMap<>();
        for(Map.Entry<String, SymbolTableItem> entry : st.items.entrySet())
            items.put(entry.getKey(), entry.getValue());
	}

	// used in pass 1
	public String getClassNameInClassScope() throws NotInClassScopeException {
		ArrayList<SymbolTableItem> items = new ArrayList<>();

		for(Map.Entry<String, SymbolTableItem> entry : this.items.entrySet()){
			if(entry.getValue() instanceof SymbolTableClassItem)
				items.add(entry.getValue());
		}
		if(items.size() != 1){ //TODO: CHECK
			throw new NotInClassScopeException();
		}
		return items.get(0).getName();
	}

//	public SymbolTable(SymbolTable pre) {
//		this.pre = pre;
//		this.items = new HashMap<String, SymbolTableItem>();
//	}

	public void put(SymbolTableItem item) throws ItemAlreadyExistsException {
		if(items.containsKey(item.getKey()))
			throw new ItemAlreadyExistsException();
		items.put(item.getKey(), item);
	}

	public SymbolTableItem getInCurrentScope(String key) {
		return items.get(key);
	}

	public SymbolTableItem get(String key) throws ItemNotFoundException {
//		System.out.println("Getting "+key);
//		System.out.println(Program.getClassSymbolTable("Main"));
		SymbolTableItem value = items.get(key);
		if(value == null && pre != null)
			return pre.get(key);
		else if(value == null)
			throw new ItemNotFoundException();
		else
			return value;
	}

	public SymbolTable getPreSymbolTable() {
		return pre;
	}
}