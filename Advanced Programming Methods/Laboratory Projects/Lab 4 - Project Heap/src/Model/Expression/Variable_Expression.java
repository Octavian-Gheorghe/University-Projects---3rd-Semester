package Model.Expression;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Value.I_Value;
import Exception.ADT_Exception;
import Exception.Expression_Evaluation_Exception;

public class Variable_Expression implements I_Expression {
    String key;

    public Variable_Expression(String k){
        key = k;
    }

    @Override
    public I_Value eval(My_I_Dict<String, I_Value> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception {
        return symTable.lookUp(key);
    }

    @Override
    public I_Expression deepCopy() {
        return new Variable_Expression(key);
    }

    @Override
    public String toString(){
        return key;
    }
}
