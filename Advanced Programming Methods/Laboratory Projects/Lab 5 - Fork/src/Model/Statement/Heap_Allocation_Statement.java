package Model.Statement;

import Model.ADT.My_I_Dictionary;
import Model.ADT.My_I_Heap;
import Model.Expression.IExpression;
import Model.Program_State.Program_State;
import Model.Type.IType;
import Model.Type.Ref_Type;
import Model.Value.IValue;
import Model.Value.Ref_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class Heap_Allocation_Statement implements IStatement{
    private String variableName;
    private IExpression expression;

    public Heap_Allocation_Statement(String variableName, IExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_Dictionary<String, IValue> symTable = state.getSymTable();
        My_I_Heap heap = state.getHeap();

        if(!symTable.contains(variableName))
            throw new Statement_Execution_Exception(String.format("%s is not in the Symbol Table", variableName));

        IValue variableValue = symTable.lookUp(variableName);
        if(!(variableValue.getType() instanceof Ref_Type))
            throw new Statement_Execution_Exception(String.format("%s is not a RefType", variableName));

        IValue evaluated = expression.eval(symTable, heap);
        IType locationType = ((Ref_Value)variableValue).getLocationType();
        if (!locationType.equals(evaluated.getType()))
            throw new Statement_Execution_Exception(String.format("%s not of %s", variableName, evaluated.getType()));

        int newPosition = heap.add(evaluated);
        symTable.put(variableName, new Ref_Value(newPosition, locationType));
        state.setSymTable(symTable);
        state.setHeap(heap);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new Heap_Allocation_Statement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("New(%s, %s)", variableName, expression);
    }
}
