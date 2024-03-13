package GUI.classgui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class classgui extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a label with the text "Hello, World!"
        Label helloLabel = new Label("Hello, World!");

        // Create a scene with the label
        Scene scene = new Scene(helloLabel, 300, 200);

        // Set the scene to the primary stage
        primaryStage.setScene(scene);

        // Set the title of the window
        primaryStage.setTitle("HelloWorldApp");

        // Show the window
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
