package Model.Value;

import Model.Type.String_Type;
import Model.Type.I_Type;

public class String_Value implements I_Value {
    private String StringValue;
    public String_Value(String value) {
        this.StringValue = value;
    }
    public String get_value() {
        return StringValue;
    }
    public String toString() {
        return StringValue;
    }
    public boolean equals(Object another) {
        if(another instanceof String_Value)
            return true;
        else
            return false;
    }
    public void set_value(String value) {
        this.StringValue = value;
    }

    @Override
    public I_Type get_type() {
        return new String_Type();
    }
}
