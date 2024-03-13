package Repository;

import Model.My_Exception;
import Model.Program_State;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements I_Repository {
    private List<Program_State> prgList;
    private String logFilePath;
    public Repository(List<Program_State> p, String givenLogFilePath) {
        prgList = p;
        logFilePath = givenLogFilePath;
    }

    @Override
    public List<Program_State> get_program_List() {
        return this.prgList;
    }

    @Override
    public Program_State get_current_program() {
        return this.prgList.get(0);
    }
    @Override
    public void add_program(Program_State prg) {
        this.prgList.add(prg);
    }

    @Override
    public void log_program_state(Program_State program) throws My_Exception {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            //logFile.println("******************************");
            logFile.println("ExeStack");
            logFile.println(program.get_Exe_stack().toString());
            logFile.println("SymbolTable");
            logFile.println(program.get_Sym_table().toString());
            logFile.println("Output");
            logFile.println(program.get_Out().toString());
            logFile.println("FileTable");
            logFile.println(program.get_File_table().toString());
            //logFile.println("******************************");
            logFile.println("\n");
            logFile.close();
        } catch (IOException e) {
            throw new My_Exception("Error writing to log file");
        }
    }


}
