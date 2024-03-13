package Model.Statement;
import Model.ADT.*;
import Model.My_Exception;
import Model.Program_State;
import Model.Type.*;
import Model.Value.*;
import Model.Expression.*;
import java.io.BufferedReader;
import java.io.IOException;


public class Open_File_Statement implements I_Statement {
    private I_Expression expression;
    private String variable_name;
    public Open_File_Statement(I_Expression expression, String variable_name){
        this.expression = expression;
        this.variable_name = variable_name;
    }
    public I_Expression get_expression(){
        return this.expression;
    }
    public void set_variable_name(String variable_name){
        this.variable_name = variable_name;
    }
    public void set_expression(I_Expression expression){
        this.expression = expression;
    }
    public String get_variable_name(){
        return this.variable_name;
    }
    @Override
    public String toString(){
        return "ReadFileStatement(" +
                "expression=" + this.expression.toString() +
                ", variable_name=" + this.variable_name +'\'' +
                ')';
    }

    @Override
    public Program_State execute(Program_State state) throws My_Exception {
       I_Dictionary<String, I_Value> symTable = state.get_Sym_table();
       I_File_Table<String, BufferedReader> fileTable = state.get_File_table();
       I_Stack<I_Statement> stack = state.get_Exe_stack();

       if(symTable.key_isDefined(variable_name))
       {
           I_Value value = symTable.lookup(variable_name);
           I_Type type = value.get_type();
           if(type.equals(new Integer_Type())){
               String_Value stringValue = (String_Value) expression.evaluate(symTable);
                String filename = stringValue.get_value();
                BufferedReader bufferedReader = fileTable.lookup(filename);
                try{
                    String line = bufferedReader.readLine();
                    Integer_Value intValue;
                    if(line == null){
                        intValue = new Integer_Value(0);
                    }
                    else{
                        intValue = new Integer_Value(Integer.parseInt(line));
                    }
                    symTable.update(variable_name, intValue);
                }
                catch(IOException e){
                    throw new My_Exception(e.getMessage());
                }

           }
           else{
               throw new My_Exception("Variable name is not of type int");
           }
       }
       return null;
    }

}
