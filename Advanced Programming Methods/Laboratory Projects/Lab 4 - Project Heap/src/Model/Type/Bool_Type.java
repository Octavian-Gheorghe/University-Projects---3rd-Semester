package Model.Type;

import Model.Value.I_Value;
import Model.Value.Bool_Value;

public class Bool_Type implements I_Type {

    @Override
    public boolean equals(I_Type anotherType) {
        return anotherType instanceof Bool_Type;
    }

    @Override
    public I_Value defaultValue() {
        return new Bool_Value(false);
    }

    @Override
    public I_Type deepCopy() {
        return new Bool_Type();
    }

    @Override
    public String toString(){
        return "bool";
    }
}
