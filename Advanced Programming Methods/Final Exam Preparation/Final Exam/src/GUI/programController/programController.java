package GUI.programController;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;
import java.util.stream.Collectors;

import controller.Controller;
import javafx.scene.input.MouseEvent;
import model.ADT.*;
import model.MyException;
import model.PrgState;
import model.stmt.IStmt;
import model.value.Value;

class Pair<T1, T2>{
    T1 first;
    T2 second;
    public Pair(T1 first, T2 second){
        this.first = first;
        this.second = second;
    }
}

/*
    -> TableView <each row data type, in this case each row consists of an address and a value, so the generics will be <Int, Val>>
    -> TableViewColumn <<how is the whole row>, <cell from this column>> = <<Integer, Value>, Integer> thats why i created Pair class
 */

public class programController {
    private Controller controller;
    public int nrOfPrograms;

    @FXML
    private TextField numberOfProgramStatesTextField;

    @FXML
    private TableView<Pair<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, Value>, Integer> addressColumn;

    @FXML
    private TableColumn<Pair<Integer, Value>, String> valueColumn;

    @FXML
    private ListView<String> outListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<Integer> programStateIdentifiersListView;

    @FXML
    private TableView<Pair<String, Value>> symbolTableView;

    @FXML
    private TableColumn<Pair<String, Value>, String> variableNameColumn;

    @FXML
    private TableColumn<Pair<String, Value>, String> variableValueColumn;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private Button runOneStepButton;

    public void setController(Controller controller) {
        this.controller = controller;
        populate();
    }

    @FXML
    private void initialize() {
        programStateIdentifiersListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        valueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        variableNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        variableValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
    }

    private PrgState getCurrentProgramState() {
        if (controller.getProgramStates().size() == 0)
            return null;
        else {
            int currentId = programStateIdentifiersListView.getSelectionModel().getSelectedIndex();
            if (currentId == -1)
                return controller.getProgramStates().get(0);
            else
                return controller.getProgramStates().get(currentId);
        }
    }



    private void populate() {
        populateNumberOfProgramStatesTextField();
        populateHeapTableView();
        populateOutputListView();
        populateFileTableListView();
        populateProgramStateIdentifiersListView();
        populateSymbolTableView();
        populateExeStackListView();
    }

    //(a) the number of PrgStates as a TextField
    private void populateNumberOfProgramStatesTextField() {
        List<PrgState> programStates = controller.getProgramStates();
        numberOfProgramStatesTextField.setText(String.valueOf(nrOfPrograms));
    }

    /*private void populateToySemaphoreTableView() {
        PrgState currentPrgState = getCurrentProgramState();
        assert currentPrgState != null;
        MyIToySemaphoreTable toySemaphore = currentPrgState.getToySemaphoreTable();
        ArrayList<Tuple> semaphoreEntries = new ArrayList<>();
        for (Map.Entry<Integer, Tuple> entry : toySemaphore.getSemaphoreTable().entrySet()) {
            Integer key = entry.getKey();
            Tuple value = entry.getValue();
            semaphoreEntries.add(value);
        }
        toySemaphoreTableView.setItems(FXCollections.observableList(semaphoreEntries));
    }*/


        //(b) the HeapTable as a TableView with two columns: address and Value
    private void populateHeapTableView(){
        PrgState currentPrgState = getCurrentProgramState();
        assert currentPrgState != null;
        MyIHeap<Integer, Value> heap = currentPrgState.getHeap();
        ArrayList<Pair<Integer, Value>> heapEntries = new ArrayList<>();
        for(Map.Entry<Integer, Value> entry: heap.getContent().entrySet())
            heapEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        heapTableView.setItems(FXCollections.observableList(heapEntries));
    }

    // (c) the Out as a ListView
    private void populateOutputListView() {
        PrgState programState = getCurrentProgramState();
        List<String> output = new ArrayList<>();
        List<Value> outputList = Objects.requireNonNull(programState).getOut().getList();
        int index;
        for (index = 0; index < outputList.size(); index++){
            output.add(outputList.get(index).toString());
        }
        outListView.setItems(FXCollections.observableArrayList(output));
}

    // (d) the FileTable as a ListView
    private void populateFileTableListView(){
        PrgState currentPrgState = getCurrentProgramState();
        assert currentPrgState != null;
        List<String> files = new ArrayList<>(currentPrgState.getFileTable().getContent().keySet()); // name of the file
        fileTableListView.setItems(FXCollections.observableArrayList(files));
    }

    // (e) the list of PrgState identifiers as a ListView
    private void populateProgramStateIdentifiersListView() {
        List<PrgState> programStates = controller.getProgramStates();
        List<Integer> idList = programStates.stream().map(PrgState::getId).collect(Collectors.toList());
        programStateIdentifiersListView.setItems(FXCollections.observableList(idList));
        populateNumberOfProgramStatesTextField();
    }


    /*  a Table View with two columns: variable name and Value, which displays the
        SymTable of the PrgState whose ID has been selected from the list described at
        (e)
    */

    private void populateSymbolTableView(){
        PrgState currentPrgState = getCurrentProgramState();
        assert currentPrgState != null;
        MyIDictionary<String, Value> symbolTable = currentPrgState.getSymTable();
        ArrayList<Pair<String, Value>> symbolTableEntries = new ArrayList<>();
        for(Map.Entry<String, Value> entry: symbolTable.getContent().entrySet())
            symbolTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        symbolTableView.setItems(FXCollections.observableArrayList(symbolTableEntries));
    }

    /*(g) a List View which displays the ExeStack of the PrgState whose ID has been
        selected from the list described at (e). First element of the ListView is a string
        representation of the top of ExeStack, the second element of the ListView
        represents the second element from the ExeStack and so on*/
    private void populateExeStackListView(){
    PrgState currentPrgState = getCurrentProgramState();
    assert currentPrgState != null;
    MyIStack<IStmt> exeStack = currentPrgState.getExeStack();
    ArrayList<String> exeStackString = new ArrayList<String>();
    for(IStmt stmt : exeStack.getReverse()){
        exeStackString.add(stmt.toString());
    }
    exeStackListView.setItems(FXCollections.observableArrayList(exeStackString));
    }

    @FXML
    public void runOneStepButton(MouseEvent event) {
        if (controller != null)
            try {
                List<PrgState> programStates = Objects.requireNonNull(controller.getProgramStates());
                if (!programStates.isEmpty()) {
                    controller.oneStep();
                    populate();
                    programStates = controller.removeCompletedPrg(controller.getProgramStates());
                    controller.setProgramStates(programStates);
                    populateProgramStateIdentifiersListView();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error occurred");
                    alert.setContentText("The program is already executed");
                    alert.showAndWait();
                }
            } catch (MyException | InterruptedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error occurred");
                alert.setContentText("Execution error!");
                alert.showAndWait();

            }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error occurred");
            alert.setContentText("No program selected!");
            alert.showAndWait();
        }
    }
    @FXML
    public void changeProgramState(MouseEvent event) {
        populateExeStackListView();
        populateSymbolTableView();
    }
}