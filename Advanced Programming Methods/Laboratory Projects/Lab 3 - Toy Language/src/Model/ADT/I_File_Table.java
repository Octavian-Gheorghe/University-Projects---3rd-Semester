package Model.ADT;

import java.util.HashMap;

public interface I_File_Table<K,V> {
    V lookup(K key);
    boolean key_isDefined(K key);
    void update(K key, V value);
    void add(K key, V value);
    void remove(K key);
    HashMap<K,V> get_content();
}
