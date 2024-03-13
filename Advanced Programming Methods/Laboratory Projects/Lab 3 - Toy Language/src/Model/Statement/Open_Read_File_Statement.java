package Model.Statement;

import Model.ADT.I_Dictionary;
import Model.ADT.I_File_Table;
import Model.ADT.I_Stack;

import Model.Program_State;
import Model.Type.String_Type;
import Model.Value.String_Value;
import Model.Value.I_Value;
import Model.My_Exception;
import Model.Expression.I_Expression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Open_Read_File_Statement implements I_Statement {
    private I_Expression expression;
    public Open_Read_File_Statement(I_Expression givenExppression){
        this.expression = givenExppression;
    }
    public String toString(){
        return "openRFile(" +
                "exp=" + expression.toString() +
                ")";
    }
    public void set_exppression(I_Expression givenExppression){
        this.expression = givenExppression;
    }
    public I_Expression get_expression(){
        return this.expression;
    }

    @Override
    public Program_State execute(Program_State state) throws My_Exception {
        I_Stack<I_Statement> stack = state.get_Exe_stack();
        I_Dictionary<String, I_Value> symbolTable = state.get_Sym_table();
        I_File_Table<String, BufferedReader> fileTable = state.get_File_table();
        I_Value value = this.expression.evaluate(symbolTable);
        if(value.get_type().equals(new String_Type())){
            String_Value stringValue = (String_Value)value;
            String file = stringValue.get_value();
            if(fileTable.key_isDefined(file)){
                throw new My_Exception("File already opened!");
            }
            else{
                try{
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    fileTable.add(file, bufferedReader);
                }
                catch(IOException e){
                    throw new My_Exception("File not found!");
                }
            }
        }
        return null;
    }
}
