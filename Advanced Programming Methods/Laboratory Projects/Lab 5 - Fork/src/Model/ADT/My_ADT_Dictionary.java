package Model.ADT;

import Exception.ADT_Exception;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class My_ADT_Dictionary<T1, T2> implements My_I_Dictionary<T1, T2> {
    final HashMap<T1, T2> dict;

    public My_ADT_Dictionary(){
        dict = new HashMap<>();
    }

    @Override
    public void put(T1 key, T2 value) {
        dict.put(key, value);
    }

    @Override
    public void update(T1 key, T2 value) throws ADT_Exception {
        if (!contains(key))
            throw new ADT_Exception("ERROR: UNDEFINED KEY " + key);
        dict.put(key, value);
    }

    @Override
    public void remove(T1 key) throws ADT_Exception {
        if (!contains(key))
            throw new ADT_Exception("ERROR: UNDEFINED KEY " + key);
        dict.remove(key);
    }

    @Override
    public boolean contains(T1 key) {
        return dict.containsKey(key);
    }

    @Override
    public T2 lookUp(T1 key) throws ADT_Exception {
        if (!contains(key))
            throw new ADT_Exception("ERROR: UNDEFINED KEY " + key);
        return dict.get(key);
    }

    @Override
    public Collection<T2> values() {
        return dict.values();
    }

    @Override
    public Set<T1> keySet() {
        return dict.keySet();
    }

    @Override
    public Map<T1, T2> getContent() {
        synchronized (dict) {
            return dict;
        }
    }
}