package Model.Type;

import Model.Value.I_Value;
import Model.Value.String_Value;

public class String_Type implements I_Type {
    @Override
    public boolean equals(I_Type anotherType) {
        return anotherType instanceof String_Type;
    }

    @Override
    public I_Value defaultValue() {
        return new String_Value("");
    }

    @Override
    public I_Type deepCopy() {
        return new String_Type();
    }

    @Override
    public String toString(){
        return "string";
    }
}
