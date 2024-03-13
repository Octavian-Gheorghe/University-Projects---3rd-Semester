package Repository;

import Model.Program_State.Program_State;
import Exception.ADT_Exception;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<Program_State> getProgramList();
    void setProgramList(List<Program_State> programStates);
//    ProgramState getCurrentState();
    void addProgram(Program_State program);
    void logPrgStateExec(Program_State programState) throws ADT_Exception, IOException;
    void emptyLogFile() throws IOException;
}
