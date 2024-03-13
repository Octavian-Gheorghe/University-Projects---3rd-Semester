package Model.Value;

import Model.Type.I_Type;
import Model.Type.Ref_Type;

public class RefValue implements I_Value {
    private final int address;
    private final I_Type locationType;

    public RefValue(int address, I_Type locationType){
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public I_Type getType() {
        return new Ref_Type(locationType);
    }

    public int getAddress(){
        return address;
    }

    public I_Type getLocationType(){
        return locationType;
    }

    @Override
    public I_Value deepCopy() {
        return new RefValue(address, locationType.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("(%d, %s)", address, locationType);
    }
}
