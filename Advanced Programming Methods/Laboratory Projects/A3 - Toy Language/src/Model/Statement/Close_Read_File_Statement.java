package Model.Statement;

import Model.Program_State;
import Model.ADT.I_Dictionary;
import Model.ADT.I_Stack;
import Model.ADT.I_File_Table;
import Model.Expression.I_Expression;
import Model.Value.I_Value;
import Model.Value.String_Value;
import Model.My_Exception;
import Model.Type.String_Type;

import java.io.BufferedReader;
import java.io.IOException;

public class Close_Read_File_Statement implements I_Statement {
    private I_Expression expression;
    public Close_Read_File_Statement(I_Expression expression) {
        this.expression = expression;
    }
    public I_Expression get_expression() {
        return expression;
    }
    public void set_expression(I_Expression expression) {
        this.expression = expression;
    }
    public String toString() {
        return "closeRFile(" +
                "expression=" + expression +
                ')';
    }

    @Override
    public Program_State execute(Program_State state) throws My_Exception {
        I_Dictionary<String, I_Value> symbolTable = state.get_Sym_table();
        I_File_Table<String, BufferedReader> fileTable = state.get_File_table();
        I_Stack<I_Statement> stack = state.get_Exe_stack();


        I_Value value = expression.evaluate(symbolTable);
        if(value.get_type().equals(new String_Type())){
            String_Value stringValue = (String_Value)value;
            String filename = stringValue.get_value();
            BufferedReader bufferedReader = fileTable.lookup(filename);
            try{
                bufferedReader.close();
            }catch (IOException e){
                throw new My_Exception("File "+filename+" cannot be closed");

            }
            fileTable.remove(filename);
        }
        else{
            throw new My_Exception("Invalid type");
        }
        return null;
    }
}
