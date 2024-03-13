package Model.Value;

import Model.Type.I_Type;

public interface I_Value {
    I_Type getType();
    I_Value deepCopy();
}
