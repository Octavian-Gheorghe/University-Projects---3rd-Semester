module GUI {
    requires javafx.fxml;
    requires javafx.controls;

    //opens GUI.programController;
    //opens GUI.programsList;
    exports controller;
    exports repository;
    exports model;
    exports model.ADT;
    exports GUI;
    exports GUI.programsList;
    exports GUI.programController;
    opens GUI.programsList to javafx.fxml;
    opens GUI.programController to javafx.fxml;

}