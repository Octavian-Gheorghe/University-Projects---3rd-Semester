package Model.Statement;

import Model.ADT.My_I_Dict;
import Model.Program_State.Program_State;
import Model.Type.IType;
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
    public My_I_Dict<String, IType> typeCheck(My_I_Dict<String, IType> typeEnv) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}
