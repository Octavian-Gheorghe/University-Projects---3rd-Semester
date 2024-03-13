package Model.Statement;

import Model.ADT.My_I_Dictionary;
import Model.Expression.IExpression;
import Model.Program_State.Program_State;
import Model.Type.Int_Type;
import Model.Type.String_Type;
import Model.Value.IValue;
import Model.Value.Int_Value;
import Model.Value.String_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

import java.io.BufferedReader;
import java.io.IOException;

public class Read_File_Statement implements IStatement{
    private final IExpression expr;
    private final String varName;

    public Read_File_Statement(IExpression e, String vName){
        expr = e;
        varName = vName;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_Dictionary<String, IValue> symTable = state.getSymTable();
        My_I_Dictionary<String, BufferedReader> fileTable = state.getFileTable();

        if (symTable.contains(varName)) {
            IValue value = symTable.lookUp(varName);
            if (value.getType().equals(new Int_Type())) {
                value = expr.eval(symTable, state.getHeap());
                if (value.getType().equals(new String_Type())) {
                    String_Value castValue = (String_Value) value;
                    if (fileTable.contains(castValue.getValue())) {
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
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new Read_File_Statement(expr.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return String.format("ReadFile(%s, %s)", expr.toString(), varName);
    }
}
