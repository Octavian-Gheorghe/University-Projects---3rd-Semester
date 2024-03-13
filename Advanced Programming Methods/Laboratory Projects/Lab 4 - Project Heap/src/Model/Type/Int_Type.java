package Model.Type;

import Model.Value.I_Value;
import Model.Value.Int_Value;

public class Int_Type implements I_Type {
    @Override
    public boolean equals(I_Type anotherType){
        return anotherType instanceof Int_Type;
    }

    @Override
    public I_Value defaultValue() {
        return new Int_Value(0);
    }

    @Override
    public I_Type deepCopy() {
        return new Int_Type();
    }

    @Override
    public String toString() {
        return "int";
    }

}
