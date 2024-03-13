package Model.ADT;

import Exception.ADT_Exception;

import java.util.ArrayList;
import java.util.List;

public class My_ADT_List<T> implements My_I_List<T> {
    List<T> list;

    public My_ADT_List(){
        list = new ArrayList<>();
    }

    @Override
    public void add(T elem) {
        list.add(elem);
    }

    @Override
    public T pop() throws ADT_Exception {
        if (list.isEmpty())
            throw new ADT_Exception("ERROR: EMPTY LIST!");
        return this.list.remove(0);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public List<T> getList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
