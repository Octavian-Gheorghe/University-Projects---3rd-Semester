package Model;

import Model.ADT.*;
import Model.Statement.I_Statement;
import Model.Value.I_Value;
import java.io.BufferedReader;


public class Program_State {
    I_Stack<I_Statement> exe_stack;
    I_Dictionary<String, I_Value> sym_table;
    I_List<I_Value> out;
    I_File_Table<String, BufferedReader> file_table;
    I_Statement original_program; //in case of hard reset
    public Program_State(I_Stack<I_Statement> stk, I_Dictionary<String, I_Value> symtbl, I_List<I_Value> ot, I_File_Table<String,BufferedReader> fileTable , I_Statement prg){
        exe_stack =stk;
        sym_table =symtbl;
        out = ot;
        original_program =prg;
        this.file_table = fileTable;
        stk.push(prg);
    }

    @Override
    public String toString() {
        return "PrgState{" +
                "exeStack=" + exe_stack +
                ", symTable=" + sym_table +
                ", out=" + out +
                '}';
    }

    public I_Stack<I_Statement> get_Exe_stack() {
        return exe_stack;
    }
    public I_Dictionary<String, I_Value> get_Sym_table() {
        return sym_table;
    }
    public I_List<I_Value> get_Out() {
        return out;
    }
    public I_File_Table<String, BufferedReader> get_File_table() {
        return file_table;
    }

    public void set_File_table(I_File_Table<String, BufferedReader> file_table) {
        this.file_table = file_table;
    }
    public void set_Exe_stack(I_Stack<I_Statement> exe_stack) {
        this.exe_stack = exe_stack;
    }

    public void set_Sym_table(I_Dictionary<String, I_Value> sym_table) {
        this.sym_table = sym_table;
    }
    public void set_Out(I_List<I_Value> out) {
        this.out = out;
    }
    public boolean is_not_completed(){
        return !exe_stack.isEmpty();
    }
    public Program_State oneStep() throws Exception {
        if(exe_stack.isEmpty()) throw new Exception("Program state stack is empty");
        I_Statement current_stmt = exe_stack.pop();
        return current_stmt.execute(this);
    }
    public I_Statement get_Original_program() {
        return original_program;
    }
}
