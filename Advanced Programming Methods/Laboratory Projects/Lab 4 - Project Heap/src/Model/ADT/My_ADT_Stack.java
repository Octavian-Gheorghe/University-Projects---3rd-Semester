package Model.ADT;

import Exception.ADT_Exception;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class My_ADT_Stack<T> implements My_I_Stack<T> {
    private final Deque<T> deque;

    public My_ADT_Stack(){
        deque = new LinkedList<>();
    }

    @Override
    public T pop() throws ADT_Exception {
        if(deque.isEmpty())
            throw new ADT_Exception("ERROR: EMPTY STACK!");
        return deque.pop();
    }

    @Override
    public void push(T v) {
        deque.push(v);
    }

    @Override
    public T peek() throws ADT_Exception {
        if(deque.isEmpty())
            throw new ADT_Exception("ERROR: EMPTY STACK!");
        return deque.peek();
    }

    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    @Override
    public Deque<T> getStack() {
        return deque;
    }

    @Override
    public Iterator<T> iterator() {
        return deque.iterator();
    }
}
