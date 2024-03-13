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
    private List<Program_State> programStates;
    private int currentPosition;
    private final String logFilePath;

    public Repository(Program_State prg, String logFilePath){
        programStates = new ArrayList<>();
        currentPosition = 0;
        this.logFilePath = logFilePath;
        addProgram(prg);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currPos) {
        this.currentPosition = currPos;
    }

    @Override
    public List<Program_State> getProgramList() {
        return programStates;
    }

    @Override
    public void setProgramStates(List<Program_State> prgSts) {
        programStates = prgSts;
    }

    @Override
    public Program_State getCurrentState() {
        return this.programStates.get(this.currentPosition);
    }

    @Override
    public void addProgram(Program_State program) {
        programStates.add(program);
    }

    @Override
    public void logPrgStateExec() throws ADT_Exception, IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println(programStates.get(0).programStateToString());
        logFile.close();
    }

    @Override
    public void emptyLogFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, false)));
        logFile.close();
    }
}
