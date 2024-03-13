package Model.Statement;

import Model.Program_State.Program_State;
import Exception.ADT_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class Nop_Statement implements IStatement{
    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new Nop_Statement();
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
