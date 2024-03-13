package Model.Expression;

import Model.ADT.I_Dictionary;
import Model.My_Exception;
import Model.Value.I_Value;

public class Variable_Expression implements I_Expression {
    private String id;
    public Variable_Expression(String i) {id=i;}
    public String get_id() {return id;}
    public String set_id(String i) {return id=i;}
    public String toString() {
        return "VarExp{" +
                "id='" + id + '\'' +
            '}';
    }

    @Override
    public I_Value evaluate(I_Dictionary<String, I_Value> tbl) throws My_Exception {
        return tbl.lookup(id);
    }


}
