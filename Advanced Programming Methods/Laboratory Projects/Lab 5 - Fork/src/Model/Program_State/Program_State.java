package Model.Program_State;

import Model.ADT.My_I_Dictionary;
import Model.ADT.My_I_Heap;
import Model.ADT.My_I_List;
import Model.ADT.My_I_Stack;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Exception.*;

import java.io.BufferedReader;

public class Program_State {
    private My_I_Stack<IStatement> exeStack;
    private My_I_Dictionary<String, IValue> symTable;
    private My_I_List<IValue> out;
    private IStatement originalProgram;
    private My_I_Dictionary<String, BufferedReader> fileTable;
    private My_I_Heap heap;
    //B.8
    private int id;
    //B.8
    private static int lastId = 0;

    public Program_State(My_I_Stack<IStatement> stack, My_I_Dictionary<String, IValue> symTbl, My_I_List<IValue> _out, IStatement program, My_I_Dictionary<String, BufferedReader> fileTbl, My_I_Heap heap){
        exeStack = stack;
        symTable = symTbl;
        out = _out;
        originalProgram = program.deepCopy();
        fileTable = fileTbl;
        this.heap = heap;
        exeStack.push(originalProgram);
        //B.8
        this.id = setId();
    }

    public Program_State(My_I_Stack<IStatement> stack, My_I_Dictionary<String, IValue> symTbl, My_I_List<IValue> _out, My_I_Dictionary<String, BufferedReader> fileTbl, My_I_Heap heap){
        exeStack = stack;
        symTable = symTbl;
        out = _out;
        fileTable = fileTbl;
        this.heap = heap;
        this.id = setId();
    }

    public synchronized int setId(){
        lastId++;
        return lastId;
    }

    public int getId(){
        return this.id;
    }

    public void setExeStack(My_I_Stack<IStatement> newStack) {
        this.exeStack = newStack;
    }

    public void setSymTable(My_I_Dictionary<String, IValue> newSymTable) {
        this.symTable = newSymTable;
    }

    public void setOut(My_I_List<IValue> newOut) {
        this.out = newOut;
    }

    public void setFileTable(My_I_Dictionary<String, BufferedReader> newFileTable) {fileTable = newFileTable;}

    public void setHeap(My_I_Heap newHeap) {heap = newHeap;}

    public My_I_Stack<IStatement> getExeStack(){
        return  exeStack;
    }

    public My_I_Dictionary<String, IValue> getSymTable(){
        return symTable;
    }

    public My_I_List<IValue> getOut(){
        return out;
    }

    public My_I_Dictionary<String, BufferedReader> getFileTable() {return fileTable;}

    public My_I_Heap getHeap() { return heap;}

    //B.6.
    public Boolean isNotCompleted(){
        return exeStack.isEmpty();
    }

    //B.7
    public Program_State oneStep() throws ADT_Exception, Program_State_Exception, Statement_Execution_Exception, Division_By_Zero_Exception, Expression_Evaluation_Exception {
        if(exeStack.isEmpty())
            throw new Program_State_Exception("ERROR: Program state stack is empty!");
        IStatement currentStatement = exeStack.pop();
        return currentStatement.execute(this);
    }

    public String exeStackToString() {
        StringBuilder exeStackString = new StringBuilder();
        for (IStatement elem : exeStack) {
            exeStackString.append("[ ").append(elem.toString()).append(" ]\n");
        }
        return exeStackString.toString();
    }

    public String symTableToString() throws ADT_Exception {
        StringBuilder symTableString = new StringBuilder();
        for (String key: symTable.keySet()) {
            symTableString.append(String.format("%s -> %s\n", key, symTable.lookUp(key).toString()));
        }
        return symTableString.toString();
    }

    public String outToString() {
        StringBuilder outString = new StringBuilder();
        for (IValue elem: out.getList()) {
            outString.append(String.format("%s\n", elem.toString()));
        }
        return outString.toString();
    }

    public String fileTableToString() {
        StringBuilder fileTableStringBuilder = new StringBuilder();
        for (String key: fileTable.keySet()) {
            fileTableStringBuilder.append(String.format("%s\n", key));
        }
        return fileTableStringBuilder.toString();
    }

    public String heapToString() throws ADT_Exception {
        StringBuilder heapStringBuilder = new StringBuilder();
        for (int key: heap.getContent().keySet()) {
            heapStringBuilder.append(String.format("%d -> %s\n", key, heap.get(key)));
        }
        return heapStringBuilder.toString();
    }

    @Override
    public String toString() {
        try {
            //B.8
            return "Id: " + id + "\nExecution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "File table:\n" + fileTableToString() + "Heap table:\n" + heapToString() + "Output list: \n" + outToString();
        } catch (ADT_Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String programStateToString() throws ADT_Exception {
        return "Id: " + id + "\nExecution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "File table:\n" + fileTableToString() + "Heap table:\n" + heapToString() + "Output list: \n" + outToString();
    }



}
