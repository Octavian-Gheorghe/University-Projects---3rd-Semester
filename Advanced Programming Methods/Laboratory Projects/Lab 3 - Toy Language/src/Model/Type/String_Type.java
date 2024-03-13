package Model.Type;

import Model.Value.I_Value;
import Model.Value.String_Value;

public class String_Type implements I_Type {

    @Override
    public boolean equals(Object anotherObject) {
        if(anotherObject instanceof String_Type)
            return true;
        else
            return false;
    }
    public String toString() {
        return "string";
    }

    @Override
    public I_Value default_value() {
        return new String_Value("");
    }
}
