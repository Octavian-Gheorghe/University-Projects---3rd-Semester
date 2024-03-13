package Model.Statement;

import Model.ADT.*;
import Model.My_Exception;
import Model.Program_State;
import Model.Expression.I_Expression;
import Model.Value.I_Value;

public class Print_Statement implements I_Statement {
    I_Expression expression;
    public Print_Statement(I_Expression givenExpression){
        expression =givenExpression;}
    public I_Expression get_expression() {return expression;}
    public void set_expression(I_Expression expression) {this.expression = expression;}
    public String toString(){ return "print(" + expression.toString() + ")";}

    @Override
    public Program_State execute(Program_State state) throws My_Exception {
        I_Stack<I_Statement> exeStack = state.get_Exe_stack();
        I_List<I_Value> out = state.get_Out();
        I_Dictionary<String, I_Value> symTable = state.get_Sym_table();
        out.add(expression.evaluate(symTable));
        return state;
    }


}
