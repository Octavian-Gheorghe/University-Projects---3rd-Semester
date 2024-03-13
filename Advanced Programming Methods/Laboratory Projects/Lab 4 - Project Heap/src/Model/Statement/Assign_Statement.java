package Model.Statement;

import Model.ADT.My_I_Dict;
import Model.Expression.I_Expression;
import Model.Program_State.Program_State;
import Model.Type.I_Type;
import Model.Value.I_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class Assign_Statement implements I_Statement {
    private final String key;
    private final I_Expression expression;

    public Assign_Statement(String k, I_Expression e){
        key = k;
        expression = e;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_Dict<String, I_Value> symbolTable = state.getSymTable();

        if (symbolTable.contains(key)) {
            I_Value value = expression.eval(symbolTable, state.getHeap());
            I_Type typeId = (symbolTable.lookUp(key)).getType();
            if (value.getType().equals(typeId)) {
                symbolTable.update(key, value);
            } else {
                throw new Statement_Execution_Exception("ERROR: Assigment mismatch:  " + key);
            }
        } else {
            throw new Statement_Execution_Exception("ERROR: Undeclared variable:  " + key);
        }
        state.setSymTable(symbolTable);
        return state;
    }

    @Override
    public I_Statement deepCopy() {
        return new Assign_Statement(key, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("%s = %s", key, expression.toString());
    }
}
