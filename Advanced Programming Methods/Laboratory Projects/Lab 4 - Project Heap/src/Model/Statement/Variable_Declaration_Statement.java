package Model.Statement;

import Model.ADT.My_I_Dict;
import Model.Program_State.Program_State;
import Model.Type.I_Type;
import Model.Value.I_Value;
import Exception.Statement_Execution_Exception;

public class Variable_Declaration_Statement implements I_Statement {
    String name;
    I_Type type;

    public Variable_Declaration_Statement(String n, I_Type t){
        name = n;
        type = t;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception {
        My_I_Dict<String, I_Value> symTable = state.getSymTable();
        if (symTable.contains(name))
            throw new Statement_Execution_Exception(String.format("ERROR: %s already exists in the symTable", name));
        symTable.put(name, type.defaultValue());
        state.setSymTable(symTable);
        return state;
    }

    @Override
    public I_Statement deepCopy() {
        return new Variable_Declaration_Statement(name, type);
    }

    @Override
    public String toString() {
        return String.format("%s %s", type.toString(), name);
    }
}
