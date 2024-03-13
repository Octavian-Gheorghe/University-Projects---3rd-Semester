package Model.Statement;

import Model.My_Exception;
import Model.Program_State;

public class                                                                                                                                                                                                                                                                                                                                                                                                                                                                No_Operation_Statement implements I_Statement {
    public No_Operation_Statement(){}
    @Override
    public Program_State execute(Program_State state) throws My_Exception {
        return null;
    }

}
