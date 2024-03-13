package Model.Expression;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Type.Bool_Type;
import Model.Value.Bool_Value;
import Model.Value.I_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;

import java.util.Objects;

public class Logic_Expression implements I_Expression {
    I_Expression expr1;
    I_Expression expr2;
    String operator;

    public Logic_Expression(I_Expression e1, I_Expression e2, String o){
        expr1 = e1;
        expr2 = e2;
        operator = o;
    }
    @Override
    public I_Value eval(My_I_Dict<String, I_Value> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        I_Value value1, value2;
        value1 = expr1.eval(symTable, heap);
        if (value1.getType().equals(new Bool_Type())) {
            value2 = expr2.eval(symTable, heap);
            if (value2.getType().equals(new Bool_Type())) {
                Bool_Value bool1 = (Bool_Value) value1;
                Bool_Value bool2 = (Bool_Value) value2;
                boolean b1, b2;
                b1 = bool1.getValue();
                b2 = bool2.getValue();
                if (Objects.equals(operator, "and")) {
                    return new Bool_Value(b1 && b2);
                } else if (Objects.equals(operator, "or")) {
                    return new Bool_Value(b1 || b2);
                }
            } else {
                throw new Expression_Evaluation_Exception("Second operand is not a boolean.");
            }
        } else {
            throw new Expression_Evaluation_Exception("First operand is not a boolean.");
        }
        return null;
    }

    @Override
    public I_Expression deepCopy() {
        return new Logic_Expression(expr1.deepCopy(), expr2.deepCopy(), operator);
    }

    @Override
    public String toString() {
        return expr1.toString() + " " + operator + " " + expr2.toString();
    }
}
