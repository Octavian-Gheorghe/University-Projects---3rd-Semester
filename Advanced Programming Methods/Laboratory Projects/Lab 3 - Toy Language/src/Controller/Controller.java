package Controller;
import Model.ADT.I_List;
import Model.Value.I_Value;
import Repository.I_Repository;
import Model.My_Exception;
import Model.Program_State;
import Model.ADT.I_Stack;
import Model.Statement.I_Statement;

import java.util.List;

public class Controller {
    private I_Repository repository;
    public Controller(I_Repository repository) {
        this.repository = repository;
    }
    public I_Repository get_repository() {
        return repository;
    }
    public void set_repository(I_Repository repository) {
        this.repository = repository;
    }
    public List<Program_State> get_program_states(){
        return repository.get_program_List();
    }

    //add a method that gets the output list not the exe stack
    public I_List<I_Value> get_out(Program_State state){
        return state.get_Out();
    }

    public Program_State oneStep(Program_State state) throws My_Exception {
        I_Stack<I_Statement> exeStack = state.get_Exe_stack();
        if (exeStack.isEmpty()) throw new My_Exception("prgstate stack is empty");
        I_Statement crtStmt = exeStack.pop();
        return crtStmt.execute(state);
    }

    public void allStep() throws My_Exception {
        Program_State prg = repository.get_current_program();
        repository.log_program_state(prg);
        while (!prg.get_Exe_stack().isEmpty()) {
            oneStep(prg);
            repository.log_program_state(prg);
        }
    }
    public void displayPrgState(Program_State state) {
        System.out.println(state);
    }
    private void printThings(){
        Program_State programState = repository.get_current_program();
        System.out.print("------------------------------------------------------\n");

        System.out.print("*****ExecutionStack*****\n");
        System.out.print(programState.get_Exe_stack().toString() + "\n");

        System.out.print("*****OutputList*****\n");
        System.out.print(programState.get_Out() + "\n");

        System.out.print("*****SymbolTable*****\n");
        System.out.print(programState.get_Sym_table().toString() + "\n");


        System.out.print("*****FileTable*****\n");
        System.out.print(programState.get_File_table().toString() + "\n");

        System.out.print("------------------------------------------------------\n");
    }
}
