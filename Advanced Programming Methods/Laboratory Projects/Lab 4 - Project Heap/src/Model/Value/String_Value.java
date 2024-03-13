package Model.Value;

import Model.Type.I_Type;
import Model.Type.String_Type;

public class String_Value implements I_Value {
    private final String value;

    public String_Value(String v){
        value = v;
    }
    @Override
    public I_Type getType() {
        return new String_Type();
    }

    @Override
    public I_Value deepCopy() {
        return new String_Value(value);
    }

    @Override
    public boolean equals(Object anotherValue) {
        if (!(anotherValue instanceof String_Value))
            return false;
        String_Value castValue = (String_Value) anotherValue;
        return this.value.equals(castValue.value);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "\"" + this.value + "\"";
    }
}
