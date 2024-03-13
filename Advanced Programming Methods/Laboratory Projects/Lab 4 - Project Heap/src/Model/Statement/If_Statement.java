package Model.Statement;

import Model.ADT.My_I_Stack;
import Model.Expression.I_Expression;
import Model.Program_State.Program_State;
import Model.Value.Bool_Value;
import Model.Value.I_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class If_Statement implements I_Statement {
    I_Expression expression;
    I_Statement thenStmt;
    I_Statement elseStmt;

    public If_Statement(I_Expression e, I_Statement thenS, I_Statement elseS){
        expression = e;
        thenStmt = thenS;
        elseStmt = elseS;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        I_Value result = this.expression.eval(state.getSymTable(), state.getHeap());
        if (result instanceof Bool_Value boolResult) {
            I_Statement statement;
            if (boolResult.getValue()) {
                statement = thenStmt;
            } else {
                statement = elseStmt;
            }

            My_I_Stack<I_Statement> stack = state.getExeStack();
            stack.push(statement);
            state.setExeStack(stack);
            return state;
        } else {
            throw new Statement_Execution_Exception("ERROR: provide a boolean expression in an if statement.");
        }
    }

    @Override
    public I_Statement deepCopy() {
        return new If_Statement(expression.deepCopy(), thenStmt.deepCopy(), elseStmt.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("if(%s){%s}else{%s}", expression.toString(), thenStmt.toString(), elseStmt.toString());
    }
}
