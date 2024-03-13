
/*
1.6.Heap Writing: Define and integrate the following statement
wH(Var_Name, expression)
• it is a statement which takes a variable and an expression, the variable contains the heap address,
 the expression represents the new value that is going to be stored into the heap
• first we check if var_name is a variable defined in SymTable, if its type is a Ref type and if the
address from the RefValue associated in SymTable is a key in Heap. If not, the execution is stopped with an
appropriate error message.
• Second the expression is evaluated and the result must have its type equal to the locationType of the var_name type.
 If not, the execution is stopped with an appropriate message.
• Third we access the Heap using the address from var_name and that Heap entry is updated to the result of the
expression evaluation.
Example: Ref int v;new(v,20);print(rH(v)); wH(
 */
package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class WriteStmt implements IStmt{

    private String var_name;
    private Exp expression;

    public WriteStmt(String var_name, Exp expression)
    {
        this.var_name = var_name;
        this.expression = expression;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        if(!(symTable.isDefined(var_name)))
            throw new MyException("The variable name is not defined in the Symbol Table!");

        Value symbolTableValue = symTable.lookup(var_name);
        if(!(symbolTableValue instanceof RefValue))
            throw new MyException("the type of the variable is not RefType!");

        RefValue refValueSymbolTable = (RefValue) symbolTableValue;
        if(!(heap.containsKey(refValueSymbolTable.getAddress())))
            throw new MyException("The address is not a key in the heap!");

        Value evaluatedExpression = expression.eval(symTable, heap);
        if(!(evaluatedExpression.getType().equals(((RefType) symbolTableValue.getType()).getInner())))
            throw new MyException("The result type and the location type should be the same!");
        heap.update(refValueSymbolTable.getAddress(), evaluatedExpression);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new WriteStmt(var_name, expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp = expression.typecheck(typeEnv);
        Type typeVar = typeEnv.lookup(var_name);
        if(!(typeVar.equals(new RefType(typeExp))))
            throw new MyException("Left side and right side have different types!");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "WriteHeap("+ var_name+ "," +expression + ')';
    }
}
