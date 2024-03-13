package model.value;

import model.type.RefType;
import model.type.Type;

public class RefValue implements Value{
    private int address;
    private Type locationType;

    public RefValue(int address, Type locationType)
    {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public Type getType() {
        /*For Ref Ref int:
        The location type is Ref(int) and the new RefType(locationType) is Ref(Ref(int))*/
        return new RefType(locationType);
    }
    public int getAddress()
    {
        return this.address;
    }

    @Override
    public String toString() {
        return "(" + this.address + "," + this.locationType.toString() + ")";
    }
}
