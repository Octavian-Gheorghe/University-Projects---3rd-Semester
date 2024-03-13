package Model.Expression;

import Model.ADT.My_I_Dictionary;
import Model.ADT.My_I_Heap;
import Model.Value.IValue;
import Model.Value.Ref_Value;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;

public class Read_Heap_Expression implements IExpression{
    private IExpression expression;

    public Read_Heap_Expression(IExpression expression){
        this.expression = expression;
    }

    @Override
    public IValue eval(My_I_Dictionary<String, IValue> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        IValue val = expression.eval(symTable, heap);
        if(!(val instanceof Ref_Value))
            throw new Expression_Evaluation_Exception(String.format("%s is not of RefType",val));

        Ref_Value refValue = (Ref_Value) val;
        return heap.get(refValue.getAddress());
    }

    @Override
    public IExpression deepCopy() {
        return new Read_Heap_Expression(expression.deepCopy());
    }

    @Override
    public String toString(){
        return String.format("ReadHeap(%s)", expression);
    }
}
