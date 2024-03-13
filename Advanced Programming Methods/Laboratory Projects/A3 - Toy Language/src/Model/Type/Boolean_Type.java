package Model.Type;

import Model.Value.I_Value;
import Model.Value.Boolean_Value;

public class Boolean_Type implements I_Type {
    @Override
    public I_Value default_value() {
        return new Boolean_Value(false);
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof Boolean_Type;
    }

    @Override
    public String toString() {
        return "boolean";
    }
}

