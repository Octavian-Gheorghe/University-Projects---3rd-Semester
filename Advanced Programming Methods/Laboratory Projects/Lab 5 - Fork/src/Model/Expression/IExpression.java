package Model.Expression;

import Model.ADT.My_I_Dictionary;
import Model.ADT.My_I_Heap;
import Model.Value.IValue;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;

public interface IExpression {
    IValue eval(My_I_Dictionary<String, IValue> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception;
    IExpression deepCopy();
}
