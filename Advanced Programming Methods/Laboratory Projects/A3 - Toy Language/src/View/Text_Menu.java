package View;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class Text_Menu {
    private Map<String,Command> commands;
    public Text_Menu() {
        commands = new HashMap<>();
    }
    public void add_command(Command command) {
        commands.put(command.get_key(), command);
    }
    private void printMenu() {
        for (Command command : commands.values())
        {
            String line = String.format("%4s : %s", command.get_key(), command.get_description());
            System.out.println(line);
        }
    }
    public void show(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.printf("Input the option: ");
            String key = scanner.nextLine();
            Command command = commands.get(key);
            if (command == null) {
                System.out.println("Invalid Option");
                continue;
            }
            command.execute();
        }
    }
}

