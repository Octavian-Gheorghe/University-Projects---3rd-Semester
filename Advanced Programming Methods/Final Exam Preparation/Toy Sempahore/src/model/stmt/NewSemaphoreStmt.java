package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.ADT.MyIToySemaphoreTable;
import model.ADT.Tuple;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.exp.ValueExp;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.ArrayList;

public class NewSemaphoreStmt implements IStmt{

    /*
    newSemaphore(var,exp1,exp2)
    which creates a new semaphore into the SemaphoreTable. The statement
    execution rule is as follows:
    Stack1={newSemaphore(var, exp1,exp2)| Stmt2|...}
    SymTable1
    Out1
    Heap1
    FileTable1
    SemaphoreTable1
    ==>
    Stack2={Stmt2|...}
    Out2=Out1
    Heap2=Heap1
    FileTable2=FileTable1

    Note that you must use the lock mechanisms of the host language
    Java over the SemaphoreTable in order to add a new semaphore to the table.
     */

    private String var;
    private Exp exp1;
    private Exp exp2;

    public NewSemaphoreStmt(String var, Exp exp1, Exp exp2){
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /*
    - evaluate the expression exp1 and exp2 using SymTable1 and Heap1
    and let be number1 and number2 the results of this evaluation
    SemaphoreTable2 = SemaphoreTable1 synchronizedUnion
    {newfreelocation ->(number1,empty list,number2)}
    if var exists in SymTable1 and has the type int then
        SymTable2 = update(SymTable1,var, newfreelocation)

    else throws an error
     */
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap heap = state.getHeap();
        MyIToySemaphoreTable toySemaphoreTable = state.getToySemaphoreTable();
        IntValue number1 =(IntValue) this.exp1.eval(symTable, heap);
        IntValue number2 =(IntValue) this.exp2.eval(symTable, heap);
        int number1Int = number1.getVal();
        int number2Int = number2.getVal();
        int freeAddress = toySemaphoreTable.getFreeAddress();
        toySemaphoreTable.put(freeAddress, new Tuple(number1Int, new ArrayList<>(), number2Int));
        if(symTable.isDefined(var) && symTable.lookup(var).getType().equals(new IntType()))
            symTable.update(var, new IntValue(freeAddress));
        else
            throw new MyException("The variable is not defined!");

        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new NewSemaphoreStmt(var, exp1, exp2);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            if (exp1.typecheck(typeEnv).equals(new IntType()) && exp2.typecheck(typeEnv).equals(new IntType())) {
                return typeEnv;
            }
            else {
                throw new MyException("Expression must be of type int!");
            }
        }
        else {
            throw new MyException("Variable must be of type int!");
        }
    }

    @Override
    public String toString() {
        return "NewSemaphore(" + var + ")";
    }
}
