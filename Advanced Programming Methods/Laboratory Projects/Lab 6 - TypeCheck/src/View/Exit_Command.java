package View;

public class Exit_Command extends Command{
    public Exit_Command(int k, String d) {
        super(k, d);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
