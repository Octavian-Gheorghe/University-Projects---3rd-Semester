package Model.ADT;

import java.util.List;
import java.util.ArrayList;
public class ADT_List<V> implements I_List<V> {
    private List<V> list;
    public ADT_List(){
        list = new ArrayList<V>();
    }
    public ADT_List(List<V> list){
        this.list = list;
    }
    public List<V> get_list(){
        return list;
    }
    public void setList(List<V> list){
        this.list = list;
    }
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (V item : list) {
            result.append("\t").append(item).append("\n");
        }
        return result.toString();
    }
    @Override
    public void add(V valueToAdd){
        list.add(valueToAdd);
    }
    @Override
    public void clear(){
        list.clear();
    }
}
