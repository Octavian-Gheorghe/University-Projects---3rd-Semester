package Model.Expression;

import Model.ADT.I_Dictionary;
import Model.My_Exception;
import Model.Type.Boolean_Type;
import Model.Value.Boolean_Value;
import Model.Value.I_Value;

public class Logic_Expression implements I_Expression {
    I_Expression e1;
    I_Expression e2;
    public static final int AND = 1;
    public static final int OR = 2;

    int op; // 1-and, 2-or
    public Logic_Expression(String stringOp, I_Expression e1, I_Expression e2){
        this.e1 = e1;
        this.e2 = e2;
        switch(stringOp){
            case "&&" -> this.op = AND;
            case "||" -> this.op = OR;
        }
    }

    public I_Expression getE1(){
        return this.e1;
    }
    public I_Expression getE2(){
        return this.e2;
    }
    public int getOp(){
        return this.op;
    }
    public void setE1(I_Expression e1){
        this.e1 = e1;
    }
    public void setE2(I_Expression e2){
        this.e2 = e2;
    }
    public void setOp(int op){
        this.op = op;
    }

    @Override
    public I_Value evaluate(I_Dictionary<String, I_Value> tbl) throws My_Exception {
        I_Value leftValue, rightValue;
        leftValue = e1.evaluate(tbl);

        if (leftValue.get_type().equals(new Boolean_Type())) {
            rightValue = e2.evaluate(tbl);

            if (rightValue.get_type().equals(new Boolean_Type())) {
                Boolean_Value boolValue1 = (Boolean_Value) leftValue;
                Boolean_Value boolValue2 = (Boolean_Value) rightValue;
                boolean leftBoolValue, rightBoolValue;
                leftBoolValue = boolValue1.get_value();
                rightBoolValue = boolValue2.get_value();

                if (op == 1) return new Boolean_Value(leftBoolValue && rightBoolValue);
                if (op == 2) return new Boolean_Value(leftBoolValue || rightBoolValue);
            } else {
                throw new My_Exception("second operand is not a boolean");
            }
        } else {
            throw new My_Exception("first operand is not a boolean");
        }
        return null;
    }



}
