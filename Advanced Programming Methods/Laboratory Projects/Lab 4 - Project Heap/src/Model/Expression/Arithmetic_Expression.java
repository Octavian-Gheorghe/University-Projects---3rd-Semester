package Model.Expression;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Type.Int_Type;
import Model.Value.I_Value;
import Model.Value.Int_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;

public class Arithmetic_Expression implements I_Expression {
    I_Expression expr1;
    I_Expression expr2;
    char operator;

    public Arithmetic_Expression(char o, I_Expression e1, I_Expression e2) {
        expr1 = e1;
        expr2 = e2;
        operator = o;
    }
    @Override
    public I_Value eval(My_I_Dict<String, I_Value> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        I_Value value1, value2;
        value1 = expr1.eval(symTable, heap);
        if (value1.getType().equals(new Int_Type())) {
            value2 = expr2.eval(symTable, heap);
            if (value2.getType().equals(new Int_Type())) {
                Int_Value int1 = (Int_Value) value1;
                Int_Value int2 = (Int_Value) value2;
                int a, b;
                a = int1.getValue();
                b = int2.getValue();
                if (operator == '+')
                    return new Int_Value(a + b);
                else if (operator == '-')
                    return new Int_Value(a - b);
                else if (operator == '*')
                    return new Int_Value(a * b);
                else if (operator == '/')
                    if (b == 0)
                        throw new Division_By_Zero_Exception("ERROR: DIVISION BY ZERO!");
                    else
                        return new Int_Value(a / b);
            } else
                throw new Expression_Evaluation_Exception("ERROR: Second operand is not an integer.");
        } else
            throw new Expression_Evaluation_Exception("ERROR: First operand is not an integer.");
        return null;
    }

    @Override
    public I_Expression deepCopy() {
        return new Arithmetic_Expression(operator, expr1.deepCopy(), expr2.deepCopy());
    }

    @Override
    public String toString() {
        return expr1.toString() + " " + operator + " " + expr2.toString();
    }
}
