package Model.Statement;

import Model.ADT.My_I_Stack;
import Model.Expression.IExpression;
import Model.Program_State.Program_State;
import Model.Value.Bool_Value;
import Model.Value.IValue;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class If_Statement implements  IStatement{
    IExpression expression;
    IStatement thenStmt;
    IStatement elseStmt;

    public If_Statement(IExpression e, IStatement thenS, IStatement elseS){
        expression = e;
        thenStmt = thenS;
        elseStmt = elseS;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        IValue result = this.expression.eval(state.getSymTable(), state.getHeap());
        if (result instanceof Bool_Value boolResult) {
            IStatement statement;
            if (boolResult.getValue()) {
                statement = thenStmt;
            } else {
                statement = elseStmt;
            }

            My_I_Stack<IStatement> stack = state.getExeStack();
            stack.push(statement);
            state.setExeStack(stack);
            return null;
        } else {
            throw new Statement_Execution_Exception("ERROR: provide a boolean expression in an if statement.");
        }
    }

    @Override
    public IStatement deepCopy() {
        return new If_Statement(expression.deepCopy(), thenStmt.deepCopy(), elseStmt.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("if(%s){%s}else{%s}", expression.toString(), thenStmt.toString(), elseStmt.toString());
    }
}
