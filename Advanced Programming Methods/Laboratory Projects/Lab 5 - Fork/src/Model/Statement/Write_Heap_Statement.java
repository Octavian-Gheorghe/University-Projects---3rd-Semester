package Model.Statement;

import Model.ADT.My_I_Dictionary;
import Model.ADT.My_I_Heap;
import Model.Expression.IExpression;
import Model.Program_State.Program_State;
import Model.Value.IValue;
import Model.Value.Ref_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class Write_Heap_Statement implements IStatement{
    private final String variableName;
    private final IExpression expression;

    public Write_Heap_Statement(String variableName, IExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_Dictionary<String, IValue> symTable = state.getSymTable();
        My_I_Heap heap = state.getHeap();

        if(!symTable.contains(variableName))
            throw new Statement_Execution_Exception(String.format("%s not present in the Symbol table", variableName));

        IValue value = symTable.lookUp(variableName);
        if(!(value instanceof Ref_Value))
            throw new Statement_Execution_Exception(String.format("%s not of RefType", value));

        Ref_Value refValue = (Ref_Value) value;
        IValue evaluated = expression.eval(symTable, heap);
        if(!evaluated.getType().equals(refValue.getLocationType()))
            throw new Statement_Execution_Exception(String.format("%s not of %s", evaluated, refValue.getLocationType()));

        heap.update(refValue.getAddress(), evaluated);
        state.setHeap(heap);

        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new Write_Heap_Statement(variableName, expression.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("WriteHeap(%s, %s)", variableName, expression);
    }
}
