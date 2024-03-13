package model;

import model.ADT.*;
import model.stmt.IStmt;
import model.value.Value;
import java.io.BufferedReader;
import java.io.PrintWriter;


public class PrgState {
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIFileTable<String, BufferedReader> fileTable;

    private MyIHeap<Integer, Value> heap;

    private IStmt originalProgram; //optional field, but good to have

    private int id;


    private static int currentID = 0;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot,MyIFileTable<String,BufferedReader> fileTable, MyIHeap<Integer, Value> heap,  IStmt prg){
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = prg.deepcopy();
        this.id = getNewId();
        stk.push(prg);
    }

    // static synchronized method to manage the id
    // each instance of PrgState (each thread) gets a unique ID generated through this method.
    private int getNewId()
    {
        currentID++;
        return currentID;
    }
    @Override
    public String toString() {
        return "PrgState{" +
                "id=" + id +
                ", exeStack=" + exeStack +
                ", symTable=" + symTable +
                ", out=" + out +
                ", heap=" + heap +
                '}';
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }
    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }
    public MyIList<Value> getOut() {
        return out;
    }
    public MyIFileTable<String, BufferedReader> getFileTable() {
        return fileTable;
    }


    public void setFileTable(MyIFileTable<String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }
    public void setExeStack(MyIStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(MyIDictionary<String, Value> symTable) {
        this.symTable = symTable;
    }
    public void setOut(MyIList<Value> out) {
        this.out = out;
    }

    public MyIHeap <Integer, Value> getHeap(){ return heap;};

    public void setHeap(MyIHeap<Integer, Value> newHeap ) {this.heap = newHeap;}
    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    // the new version of oneStep has no argument since the argument of the old version is the
    // receiver of the new version
    public PrgState oneStep() throws MyException {
        if(exeStack.isEmpty()) throw new MyException("Program state stack is empty");
        IStmt crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }
    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public int getId() {
        return id;
    }

}