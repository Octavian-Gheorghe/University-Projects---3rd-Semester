package Model.Statement;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Expression.I_Expression;
import Model.Program_State.Program_State;
import Model.Type.I_Type;
import Model.Type.Ref_Type;
import Model.Value.I_Value;
import Model.Value.RefValue;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class Heap_Allocation_Statement implements I_Statement {
    private String variableName;
    private I_Expression expression;

    public Heap_Allocation_Statement(String variableName, I_Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_Dict<String, I_Value> symTable = state.getSymTable();
        My_I_Heap heap = state.getHeap();

        if(!symTable.contains(variableName))
            throw new Statement_Execution_Exception(String.format("%s is not in the Symbol Table", variableName));

        I_Value variableValue = symTable.lookUp(variableName);
        if(!(variableValue.getType() instanceof Ref_Type))
            throw new Statement_Execution_Exception(String.format("%s is not a RefType", variableName));

        I_Value evaluated = expression.eval(symTable, heap);
        I_Type locationType = ((RefValue)variableValue).getLocationType();
        if (!locationType.equals(evaluated.getType()))
            throw new Statement_Execution_Exception(String.format("%s not of %s", variableName, evaluated.getType()));

        int newPosition = heap.add(evaluated);
        symTable.put(variableName, new RefValue(newPosition, locationType));
        state.setSymTable(symTable);
        state.setHeap(heap);
        return state;
    }

    @Override
    public I_Statement deepCopy() {
        return new Heap_Allocation_Statement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", variableName, expression);
    }
}
