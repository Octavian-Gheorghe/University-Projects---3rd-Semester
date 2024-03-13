package Model.Statement;

import Model.ADT.My_I_Dictionary;
import Model.Program_State.Program_State;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.Statement_Execution_Exception;

public class Variable_Declaration_Statement implements IStatement{
    String name;
    IType type;

    public Variable_Declaration_Statement(String n, IType t){
        name = n;
        type = t;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception {
        My_I_Dictionary<String, IValue> symTable = state.getSymTable();
        if (symTable.contains(name))
            throw new Statement_Execution_Exception(String.format("ERROR: %s already exists in the symTable", name));
        symTable.put(name, type.defaultValue());
        state.setSymTable(symTable);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new Variable_Declaration_Statement(name, type);
    }

    @Override
    public String toString() {
        return String.format("%s %s", type.toString(), name);
    }
}
