package Model.Type;

import Model.Value.IValue;
import Model.Value.Bool_Value;

public class Bool_Type implements IType {

    @Override
    public boolean equals(IType anotherType) {
        return anotherType instanceof Bool_Type;
    }

    @Override
    public IValue defaultValue() {
        return new Bool_Value(false);
    }

    @Override
    public IType deepCopy() {
        return new Bool_Type();
    }

    @Override
    public String toString(){
        return "bool";
    }
}
