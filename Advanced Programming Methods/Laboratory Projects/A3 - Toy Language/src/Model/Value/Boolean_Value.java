package Model.Value;
import Model.Type.I_Type;
import Model.Type.Boolean_Type;

public class Boolean_Value implements I_Value {
    private boolean val;

    public Boolean_Value(boolean value) {
        this.val = value;
    }

    public boolean get_value() {
        return val;
    }

    public void set_value(boolean value) {
        this.val = value;
    }

    @Override
    public I_Type get_type() {
        return new Boolean_Type();
    }

    @Override
    public String toString() {
        return Boolean.toString(val);
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof Boolean_Value;
    }

}
