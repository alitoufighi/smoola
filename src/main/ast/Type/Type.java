package ast.Type;

import ast.Type.ArrayType.ArrayType;
import ast.Type.PrimitiveType.BooleanType;
import ast.Type.PrimitiveType.IntType;
import ast.Type.PrimitiveType.StringType;
import ast.Type.UserDefinedType.UserDefinedType;

public abstract class Type {
    public abstract String toString();

    public boolean equals(Type obj) {
        return this.toString().equals(obj.toString());
    }

    public String getCodeString(){
        if(this instanceof IntType){
            return "I";
        } else if(this instanceof StringType){
            return "Ljava/lang/String ;";
        } else if(this instanceof ArrayType){
            return "[I";
        } else if(this instanceof BooleanType){
            return "Z";
        } else if(this instanceof UserDefinedType){
            return "L"+((UserDefinedType) this).getName().getName();
        } else
            return ""; //error
    }
}
