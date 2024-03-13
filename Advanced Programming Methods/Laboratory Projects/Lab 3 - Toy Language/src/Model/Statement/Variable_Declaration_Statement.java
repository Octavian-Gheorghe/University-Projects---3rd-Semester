package Model.Statement;

import Model.ADT.*;
import Model.My_Exception;
import Model.Program_State;
import Model.Type.I_Type;
import Model.Value.I_Value;
public class Variable_Declaration_Statement implements I_Statement {
    private String name;
    private I_Type type;
    public Variable_Declaration_Statement(String name, I_Type type){
        this.name = name;
        this.type = type;
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public I_Type getType() {return type;}
    public void setType(I_Type type) {this.type = type;}
    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public Program_State execute(Program_State state) throws My_Exception {
       I_Stack<I_Statement> exeStack = state.get_Exe_stack();
         I_Dictionary<String, I_Value> symTable = state.get_Sym_table();
            if(symTable.key_isDefined(name)){
                throw new My_Exception("Variable already declared");
            }
            else{
                symTable.add(name, type.default_value());
            }
            return state;
    }
}
