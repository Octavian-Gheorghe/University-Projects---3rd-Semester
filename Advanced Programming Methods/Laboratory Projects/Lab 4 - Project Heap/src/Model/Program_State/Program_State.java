package Model.Program_State;

import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Heap;
import Model.ADT.My_I_List;
import Model.ADT.My_I_Stack;
import Model.Statement.I_Statement;
import Model.Value.I_Value;
import Exception.ADT_Exception;

import java.io.BufferedReader;

public class Program_State {
    private My_I_Stack<I_Statement> exeStack;
    private My_I_Dict<String, I_Value> symTable;
    private My_I_List<I_Value> out;
    private I_Statement originalProgram;
    private My_I_Dict<String, BufferedReader> fileTable;

    private My_I_Heap heap;

    public Program_State(My_I_Stack<I_Statement> stack, My_I_Dict<String, I_Value> symTbl, My_I_List<I_Value> _out, I_Statement program, My_I_Dict<String, BufferedReader> fileTbl, My_I_Heap heap){
        exeStack = stack;
        symTable = symTbl;
        out = _out;
        originalProgram = program.deepCopy();
        fileTable = fileTbl;
        this.heap = heap;
        exeStack.push(originalProgram);
    }

    public void setExeStack(My_I_Stack<I_Statement> newStack) {
        this.exeStack = newStack;
    }

    public void setSymTable(My_I_Dict<String, I_Value> newSymTable) {
        this.symTable = newSymTable;
    }

    public void setOut(My_I_List<I_Value> newOut) {
        this.out = newOut;
    }

    public void setFileTable(My_I_Dict<String, BufferedReader> newFileTable) {fileTable = newFileTable;}

    public void setHeap(My_I_Heap newHeap) {heap = newHeap;}

    public My_I_Stack<I_Statement> getExeStack(){
        return  exeStack;
    }

    public My_I_Dict<String, I_Value> getSymTable(){
        return symTable;
    }

    public My_I_List<I_Value> getOut(){
        return out;
    }

    public My_I_Dict<String, BufferedReader> getFileTable() {return fileTable;}

    public My_I_Heap getHeap() { return heap;}

    public String exeStackToString() {
        StringBuilder exeStackString = new StringBuilder();
        for (I_Statement elem : exeStack) {
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
        for (I_Value elem: out.getList()) {
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
            return "Execution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "File table:\n" + fileTableToString() + "Heap table:\n" + heapToString() + "Output list: \n" + outToString();
        } catch (ADT_Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String programStateToString() throws ADT_Exception {
        return "Execution stack: \n" + exeStackToString() + "Symbol table: \n" + symTableToString() + "File table:\n" + fileTableToString() + "Heap table:\n" + heapToString() + "Output list: \n" + outToString();
    }



}
