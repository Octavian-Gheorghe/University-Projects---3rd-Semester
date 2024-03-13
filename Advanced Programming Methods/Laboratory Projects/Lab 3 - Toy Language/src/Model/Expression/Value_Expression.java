package Model.Expression;

import Model.ADT.I_Dictionary;
import Model.My_Exception;
import Model.Value.I_Value;

public class Value_Expression implements I_Expression {
    private I_Value e;
    public Value_Expression(I_Value e){
        this.e = e;
    }
    public I_Value get_e(){
        return this.e;
    }
    public void set_e(I_Value e){
        this.e = e;
    }
    @Override
    public String toString()
    {
        return "ValueExp{"+
                "e="+e+
                '}';
    }

    @Override
    public I_Value evaluate(I_Dictionary<String, I_Value> tbl) throws My_Exception {
        return e;
    }

}
