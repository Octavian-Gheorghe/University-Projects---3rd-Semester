package Model.Value;

import Model.Type.I_Type;
import Model.Type.Bool_Type;

public class Bool_Value implements I_Value {
    private final boolean value;

    public Bool_Value(boolean v){
        value = v;
    }

    public boolean getValue(){
        return value;
    }

    @Override
    public I_Type getType() {
        return new Bool_Type();
    }

    @Override
    public I_Value deepCopy() {
        return new Bool_Value(value);
    }

    @Override
    public boolean equals(Object anotherValue){
        if(!(anotherValue instanceof Bool_Value))
            return false;
        Bool_Value castVal = (Bool_Value) anotherValue;
        return this.value == castVal.value;
    }

    public String toString(){
        return value ? "true" : "false";
    }
}
