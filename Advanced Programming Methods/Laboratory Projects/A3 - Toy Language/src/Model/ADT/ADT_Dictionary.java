package Model.ADT;

import Model.My_Exception;

import java.util.HashMap;
import java.util.Collection;

public class ADT_Dictionary<T, V> implements I_Dictionary<T,V> {
    private HashMap<T, V> dictionary;
    public ADT_Dictionary()
    {
        dictionary = new HashMap<T, V>();
    }
    public ADT_Dictionary(HashMap<T,V> dictionary)
    {
        this.dictionary = dictionary;
    }

    public String toString()
    {
        StringBuilder result = new StringBuilder("MyDictionary{\n");
        for (HashMap.Entry<T, V> entry : dictionary.entrySet())
        {
            result.append("\t").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        result.append("}");
        return result.toString();
    }
    @Override
    public V lookup(T key) {
        return dictionary.get(key);
    }

    @Override
    public boolean key_isDefined(T key) {
        return this.dictionary.get(key) != null;
    }

    @Override
    public void update(T key, V value) throws My_Exception
    {
        if(dictionary.get(key) == null)
            throw new My_Exception("Key not found");
        else dictionary.put(key, value);
    }
    @Override
    public void add(T key, V value) {
    this.dictionary.putIfAbsent(key, value);
    }

    @Override
    public HashMap<T, V> get_content() {
        return this.dictionary;
    }

    @Override
    public Collection<V> get_values()
    {
        synchronized (this)
        {
            return dictionary.values();
        }
    }
}

