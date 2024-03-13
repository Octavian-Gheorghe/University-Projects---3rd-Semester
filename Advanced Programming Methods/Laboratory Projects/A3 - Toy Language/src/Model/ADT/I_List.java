package Model.ADT;

import java.util.List;
public interface I_List<V>{
    void add(V valueToAdd);
    List<V> get_list();
    void clear();
}
