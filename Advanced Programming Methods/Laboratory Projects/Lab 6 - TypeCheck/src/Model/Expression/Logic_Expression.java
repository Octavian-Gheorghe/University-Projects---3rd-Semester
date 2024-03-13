package Model.Expression;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Type.Bool_Type;
import Model.Type.IType;
import Model.Value.Bool_Value;
import Model.Value.IValue;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;

import java.util.Objects;

public class Logic_Expression implements IExpression{
    IExpression expr1;
    IExpression expr2;
    String operator;

    public Logic_Expression(IExpression e1, IExpression e2, String o){
        expr1 = e1;
        expr2 = e2;
        operator = o;
    }
    //2
    @Override
    public IValue eval(My_I_Dict<String, IValue> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        IValue value1, value2;
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
    public IExpression deepCopy() {
        return new Logic_Expression(expr1.deepCopy(), expr2.deepCopy(), operator);
    }

    @Override
    public IType typeCheck(My_I_Dict<String, IType> typeEnv) throws Expression_Evaluation_Exception, ADT_Exception {
        IType type1, type2;
        type1 = expr1.typeCheck(typeEnv);
        type2 = expr2.typeCheck(typeEnv);
        if (type1.equals(new Bool_Type())) {
            if (type2.equals(new Bool_Type())) {
                return new Bool_Type();
            } else
                throw new Expression_Evaluation_Exception("Second operand is not a boolean.");
        } else
            throw new Expression_Evaluation_Exception("First operand is not a boolean.");
    }

    @Override
    public String toString() {
        return expr1.toString() + " " + operator + " " + expr2.toString();
    }
}
