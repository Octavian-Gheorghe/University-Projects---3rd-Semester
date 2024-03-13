package Model.Expression;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Value.I_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;

public interface I_Expression {
    I_Value eval(My_I_Dict<String, I_Value> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception;
    I_Expression deepCopy();
}
