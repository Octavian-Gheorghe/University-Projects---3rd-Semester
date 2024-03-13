package Model.Statement;

import Model.ADT.My_I_Stack;
import Model.Program_State.Program_State;
import Exception.ADT_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class Compound_Statement implements I_Statement {
    I_Statement firstStmt;
    I_Statement secondStmt;

    public Compound_Statement(I_Statement f, I_Statement s){
        firstStmt = f;
        secondStmt = s;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception {
        My_I_Stack<I_Statement> stack = state.getExeStack();
        stack.push(secondStmt);
        stack.push(firstStmt);
        state.setExeStack(stack);
        return state;
    }

    @Override
    public I_Statement deepCopy() {
        return new Compound_Statement(firstStmt.deepCopy(), secondStmt.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%s|%s)", firstStmt.toString(), secondStmt.toString());
    }
}
