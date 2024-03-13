package View;

import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;
import Service.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class RunExampleCommand extends Command{
    private Service serv;

    public RunExampleCommand(String k, String d, Service s) {
        super(k, d);
        serv = s;
    }

    @Override
    public void execute() {
        try{
            System.out.print("Print steps?(y/n) ");
            Scanner scn = new Scanner(System.in);
            String cmd = scn.next();
            serv.changePrintStep(Objects.equals(cmd, "y"));
            serv.allSteps();
        }
        catch (Expression_Evaluation_Exception | ADT_Exception | Statement_Execution_Exception | Division_By_Zero_Exception | IOException e){
            System.out.println(e.getMessage());
        }
    }
}
