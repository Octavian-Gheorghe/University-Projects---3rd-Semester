package Model.Type;

import Model.Value.IValue;
import Model.Value.Ref_Value;

public class Ref_Type implements IType {
    private IType inner;

    public Ref_Type(IType inner){
        this.inner = inner;
    }

    IType getInner() { return inner; }

    @Override
    public boolean equals(IType anotherType) {
        if (anotherType instanceof Ref_Type)
            return inner.equals(((Ref_Type) anotherType).getInner());
        return false;
    }

    @Override
    public IValue defaultValue() {
        return new Ref_Value(0, inner);
    }

    @Override
    public IType deepCopy() {
        return new Ref_Type(inner.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("Ref(%s)", inner);
    }
}
