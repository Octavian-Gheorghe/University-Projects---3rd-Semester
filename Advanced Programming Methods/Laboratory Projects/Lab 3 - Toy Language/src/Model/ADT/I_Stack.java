package Model.ADT;

import java.util.List;
public interface I_Stack<T> {
    T pop();
    void push(T valueToPush);
    boolean isEmpty();
    String toString();
    List<T> getAll();
    List<T> get_reverese();

}
