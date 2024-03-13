package Model.Statement;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Expression.I_Expression;
import Model.Program_State.Program_State;
import Model.Value.I_Value;
import Model.Value.RefValue;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class Write_Heap_Statement implements I_Statement {
    private final String variableName;
    private final I_Expression expression;

    public Write_Heap_Statement(String variableName, I_Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_Dict<String, I_Value> symTable = state.getSymTable();
        My_I_Heap heap = state.getHeap();

        if(!symTable.contains(variableName))
            throw new Statement_Execution_Exception(String.format("%s not present in the Symbol table", variableName));

        I_Value value = symTable.lookUp(variableName);
        if(!(value instanceof RefValue))
            throw new Statement_Execution_Exception(String.format("%s not of RefType", value));

        RefValue refValue = (RefValue) value;
        I_Value evaluated = expression.eval(symTable, heap);
        if(!evaluated.getType().equals(refValue.getLocationType()))
            throw new Statement_Execution_Exception(String.format("%s not of %s", evaluated, refValue.getLocationType()));

        heap.update(refValue.getAddress(), evaluated);
        state.setHeap(heap);

        return state;
    }

    @Override
    public I_Statement deepCopy() {
        return new Write_Heap_Statement(variableName, expression.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("WriteHeap(%s, %s)", variableName, expression);
    }
}
