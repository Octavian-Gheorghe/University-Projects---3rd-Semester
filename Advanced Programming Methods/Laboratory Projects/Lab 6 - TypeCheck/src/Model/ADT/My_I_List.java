package Model.ADT;

import Exception.ADT_Exception;

import java.util.List;

public interface My_I_List<T> {
    void add(T elem);
    T pop() throws ADT_Exception;
    boolean isEmpty();
    List<T> getList();
}