package Model.Expression;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.ADT_Exception;
import Exception.Expression_Evaluation_Exception;

public class Value_Expression implements IExpression{
    IValue value;

    public Value_Expression(IValue v){
        value = v;
    }
    @Override
    public IValue eval(My_I_Dict<String, IValue> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception {
        return value;
    }

    @Override
    public IExpression deepCopy() {
        return new Value_Expression(value);
    }

    @Override
    public IType typeCheck(My_I_Dict<String, IType> typeEnv) throws Expression_Evaluation_Exception, ADT_Exception {
        return value.getType();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
