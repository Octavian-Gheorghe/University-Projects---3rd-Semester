package Repository;

import Model.Program_State.Program_State;
import Exception.ADT_Exception;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    //A.1.!
    private List<Program_State> programStates;
    private final String logFilePath;

    //A.1!
    public Repository(Program_State prg, String logFilePath){
        programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
        addProgram(prg);
    }

    //A.2!
    @Override
    public List<Program_State> getProgramList() {
        return programStates;
    }

    //A.3!
    @Override
    public void setProgramList(List<Program_State> prgSts) {
        programStates = prgSts;
    }

    //A.4
//    @Override
//    public ProgramState getCurrentState() {
//        return this.programStates.get(this.currentPosition);
//    }

    @Override
    public void addProgram(Program_State program) {
        programStates.add(program);
    }

    //A.5!
    @Override
    public void logPrgStateExec(Program_State programState) throws ADT_Exception, IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programState.programStateToString());
        logFile.close();
    }

    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.close();
    }
}
