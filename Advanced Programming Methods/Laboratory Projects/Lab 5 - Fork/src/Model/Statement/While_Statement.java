package Model.Statement;

import Model.ADT.My_I_Stack;
import Model.Expression.IExpression;
import Model.Program_State.Program_State;
import Model.Type.Bool_Type;
import Model.Value.Bool_Value;
import Model.Value.IValue;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class While_Statement implements IStatement{
    private final IExpression expression;
    private final IStatement statement;

    public While_Statement(IExpression expression, IStatement statement){
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        IValue value = expression.eval(state.getSymTable(), state.getHeap());
        My_I_Stack<IStatement> stack = state.getExeStack();

        if(!value.getType().equals(new Bool_Type()))
            throw new Statement_Execution_Exception(String.format("%s is not of BoolType", value));

        Bool_Value boolValue = (Bool_Value) value;
        if(boolValue.getValue()){
            stack.push(this);
            stack.push(statement);
        }

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new While_Statement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("while(%s) { %s }", expression, statement);
    }
}
