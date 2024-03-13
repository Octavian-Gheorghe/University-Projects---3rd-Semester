package Model.Statement;

import Model.ADT.*;
import Model.My_Exception;
import Model.Program_State;
import Model.Expression.I_Expression;
import Model.Type.I_Type;
import Model.Value.I_Value;
public class Assign_Statement implements I_Statement {
    private I_Expression expression;
    private String id;
    public Assign_Statement(String id, I_Expression exp){
        this.expression = exp;
        this.id = id;
    }
    public I_Expression get_expression() {
        return expression;
    }
    public String get_id() {
        return id;
    }
    public void set_expression(I_Expression expression) {
        this.expression = expression;
    }
    public void set_id(String id) {
        this.id = id;
    }
    public String toString(){
        return id + "=" + expression.toString();
    }

    @Override
    public Program_State execute(Program_State state) throws My_Exception {
        I_Stack<I_Statement> exeStack = state.get_Exe_stack();
        I_Dictionary<String, I_Value> symTable = state.get_Sym_table();
        if(symTable.key_isDefined(id)){
            I_Value val = expression.evaluate(symTable);
            I_Type typId = (symTable.lookup(id)).get_type();
            if(val.get_type().equals(typId))
                symTable.update(id, val);
            else
                throw new My_Exception("declared type of variable" + id + " and type of the assigned expression do not match");
        }
        else
            throw new My_Exception("the used variable" + id + " was not declared before");
        return state;
    }

}
