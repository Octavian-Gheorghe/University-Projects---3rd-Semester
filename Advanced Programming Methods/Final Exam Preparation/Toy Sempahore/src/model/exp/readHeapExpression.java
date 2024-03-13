/*
1.5. Heap Reading: Define and integrate the following expression
rH(expression)
• the expression must be evaluated to a RefValue. If not, the execution is stopped with an appropriate error message.
• Take the address component of the RefValue computed before and use it to access Heap table and return the value
 associated to that address. If the address is not a key in Heap table, the execution is stopped with an
 appropriate error message.
• In order to implement the evaluation of the new expression, you have to change the signature of the eval
method of the expressions classes as follows
Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Integer,Value> hp)
Example:
 */
package model.exp;

import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.MyException;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class readHeapExpression implements Exp{

    private Exp expression;
    public readHeapExpression(Exp expression){
        this.expression = expression;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> symTbl, MyIHeap<Integer, Value> heap) throws MyException {
        Value evaluatedExpression = expression.eval(symTbl, heap);
        if (evaluatedExpression instanceof RefValue) {
            RefValue refValue = (RefValue) evaluatedExpression;
            int addr = refValue.getAddress();
            if (heap.containsKey(addr)) {
                return heap.get(addr);
            }
            else {
                throw new MyException("location does not exist!");
            }
        }
        else {
            throw new MyException("expression must be RefValue");
        }
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ=expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "readHeapExpression{" + expression.toString() + "}";
    }
}
