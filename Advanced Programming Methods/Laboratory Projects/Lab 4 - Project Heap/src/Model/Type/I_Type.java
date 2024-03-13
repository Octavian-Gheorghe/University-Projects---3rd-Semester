package Model.Type;
import Model.Value.I_Value;

public interface I_Type {
    boolean equals(I_Type anotherType);
    I_Value defaultValue();

    I_Type deepCopy();
}
