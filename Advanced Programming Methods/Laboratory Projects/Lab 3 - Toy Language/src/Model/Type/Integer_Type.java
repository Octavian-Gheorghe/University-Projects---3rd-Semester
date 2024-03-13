package Model.Type;

import Model.Value.I_Value;
import Model.Value.Integer_Value;
public class Integer_Type implements I_Type {

    public boolean equals(Object another) {
        return another instanceof Integer_Type;
    }

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public I_Value default_value() {
        return new Integer_Value(0);
    }
}
