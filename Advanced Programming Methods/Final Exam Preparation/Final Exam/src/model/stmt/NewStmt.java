/*
• it is a statement which takes a variable name and an expression -
• check whether var_name is a variable in SymTable and its type is a RefType. If not, the execution is
stopped with an appropriate error message.-
• Evaluate the expression to a value and then compare the type of the value to the locationType from
the value associated to var_name in SymTable. If the types are not equal, the execution is stopped with
an appropriate error message. -
• Create a new entry in the Heap table such that a new key (new free address) is generated and it is associated
 to the result of the expression evaluation -
• in SymTable update the RefValue associated to the var_name such that the new RefValue has the same locationType
 and the address is equal to the new key generated in the Heap at the previous step-
Example: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
At the end of execution: Heap={1->20, 2->(1,int)}, SymTable={v->(1,int), a->(2,Ref int)} and Out={(1,int),(2,Ref int)}
 */

//HEAP ALLOCATION

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

public class NewStmt implements IStmt{
    private String var_name;
    private Exp expression;

    public NewStmt(String var_name, Exp expression){
        this.var_name = var_name;
        this.expression = expression;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symbolTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        if(!symbolTable.isDefined(var_name))
            throw new MyException("Variable is not declared in the SymbolTable!");

        Value getVariableFromSymTable = symbolTable.lookup(var_name);

        if(!(getVariableFromSymTable.getType() instanceof RefType))
            throw new MyException("The type of the variable should be RefType!");

        Value evaluatedExpression = expression.eval(symbolTable, heap);

        //check if the type of the evaluated expression equals the INNER TYPE (location type) of the RefValue from the symbol table
        if (!(evaluatedExpression.getType().equals(((RefType) getVariableFromSymTable.getType()).getInner())))
            throw new MyException("The type of the expression should be the same as the one from the inner type value!");

        //Create a new entry in the Heap table such that a new key (new free address) is generated and it is associated
        // to the result of the expression evaluation
        int getFreePosition = heap.getFirstFreeAdress();
        heap.put(getFreePosition, evaluatedExpression);

        // update the address from the symbol table correspondingly to the first free position generated from the heap table
        /*
        # Assuming SymTable is a dictionary with variable names as keys and RefValue as values
        # and Heap is a data structure for dynamic memory allocation

         def update_symtable(var_name, new_heap_key):
            # Get the existing RefValue from the symbol table
            old_ref_value = SymTable[var_name]

            # Create a new RefValue with the same locationType and the new heap key
            new_ref_value = RefValue(locationType=old_ref_value.locationType, address=new_heap_key)

            # Update the symbol table with the new RefValue
            SymTable[var_name] = new_ref_value
         */
        //It creates a new RefValue for the variable with a memory position obtained from getFreePosition
        // and a type derived from the inner type of the original variable's type (assuming it's a reference type)
        symbolTable.update(var_name, new RefValue(getFreePosition, ((RefType) getVariableFromSymTable.getType()).getInner()));

        return null;

    }
    @Override
    public IStmt deepcopy() {
        return new NewStmt(var_name, expression);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(var_name);
        Type typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }


    @Override
    public String toString() {
        return "new(" + this.var_name + ", " + this.expression.toString() + ")";
    }
}
