package model.stmt;

import javafx.util.Pair;
import model.ADT.MyIToySemaphoreTable;
import model.ADT.MyIDictionary;
import model.ADT.Tuple;
import model.MyException;
import model.PrgState;
import model.type.IntType;
import model.type.Type;
import model.value.IntValue;
import model.value.Value;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AcquireStmt implements IStmt{
    private String var;
    private static Lock lock = new ReentrantLock();

    public AcquireStmt(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "Acquire{" +
                "var='" + var + '\'' +
                '}';
    }
    /*- pop the statement
        - foundIndex=lookup(SymTable,var). If var is not in SymTable or has not
        the type int then print an error message and terminate the execution.
        - if foundIndex is not an index in the SemaphoreTable then
        print an error message and terminate the execution

        else
        - retrieve the entry for that foundIndex, as
        SemaphoreTable[foundIndex]== (N1,List1,N2)
        - compute the length of that list List1 as NL=length(List1)
        - if ((N1-N2)>NL) then
        if(the identifier of the current PrgState is in List1) then
        - do nothing
        else
        - add the id of the current PrgState to List1
        else
        - push back acquire(var) on the ExeStack*/

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIToySemaphoreTable semaphoreTable = state.getToySemaphoreTable();
        if (symTable.isDefined(var)) {
            if (symTable.lookup(var).getType().equals(new IntType())){
                IntValue fi = (IntValue) symTable.lookup(var);
                int foundIndex = fi.getVal();
                if (semaphoreTable.getSemaphoreTable().containsKey(foundIndex)) {
                    Tuple foundSemaphore = semaphoreTable.get(foundIndex);
                    //int NL = foundSemaphore.getValue().size();
                    int N1 = foundSemaphore.getFirst();
                    int N2 = foundSemaphore.getThird();
                    List<Integer> list = foundSemaphore.getSecond();
                    int NL = list.size();
                    if (N1 - N2 > NL) {
                        if (!list.contains(state.getId())) {
                            foundSemaphore.getSecond().add(state.getId());
                            semaphoreTable.update(foundIndex, foundSemaphore);
                        }
                    } else {
                        state.getExeStack().push(this);
                    }
                } else {
                    throw new MyException("Index not a key in the semaphore table!");
                }
            } else {
                throw new MyException("Index must be of int type!");
            }
        } else {
            throw new MyException("Index not in symbol table!");
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new AcquireStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if (typeEnv.lookup(var).equals(new IntType())) {
            return typeEnv;
        }
        else {
            throw new MyException("Variable must be of type int!");
        }
    }
}