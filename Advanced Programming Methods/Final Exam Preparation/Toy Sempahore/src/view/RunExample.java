package view;
import controller.Controller;
import model.MyException;

public class RunExample extends Command {
    private Controller controller;
    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }
    @Override
    public void execute() {
        try {
            controller.allStep();
        }
        catch (InterruptedException | MyException e) {
            throw new RuntimeException(e);
        }
    }
}

/*
public class RunExample extends Command{
    private Controller ctr;
    public RunExample(String key, String desc, Controller ctr) {
        super(key, desc);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.allStep();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/