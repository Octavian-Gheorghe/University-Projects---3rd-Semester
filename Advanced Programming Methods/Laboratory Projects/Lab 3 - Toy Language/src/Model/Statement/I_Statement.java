package Model.Statement;

import Model.My_Exception;
import Model.Program_State;


public interface I_Statement {
    public Program_State execute(Program_State state) throws My_Exception;
}
