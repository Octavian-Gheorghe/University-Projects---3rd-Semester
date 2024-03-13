package Model.ADT;

import Model.Value.IValue;
import Exception.ADT_Exception;

import java.util.HashMap;
import java.util.Map;

public interface My_I_Heap {
    Integer getFreeValue();
    Map<Integer, IValue> getContent();
    void setContent(HashMap<Integer, IValue> newMap);
    Integer add(IValue value);
    void update(Integer position, IValue value) throws ADT_Exception;
    IValue get(Integer position) throws ADT_Exception;
}
