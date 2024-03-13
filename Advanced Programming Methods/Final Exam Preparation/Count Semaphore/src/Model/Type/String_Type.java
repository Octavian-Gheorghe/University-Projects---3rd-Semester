package Model.Type;

import Model.Value.IValue;
import Model.Value.String_Value;

public class String_Type implements IType{
    @Override
    public boolean equals(IType anotherType) {
        return anotherType instanceof String_Type;
    }

    @Override
    public IValue defaultValue() {
        return new String_Value("");
    }

    @Override
    public IType deepCopy() {
        return new String_Type();
    }

    @Override
    public String toString(){
        return "string";
    }
}
