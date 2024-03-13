package Model.Statement;
import Model.ADT.*;
import Model.My_Exception;
import Model.Program_State;
import Model.Expression.I_Expression;
import Model.Type.Boolean_Type;
import Model.Value.I_Value;
import Model.Value.Boolean_Value;
public class If_Statement implements I_Statement {
    private I_Expression expression;
    I_Statement thenS;
    I_Statement elseS;

    public If_Statement(I_Expression exp, I_Statement t, I_Statement e)
    {
        this.expression = exp;
        this.thenS = t;
        this.elseS = e;
    }
    public I_Expression get_expression() {
        return expression;
    }

    public void set_expression(I_Expression expression) {
        this.expression = expression;
    }

    public I_Statement get_ThenS() {
        return thenS;
    }

    public void set_ThenS(I_Statement thenS) {
        this.thenS = thenS;
    }

    public I_Statement get_ElseS() {
        return elseS;
    }

    public void set_ElseS(I_Statement elseS) {
        this.elseS = elseS;
    }
    public String toString(){
        return "(IF("+ expression.toString()+") THEN(" +thenS.toString()
                +")ELSE("+elseS.toString()+"))";}

    @Override
    public Program_State execute(Program_State state) throws My_Exception {
        I_Stack<I_Statement> exeStack = state.get_Exe_stack();
        I_Dictionary<String, I_Value> symTable = state.get_Sym_table();
        I_Value val = expression.evaluate(symTable);
        if(!val.get_type().equals(new Boolean_Type()))
            throw new My_Exception("Conditional expression is not a boolean");
        else{
            Boolean_Value boolVal = (Boolean_Value) val;
            if(boolVal.get_value())
                exeStack.push(thenS);
            else
                exeStack.push(elseS);
        }
        return state;
    }


}

