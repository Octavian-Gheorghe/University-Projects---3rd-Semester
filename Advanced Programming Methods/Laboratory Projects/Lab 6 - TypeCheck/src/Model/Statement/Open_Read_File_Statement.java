package Model.Statement;

import Model.ADT.My_I_Dict;
import Model.Expression.IExpression;
import Model.Program_State.Program_State;
import Model.Type.IType;
import Model.Type.String_Type;
import Model.Value.IValue;
import Model.Value.String_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Open_Read_File_Statement implements IStatement{
    private final IExpression expr;

    public Open_Read_File_Statement(IExpression e){
        expr = e;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        IValue value = expr.eval(state.getSymTable(), state.getHeap());
        if (value.getType().equals(new String_Type())) {
            String_Value fileName = (String_Value) value;
            My_I_Dict<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.contains(fileName.getValue())) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(fileName.getValue()));
                } catch (FileNotFoundException e) {
                    throw new Statement_Execution_Exception(String.format("ERROR: %s could not be opened", fileName.getValue()));
                }
                fileTable.put(fileName.getValue(), br);
                state.setFileTable(fileTable);
            } else {
                throw new Statement_Execution_Exception(String.format("ERROR: %s is already opened", fileName.getValue()));
            }
        } else {
            throw new Statement_Execution_Exception(String.format("ERROR: %s does not evaluate to StringType", expr.toString()));
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new Open_Read_File_Statement(expr.deepCopy());
    }

    @Override
    public My_I_Dict<String, IType> typeCheck(My_I_Dict<String, IType> typeEnv) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception {
        if (expr.typeCheck(typeEnv).equals(new String_Type()))
            return typeEnv;
        else
            throw new Statement_Execution_Exception("ERROR: OpenReadFile requires a string expression.");
    }

    @Override
    public String toString() {
        return String.format("OpenReadFile(%s)", expr.toString());
    }
}
