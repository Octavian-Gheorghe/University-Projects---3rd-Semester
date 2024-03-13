package Model.ADT;

import Exception.ADT_Exception;

import java.util.Deque;

public interface My_I_Stack<T> extends Iterable<T>{
    T pop() throws ADT_Exception;
    void push(T v);
    T peek() throws ADT_Exception;
    boolean isEmpty();
    Deque<T> getStack();
}
