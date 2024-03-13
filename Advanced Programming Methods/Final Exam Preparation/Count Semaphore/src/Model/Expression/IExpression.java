package Model.Expression;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;

public interface IExpression {
    IValue eval(My_I_Dict<String, IValue> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception;
    IExpression deepCopy();
    //1
    IType  typeCheck(My_I_Dict<String, IType> typeEnv) throws Expression_Evaluation_Exception, ADT_Exception;
}
