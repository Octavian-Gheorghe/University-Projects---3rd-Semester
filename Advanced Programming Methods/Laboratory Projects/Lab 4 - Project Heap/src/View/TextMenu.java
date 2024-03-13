package View;

import Model.ADT.My_ADT_Dict;
import Model.ADT.My_I_Dict;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

import java.io.IOException;
import java.util.Scanner;

public class TextMenu {
    private My_I_Dict<String, Command> commands;

    public TextMenu(){
        commands = new My_ADT_Dict<>();
    }

    public void addCommand(Command cmd){
        commands.put(cmd.getKey(), cmd);
    }

    private void printMenu(){
        for (Command cmd: commands.values()){
            String line = String.format("%4s: %s", cmd.getKey(), cmd.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            try{
                Command cmd = commands.lookUp(key);
                cmd.execute();
            }
            catch (ADT_Exception e){
                System.out.println("Invalid option!");
            } catch (Statement_Execution_Exception | Expression_Evaluation_Exception | IOException | Division_By_Zero_Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
