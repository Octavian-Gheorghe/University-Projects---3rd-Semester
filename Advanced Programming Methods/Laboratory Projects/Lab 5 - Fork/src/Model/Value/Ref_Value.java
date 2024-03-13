package Model.Value;

import Model.Type.IType;
import Model.Type.Ref_Type;

public class Ref_Value implements IValue{
    private final int address;
    private final IType locationType;

    public Ref_Value(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public IType getType() {
        return new Ref_Type(locationType);
    }

    public int getAddress(){
        return address;
    }

    public IType getLocationType(){
        return locationType;
    }

    @Override
    public IValue deepCopy() {
        return new Ref_Value(address, locationType.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("(%d, %s)", address, locationType);
    }
}
