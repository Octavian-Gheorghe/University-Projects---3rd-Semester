package Model.Type;

import Model.Value.I_Value;
import Model.Value.RefValue;

public class Ref_Type implements I_Type {
    private I_Type inner;

    public Ref_Type(I_Type inner){
        this.inner = inner;
    }

    I_Type getInner() { return inner; }

    @Override
    public boolean equals(I_Type anotherType) {
        if (anotherType instanceof Ref_Type)
            return inner.equals(((Ref_Type) anotherType).getInner());
        return false;
    }

    @Override
    public I_Value defaultValue() {
        return new RefValue(0, inner);
    }

    @Override
    public I_Type deepCopy() {
        return new Ref_Type(inner.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("Ref(%s)", inner);
    }
}
