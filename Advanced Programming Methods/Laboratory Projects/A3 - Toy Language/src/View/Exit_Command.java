package View;

public class Exit_Command extends Command{
    public Exit_Command(String key, String description) {
        super(key, description);
    }
    @Override
    public void execute() {
        System.exit(0);
    }
}
