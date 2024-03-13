package Model.Statement;

import Model.ADT.My_I_Stack;
import Model.Program_State.Program_State;
import Exception.ADT_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class Compound_Statement implements  IStatement{
    IStatement firstStmt;
    IStatement secondStmt;

    public Compound_Statement(IStatement f, IStatement s){
        firstStmt = f;
        secondStmt = s;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception {
        My_I_Stack<IStatement> stack = state.getExeStack();
        stack.push(secondStmt);
        stack.push(firstStmt);
        state.setExeStack(stack);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new Compound_Statement(firstStmt.deepCopy(), secondStmt.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%s|%s)", firstStmt.toString(), secondStmt.toString());
    }
}
