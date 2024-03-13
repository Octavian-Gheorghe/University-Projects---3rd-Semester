package Model.Expression;
import Model.ADT.I_Dictionary;
import Model.My_Exception;
import Model.Value.I_Value;

public interface I_Expression {
    I_Value evaluate(I_Dictionary<String, I_Value> symTbl) throws My_Exception;
}
