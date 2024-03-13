package View;
import Controller.Controller;
import Model.My_Exception;

public class Run_Example_Command extends Command {
    private Controller controller;
    public Run_Example_Command(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }
    @Override
    public void execute() {
        try
        {
            controller.allStep();
            System.out.println("Output:");
            System.out.println(controller.get_out(controller.get_repository().get_current_program()));
        } catch (My_Exception error) {
            System.out.println(error.getMessage());
        }
    }
}
