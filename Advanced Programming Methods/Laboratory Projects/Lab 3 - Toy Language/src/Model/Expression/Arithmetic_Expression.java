package Model.Expression;
import Model.ADT.I_Dictionary;
import Model.My_Exception;
import Model.Type.Integer_Type;
import Model.Value.Integer_Value;
import Model.Value.I_Value;

public class Arithmetic_Expression implements I_Expression {
    private I_Expression e1;
    private I_Expression e2;
    public static final int ADD = 1;
    public static final int SUBTRACT = 2;
    public static final int MULTIPLY = 3;
    public static final int DIVIDE = 4;
    private int op; // 1-plus, 2-minus, 3-star, 4-divide
    public Arithmetic_Expression(String stringOp, I_Expression e1, I_Expression e2){
        this.e1 = e1;
        this.e2 = e2;
        switch(stringOp){
            case "+" -> this.op = ADD;
            case "-" -> this.op = SUBTRACT;
            case "*" -> this.op = MULTIPLY;
            case "/" -> this.op = DIVIDE;
        }
    }
    public I_Expression get_e1(){
        return this.e1;
    }
    public I_Expression get_e2(){
        return this.e2;
    }
    public int get_operation(){
        return this.op;
    }
    public void set_e1(I_Expression e1){
        this.e1 = e1;
    }
    public void set_e2(I_Expression e2){
        this.e2 = e2;
    }
    public void set_operation(int op){
        this.op = op;
    }
    @Override
    public String toString()
    {
        return "ArithExp{"+
                "e1="+e1+
                ", e2="+e2+
                ", op="+op+
                '}';
    }

    @Override
    public I_Value evaluate(I_Dictionary<String, I_Value> symbolTable) throws My_Exception {
        I_Value leftValue, rightValue;
        leftValue = e1.evaluate(symbolTable);

        if (leftValue.get_type().equals(new Integer_Type())) {
            rightValue = e2.evaluate(symbolTable);

            if (rightValue.get_type().equals(new Integer_Type()))
            {
                Integer_Value intValue1 = (Integer_Value) leftValue;
                Integer_Value intValue2 = (Integer_Value) rightValue;
                int leftIntValue, rightIntValue;
                leftIntValue = intValue1.get_value();
                rightIntValue = intValue2.get_value();

                if (op == Arithmetic_Expression.ADD) {
                    return new Integer_Value(leftIntValue + rightIntValue);
                }
                if (op == Arithmetic_Expression.SUBTRACT) {
                    return new Integer_Value(leftIntValue - rightIntValue);
                }
                if (op == Arithmetic_Expression.MULTIPLY) {
                    return new Integer_Value(leftIntValue * rightIntValue);
                }
                if (op == Arithmetic_Expression.DIVIDE) {
                    if (rightIntValue == 0) {
                        throw new My_Exception("division by zero");
                    } else {
                        return new Integer_Value(leftIntValue / rightIntValue);
                    }
                }
            } else {
                throw new My_Exception("second operand is not an integer");
            }
        } else {
            throw new My_Exception("first operand is not an integer");
        }

        return null;
    }



}

