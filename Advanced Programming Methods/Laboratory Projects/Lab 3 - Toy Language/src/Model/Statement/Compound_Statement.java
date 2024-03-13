package Model.Statement;

import Model.My_Exception;
import Model.Program_State;
import Model.ADT.I_Stack;

public class Compound_Statement implements I_Statement {
    private I_Statement first;
    private I_Statement second;
    public Compound_Statement(I_Statement v, I_Statement v2){
        first=v;
        second =v2;
    }
    public I_Statement get_first() {
        return first;
    }
    public void set_first(I_Statement first) {
        this.first = first;
    }
    public I_Statement get_second() {
        return second;
    }
    public void set_second(I_Statement second) {
        this.second = second;
    }
    public String toString(){
        return "("+first.toString()+";"+ second.toString()+")";
    }

    @Override
    public Program_State execute(Program_State state) throws My_Exception {
        I_Stack<I_Statement> exeStack=state.get_Exe_stack();
        exeStack.push(second);
        exeStack.push(first);
        return state;
    }

}
