package Model.Expression;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Value.I_Value;
import Model.Value.RefValue;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;

public class Read_Heap_Expression implements I_Expression {
    private I_Expression expression;

    public Read_Heap_Expression(I_Expression expression){
        this.expression = expression;
    }

    @Override
    public I_Value eval(My_I_Dict<String, I_Value> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        I_Value val = expression.eval(symTable, heap);
        if(!(val instanceof RefValue))
            throw new Expression_Evaluation_Exception(String.format("%s is not of RefType",val));

        RefValue refValue = (RefValue) val;
        return heap.get(refValue.getAddress());
    }

    @Override
    public I_Expression deepCopy() {
        return new Read_Heap_Expression(expression.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("ReadHeap(%s)", expression);
    }
}
