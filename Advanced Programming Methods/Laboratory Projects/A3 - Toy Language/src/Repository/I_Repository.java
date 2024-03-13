package Repository;

import Model.Program_State;
import Model.My_Exception;

import java.util.List;

public interface I_Repository {
    public List<Program_State> get_program_List();

    Program_State get_current_program();

    void add_program(Program_State prg);
    void log_program_state(Program_State program) throws My_Exception;
}
