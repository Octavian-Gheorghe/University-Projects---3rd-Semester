package GUI;

import GUI.programController.programController;
import GUI.programsList.*;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.MyException;

import java.util.Objects;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        //get the url from the fxml file
        FXMLLoader programsListLoader = new FXMLLoader();
        programsListLoader.setLocation(Main.class.getResource("programsList/programsList.fxml"));
        Parent programsListRoot = programsListLoader.load();
        Scene programsListScene = new Scene(programsListRoot, 500, 500, Color.LIGHTGREEN);
        String css = getClass().getResource("button-style.css").toExternalForm(); //conv url to string
        programsListScene.getStylesheets().add(css);
        programsList programSelectionController = programsListLoader.getController();
        primaryStage.setScene(programsListScene);
        primaryStage.setTitle("Programs List");
        primaryStage.show();

        FXMLLoader programExecutorLoader = new FXMLLoader();
        programExecutorLoader.setLocation(Main.class.getResource("programController/programController.fxml"));
        Parent programExecutorRoot = programExecutorLoader.load(); //loads the fxml file as the root
        Scene programExecutorScene = new Scene(programExecutorRoot, 650, 600);
        programExecutorScene.getStylesheets().add(css);
        programController programExecutorController = programExecutorLoader.getController();
        // we create the link between the 2 controllers
        programSelectionController.setProgramExecutorController(programExecutorController);
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(programExecutorScene);
        secondaryStage.setTitle("Interpreter");
        secondaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}