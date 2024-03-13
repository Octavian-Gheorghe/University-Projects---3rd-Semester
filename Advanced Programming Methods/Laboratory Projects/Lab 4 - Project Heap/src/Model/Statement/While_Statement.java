package Model.Statement;

import Model.ADT.My_I_Stack;
import Model.Expression.I_Expression;
import Model.Program_State.Program_State;
import Model.Type.Bool_Type;
import Model.Value.Bool_Value;
import Model.Value.I_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class While_Statement implements I_Statement {
    private final I_Expression expression;
    private final I_Statement statement;

    public While_Statement(I_Expression expression, I_Statement statement){
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        I_Value value = expression.eval(state.getSymTable(), state.getHeap());
        My_I_Stack<I_Statement> stack = state.getExeStack();

        if(!value.getType().equals(new Bool_Type()))
            throw new Statement_Execution_Exception(String.format("%s is not of BoolType", value));

        Bool_Value boolValue = (Bool_Value) value;
        if(boolValue.getValue()) {
            stack.push(this);
            stack.push(statement);
        }

        return state;
    }

    @Override
    public I_Statement deepCopy() {
        return new While_Statement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("while(%s) { %s }", expression, statement);
    }
}
