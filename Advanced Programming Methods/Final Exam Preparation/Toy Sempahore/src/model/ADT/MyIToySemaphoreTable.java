package model.ADT;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;


public interface MyIToySemaphoreTable {
    void put(int key, Tuple value);
    Tuple get(int key);
    boolean containsKey(int key);
    int getFreeAddress();
    void setFreeAddress(int freeAddress);
    void update(int key, Tuple value);
    HashMap<Integer, Tuple> getSemaphoreTable();
    void setSemaphoreTable(HashMap<Integer, Tuple> newSemaphoreTable);
}