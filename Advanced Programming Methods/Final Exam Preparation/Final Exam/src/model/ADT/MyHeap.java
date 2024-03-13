package model.ADT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyHeap<K, V> implements MyIHeap<K,V>{

    private Map<K, V> heap;

    private int freePosition = 1;


    public MyHeap(){
        this.heap = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        this.heap.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.heap.get(key);
    }

    @Override
    public boolean containsKey(int key) {
        return this.heap.containsKey(key);
    }

    @Override
    public void setContent(Map<K, V> newHeap) {
        this.heap.clear();
        this.heap.putAll(newHeap);
    }

    @Override
    public void setFirstFreeAdress() {
        this.freePosition = freePosition + 1;
    }

    @Override
    public int getFirstFreeAdress() {
        int copyFreePosition = this.freePosition;
        setFirstFreeAdress();
        return copyFreePosition;
    }

    @Override
    public void update(K key, V value) {
        this.heap.replace(key, value);
    }

    @Override
    public Map getContent() {
        return this.heap;
    }

    @Override
    public Collection<V> getAllValues() {
        return this.heap.values();
    }

    @Override
    public String toString() {
        String heapValues = "";
        for(K key: this.heap.keySet())
            heapValues = heapValues.concat("[" + key + "->" + this.heap.get(key).toString() + "] ");
        return heapValues;
    }
}