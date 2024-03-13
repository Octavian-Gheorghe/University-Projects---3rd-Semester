package Model.Type;

import Model.Value.IValue;
import Model.Value.Int_Value;

public class Int_Type implements IType {
    @Override
    public boolean equals(IType anotherType){
        return anotherType instanceof Int_Type;
    }

    @Override
    public IValue defaultValue() {
        return new Int_Value(0);
    }

    @Override
    public IType deepCopy() {
        return new Int_Type();
    }

    @Override
    public String toString() {
        return "int";
    }

}
