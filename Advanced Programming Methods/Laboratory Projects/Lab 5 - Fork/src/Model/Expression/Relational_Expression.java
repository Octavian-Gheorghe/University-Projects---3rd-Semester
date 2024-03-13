package Model.Expression;

import Model.ADT.My_I_Dictionary;
import Model.ADT.My_I_Heap;
import Model.Type.Int_Type;
import Model.Value.Bool_Value;
import Model.Value.IValue;
import Model.Value.Int_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;

import java.util.Objects;

public class Relational_Expression implements IExpression{
    IExpression expr1;
    IExpression expr2;
    String operator;

    public Relational_Expression(String op, IExpression e1, IExpression e2){
        expr1 = e1;
        expr2 = e2;
        operator = op;
    }

    @Override
    public IValue eval(My_I_Dictionary<String, IValue> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        IValue value1, value2;
        value1 = expr1.eval(symTable, heap);
        if (value1.getType().equals(new Int_Type())) {
            value2 = expr2.eval(symTable, heap);
            if (value2.getType().equals(new Int_Type())) {
                Int_Value val1 = (Int_Value) value1;
                Int_Value val2 = (Int_Value) value2;
                int v1, v2;
                v1 = val1.getValue();
                v2 = val2.getValue();
                if (Objects.equals(this.operator, "<"))
                    return new Bool_Value(v1 < v2);
                else if (Objects.equals(this.operator, "<="))
                    return new Bool_Value(v1 <= v2);
                else if (Objects.equals(this.operator, "=="))
                    return new Bool_Value(v1 == v2);
                else if (Objects.equals(this.operator, "!="))
                    return new Bool_Value(v1 != v2);
                else if (Objects.equals(this.operator, ">"))
                    return new Bool_Value(v1 > v2);
                else if (Objects.equals(this.operator, ">="))
                    return new Bool_Value(v1 >= v2);
            } else
                throw new Expression_Evaluation_Exception("ERROR: Second operand not integer.");
        } else
            throw new Expression_Evaluation_Exception("ERROR: First operand not integer.");
        return null;
    }

    @Override
    public IExpression deepCopy() {
        return new Relational_Expression(operator, expr1.deepCopy(), expr2.deepCopy());
    }

    @Override
    public String toString() {
        return expr1.toString() + " " + operator + " " + expr2.toString();
    }
}
