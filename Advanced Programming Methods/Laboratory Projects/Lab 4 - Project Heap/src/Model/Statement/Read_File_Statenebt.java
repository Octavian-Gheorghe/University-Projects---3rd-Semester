package Model.Statement;

import Model.ADT.My_I_Dict;
import Model.Expression.I_Expression;
import Model.Program_State.Program_State;
import Model.Type.Int_Type;
import Model.Type.String_Type;
import Model.Value.I_Value;
import Model.Value.Int_Value;
import Model.Value.String_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

import java.io.BufferedReader;
import java.io.IOException;

public class Read_File_Statenebt implements I_Statement {
    private final I_Expression expr;
    private final String varName;

    public Read_File_Statenebt(I_Expression e, String vName)
    {
        expr = e;
        varName = vName;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_Dict<String, I_Value> symTable = state.getSymTable();
        My_I_Dict<String, BufferedReader> fileTable = state.getFileTable();

        if (symTable.contains(varName))
        {
            I_Value value = symTable.lookUp(varName);
            if (value.getType().equals(new Int_Type()))
            {
                value = expr.eval(symTable, state.getHeap());
                if (value.getType().equals(new String_Type())) {
                    String_Value castValue = (String_Value) value;
                    if (fileTable.contains(castValue.getValue()))
                    {
                        BufferedReader br = fileTable.lookUp(castValue.getValue());
                        try {
                            String line = br.readLine();
                            if (line == null)
                                line = "0";
                            symTable.put(varName, new Int_Value(Integer.parseInt(line)));
                        } catch (IOException e) {
                            throw new Statement_Execution_Exception(String.format("ERROR: Could not read from file %s", castValue));
                        }
                    } else {
                        throw new Statement_Execution_Exception(String.format("ERROR: The file table does not contain %s", castValue));
                    }
                } else {
                    throw new Statement_Execution_Exception(String.format("ERROR: %s does not evaluate to StringType", value));
                }
            } else {
                throw new Statement_Execution_Exception(String.format("ERROR: %s is not of type IntType", value));
            }
        } else {
            throw new Statement_Execution_Exception(String.format("ERROR: %s is not present in the symTable.", varName));
        }
        return state;
    }

    @Override
    public I_Statement deepCopy() {
        return new Read_File_Statenebt(expr.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return String.format("ReadFile(%s, %s)", expr.toString(), varName);
    }
}
