package model.ADT;

import java.util.HashMap;
import java.util.Map;

public class MyToySemaphoreTable implements MyIToySemaphoreTable{
    private HashMap<Integer, Tuple> toySemaphoreTable;
    private int freeAddress = 0;

    public MyToySemaphoreTable(){
        toySemaphoreTable = new HashMap<>();
    }
    @Override
    public void put(int key, Tuple value) {
        toySemaphoreTable.put(key, value);
    }

    @Override
    public Tuple get(int key) {
        return toySemaphoreTable.get(key);
    }

    @Override
    public boolean containsKey(int key) {
        return toySemaphoreTable.containsKey(key);
    }

    @Override
    public int getFreeAddress() {
        freeAddress++;
        return freeAddress;
    }

    @Override
    public void setFreeAddress(int freeAddress) {

    }


    @Override
    public void update(int key, Tuple value) {
        this.toySemaphoreTable.put(key, value);
    }

    @Override
    public HashMap<Integer, Tuple> getSemaphoreTable() {
        return this.toySemaphoreTable;
    }

    @Override
    public void setSemaphoreTable(HashMap<Integer, Tuple> newSemaphoreTable) {
        this.toySemaphoreTable = newSemaphoreTable;
    }
}
