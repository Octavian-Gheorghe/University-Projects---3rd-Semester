package Model.ADT;

import Exception.ADT_Exception;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface My_I_Dict<T1,T2> {
    void put(T1 key, T2 value);
    void update(T1 key, T2 value) throws ADT_Exception;
    void remove(T1 key) throws ADT_Exception;
    boolean contains(T1 key);
    T2 lookUp(T1 key) throws ADT_Exception;
    Collection<T2> values();
    Set<T1> keySet();
    Map<T1, T2> getContent();

    My_I_Dict<T1,T2> deepCopy() throws ADT_Exception;
}
