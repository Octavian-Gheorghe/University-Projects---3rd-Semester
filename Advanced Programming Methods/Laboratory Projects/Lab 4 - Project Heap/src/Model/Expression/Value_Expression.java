package Model.Expression;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Value.I_Value;
import Exception.ADT_Exception;
import Exception.Expression_Evaluation_Exception;

public class Value_Expression implements I_Expression {
    I_Value value;

    public Value_Expression(I_Value v){
        value = v;
    }
    @Override
    public I_Value eval(My_I_Dict<String, I_Value> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception {
        return value;
    }

    @Override
    public I_Expression deepCopy() {
        return new Value_Expression(value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
