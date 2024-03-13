package Model.Expression;

import Model.ADT.My_I_Dictionary;
import Model.ADT.My_I_Heap;
import Model.Value.IValue;
import Exception.ADT_Exception;
import Exception.Expression_Evaluation_Exception;

public class Variable_Expression implements IExpression{
    String key;

    public Variable_Expression(String k){
        key = k;
    }

    @Override
    public IValue eval(My_I_Dictionary<String, IValue> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception {
        return symTable.lookUp(key);
    }

    @Override
    public IExpression deepCopy() {
        return new Variable_Expression(key);
    }

    @Override
    public String toString(){
        return key;
    }
}
