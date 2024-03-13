package Model.Expression;
import Model.My_Exception;
import Model.ADT.I_Dictionary;
import Model.Type.Integer_Type;
import Model.Value.Boolean_Value;
import Model.Value.Integer_Value;
import Model.Value.I_Value;

public class Relational_Expression implements I_Expression {
    private I_Expression firstExpression;
    private I_Expression secondExpression;
    int intOperand;

    public static final int LESS_THAN = 1;
    public static final int LESS_THAN_OR_EQUAL = 2;
    public static final int GREATER_THAN = 3;
    public static final int GREATER_THAN_OR_EQUAL = 4;
    public static final int EQUAL = 5;
    public static final int NOT_EQUAL = 6;

    public Relational_Expression(String stringOperand, I_Expression firstExpression, I_Expression secondExpression){
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        switch(stringOperand){
            case "<" -> this.intOperand = LESS_THAN;
            case "<=" -> this.intOperand = LESS_THAN_OR_EQUAL;
            case ">" -> this.intOperand = GREATER_THAN;
            case ">=" -> this.intOperand = GREATER_THAN_OR_EQUAL;
            case "==" -> this.intOperand = EQUAL;
            case "!=" -> this.intOperand = NOT_EQUAL;
        }
    }
    public I_Expression get_first_expression(){
        return this.firstExpression;
    }
    public I_Expression get_second_expression(){
        return this.secondExpression;
    }
    public int get_int_operand(){
        return this.intOperand;
    }
    public void set_first_expression(I_Expression firstExpression){
        this.firstExpression = firstExpression;
    }
    public void set_second_expression(I_Expression secondExpression){
        this.secondExpression = secondExpression;
    }
    public void set_int_operand(int intOperand){
        this.intOperand = intOperand;
    }
    @Override
    public String toString()
    {
        return "RelationalExpressions{"+
                "firstExpression="+firstExpression+
                ", secondExpression="+secondExpression+
                ", intOperand="+intOperand+
                '}';
    }


    @Override
    public I_Value evaluate(I_Dictionary<String, I_Value> symbolTable) throws My_Exception {
        I_Value firstValue, secondValue;
        firstValue = firstExpression.evaluate(symbolTable);
        if(firstValue.get_type().equals(new Integer_Type())){
            secondValue = secondExpression.evaluate(symbolTable);
            if(secondValue.get_type().equals(new Integer_Type())){
                Integer_Value IntValue1 = (Integer_Value) firstValue;
                Integer_Value IntValue2 = (Integer_Value) secondValue;
                int number1, number2;
                number1 = IntValue1.get_value();
                number2 = IntValue2.get_value();
                switch(intOperand){
                    case LESS_THAN -> {
                        if(number1 < number2)
                            return new Boolean_Value(true);
                        else
                            return new Boolean_Value(false);
                    }
                    case LESS_THAN_OR_EQUAL -> {
                        if(number1 <= number2)
                            return new Boolean_Value(true);
                        else
                            return new Boolean_Value(false);
                    }
                    case GREATER_THAN -> {
                        if(number1 > number2)
                            return new Boolean_Value(true);
                        else
                            return new Boolean_Value(false);
                    }
                    case GREATER_THAN_OR_EQUAL -> {
                        if(number1 >= number2)
                            return new Boolean_Value(true);
                        else
                            return new Boolean_Value(false);
                    }
                    case EQUAL -> {
                        if(number1 == number2)
                            return new Boolean_Value(true);
                        else
                            return new Boolean_Value(false);
                    }
                    case NOT_EQUAL -> {
                        if(number1 != number2)
                            return new Boolean_Value(true);
                        else
                            return new Boolean_Value(false);
                    }
                    default -> throw new My_Exception("Invalid operand!");
                }

            }
            else throw new My_Exception("Second operand is not an integer!");

        }
        else throw new My_Exception("First operand is not an integer!");

    }
}
