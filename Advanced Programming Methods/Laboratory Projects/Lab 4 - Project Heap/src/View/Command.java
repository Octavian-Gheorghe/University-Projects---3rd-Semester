package View;

import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

import java.io.IOException;

public abstract class Command {
    private String key, description;

    public Command(String k, String d) {
        key = k;
        description = d;
    }

    public abstract void execute() throws Statement_Execution_Exception, Division_By_Zero_Exception, ADT_Exception, IOException, Expression_Evaluation_Exception;

    public String getKey() {return key;}

    public String getDescription() {return description;}

}
