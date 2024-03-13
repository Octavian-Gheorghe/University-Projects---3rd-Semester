package Model.Statement;

import Model.ADT.My_I_List;
import Model.Expression.I_Expression;
import Model.Program_State.Program_State;
import Model.Value.I_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class Print_Statement implements I_Statement {
    I_Expression expression;

    public Print_Statement(I_Expression e) {
        this.expression = e;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_List<I_Value> out = state.getOut();
        out.add(expression.eval(state.getSymTable(), state.getHeap()));
        state.setOut(out);
        return state;
    }

    @Override
    public I_Statement deepCopy() {
        return new Print_Statement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("Print(%s)", expression.toString());
    }
}
