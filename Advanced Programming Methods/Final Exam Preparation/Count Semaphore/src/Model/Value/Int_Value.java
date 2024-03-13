package Model.Value;

import Model.Type.IType;
import Model.Type.Int_Type;

public class Int_Value implements IValue {
    private final int value;

    public Int_Value(int v){
        this.value = v;
    }

    public int getValue(){
        return value;
    }

    @Override
    public IType getType() {
        return new Int_Type();
    }

    @Override
    public IValue deepCopy() {
        return new Int_Value(value);
    }

    @Override
    public boolean equals(Object anotherValue){
        if(!(anotherValue instanceof Int_Value))
            return false;
        Int_Value castVal = (Int_Value) anotherValue;
        return value == castVal.value;
    }
    @Override
    public String toString() {
        return String.format("%d", value);
    }
}
