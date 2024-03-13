package Model.Value;

import Model.Type.I_Type;
import Model.Type.Integer_Type;

public class Integer_Value implements I_Value {
    private int value;
    public Integer_Value(int val)
    {
        this.value =val;
    }
    public int get_value()
    {
        return this.value;
    }
    public void set_value(int value)
    {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Integer_Value;
    }
    @Override
    public String toString()
    {
        return Integer.toString(this.value);
    }
    @Override
    public I_Type get_type()
    {
        return new Integer_Type();
    }
}
