package Model.Expression;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.Type.IType;
import Model.Type.Ref_Type;
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

    //2
    @Override
    public IValue eval(My_I_Dict<String, IValue> symTable, My_I_Heap heap) throws Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
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
    public IType typeCheck(My_I_Dict<String, IType> typeEnv) throws Expression_Evaluation_Exception, ADT_Exception {
        IType type = expression.typeCheck(typeEnv);
        if (type instanceof Ref_Type) {
            Ref_Type refType = (Ref_Type) type;
            return refType.getInner();
        } else
            throw new Expression_Evaluation_Exception("ERROR: The rH argument is not a RefType.");
    }

    @Override
    public String toString(){
        return String.format("ReadHeap(%s)", expression);
    }
}
