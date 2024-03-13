package Model.ADT;

import Model.My_Exception;

import java.util.Collection;
import java.util.HashMap;

public interface I_Dictionary<T, V> {
    V lookup(T key);
    boolean key_isDefined(T key);
    void update(T key, V value) throws My_Exception;
    void add(T key, V value);
    HashMap<T, V> get_content();
    Collection<V> get_values();
}
