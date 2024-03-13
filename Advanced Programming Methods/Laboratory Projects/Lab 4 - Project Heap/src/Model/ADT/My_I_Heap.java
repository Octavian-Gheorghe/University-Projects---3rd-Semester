package Model.ADT;

import Model.Value.I_Value;
import Exception.ADT_Exception;

import java.util.HashMap;
import java.util.Map;

public interface My_I_Heap {
    Integer getFreeValue();
    Map<Integer, I_Value> getContent();
    void setContent(HashMap<Integer, I_Value> newMap);
    Integer add(I_Value value);
    void update(Integer position, I_Value value) throws ADT_Exception;
    I_Value get(Integer position) throws ADT_Exception;
}
