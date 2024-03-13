package Repository;

import Model.Program_State.Program_State;
import Exception.ADT_Exception;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<Program_State> getProgramList();
    void setProgramStates(List<Program_State> programStates);
    Program_State getCurrentState();
    void addProgram(Program_State program);
    void logPrgStateExec() throws ADT_Exception, IOException;
    void emptyLogFile() throws IOException;
}
