package Model.Statement;

import Model.ADT.My_I_Dict;
import Model.Expression.I_Expression;
import Model.Program_State.Program_State;
import Model.Type.String_Type;
import Model.Value.I_Value;
import Model.Value.String_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

import java.io.BufferedReader;
import java.io.IOException;

public class Close_Read_File_Statement implements I_Statement {
    private final I_Expression expr;

    public Close_Read_File_Statement(I_Expression e){
        expr = e;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        I_Value value = expr.eval(state.getSymTable(), state.getHeap());
        if (!value.getType().equals(new String_Type()))
            throw new Statement_Execution_Exception(String.format("ERROR: %s does not evaluate to StringValue", expr));
        String_Value fileName = (String_Value) value;
        My_I_Dict<String, BufferedReader> fileTable = state.getFileTable();
        if (!fileTable.contains(fileName.getValue()))
            throw new Statement_Execution_Exception(String.format("ERROR: %s is not present in the FileTable", value));
        BufferedReader br = fileTable.lookUp(fileName.getValue());
        try {
            br.close();
        } catch (IOException e) {
            throw new Statement_Execution_Exception(String.format("ERROR: Unexpected error in closing %s", value));
        }
        fileTable.remove(fileName.getValue());
        state.setFileTable(fileTable);
        return null;
    }

    @Override
    public I_Statement deepCopy() {
        return new Close_Read_File_Statement(expr.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("CloseReadFile(%s)", expr.toString());
    }
}
