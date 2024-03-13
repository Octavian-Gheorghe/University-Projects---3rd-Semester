package GUI.programsList;

import GUI.programController.programController;
import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import model.ADT.*;
import model.MyException;
import model.PrgState;
import model.exp.*;
import model.stmt.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import model.value.Value;
import repository.Repository;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class programsList {

    private programController programExecutorController;
    private int nrOfPrograms;
    public void setProgramExecutorController(programController programExecutorController)
    {
        this.programExecutorController = programExecutorController;
    }


    @FXML
    private ListView<IStmt> programsListView;

    @FXML
    private Button displayButton;


    @FXML
    public void initialize(){
        programsListView.setItems(getAllStatements());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void displayProgram(MouseEvent actionEvent) {
        // when clicking a prg
        IStmt selectedStatement = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            //System.out.println(selectedStatement.toString());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No program selected!");
            alert.setContentText("Choose a program from the list.");
            alert.showAndWait();
        } else {
            int id = programsListView.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typecheck(new MyDictionary<String, Type>());
                PrgState prg = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, Value>(), new MyList<Value>(), new MyFileTable<String, BufferedReader>(), new MyHeap<Integer, Value>(), new MyToySemaphoreTable(), selectedStatement);
                ArrayList<PrgState> list = new ArrayList<>();
                list.add(prg);
                Repository repo = new Repository(list, "log" + (id) + ".txt");
                Controller controller = new Controller(repo);
                programExecutorController.nrOfPrograms = this.nrOfPrograms;
                programExecutorController.setController(controller);

            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error encountered!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }

    }

    @FXML
    private ObservableList<IStmt> getAllStatements(){
        List<IStmt> allStatements = new ArrayList<>();
        //List<String> allStatementsString = new ArrayList<String>();
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        allStatements.add(ex1);
       // allStatementsString.add("int v; v=2;Print(v)");

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp("+", new ValueExp(new IntValue(2)),
                                new ArithExp("*", new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp("+", new VarExp("a"),
                                        new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
        allStatements.add(ex2);

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
        allStatements.add(ex3);

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenReadFileStatement(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStatement(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")), new CloseReadFileStatement(new VarExp("varf"))))))))));
        allStatements.add(ex4);

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExpressions(">", new VarExp("v"), new ValueExp(new IntValue(0))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp("-", new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));
        allStatements.add(ex5);

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new PrintStmt(new readHeapExpression(new readHeapExpression(new VarExp("a")))))))));
        allStatements.add(ex6);

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteStmt("a", new ValueExp(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new readHeapExpression(new VarExp("a")))
                                                        )))),
                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new readHeapExpression(new VarExp("a")))))))));
        allStatements.add(ex7);

        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new BoolType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

            allStatements.add(ex8);


        // for(int i = 1; i<=3; i++) print(i)
        /*IStmt ex9 = new CompStmt(new VarDeclStmt("i", new IntType()),
                new ForStmt("i",  new ValueExp(new IntValue(1)),
                        new ValueExp(new IntValue(3)),
                        new ArithExp("+", new VarExp("i"), new ValueExp(new IntValue(1))),
                        new PrintStmt(new VarExp("i"))
                        ));
        allStatements.add(ex9);
*/

        /*
        b.
        Ref int a; new(a,20);
        (for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a)));
        print(rh(a))
        The final Out should be {0,1,2,20}
         */
        IStmt ex10 = new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("v", new IntType()),
                                new CompStmt(new ForStmt("v",
                                        new ValueExp(new IntValue(0)),
                                        new ValueExp(new IntValue(3)),
                                        new ArithExp("+", new VarExp("v"), new ValueExp(new IntValue(1))),
                                        new ForkStmt(
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new AssignStmt("v", new ArithExp("*", new VarExp("v"), new readHeapExpression(new VarExp("a"))))))),
                                        new PrintStmt(new readHeapExpression(new VarExp("a")))))));

        allStatements.add(ex10);

        // int v; Ref int a;
        // v=10;new(a,22);
        // fork(wH(a,30);
        // v=32; print(v);
        // print(rH(a)))  ;
        // print(v);print(rH(a))
        IStmt ex11 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(
                                new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(
                                        new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                // fork(wH(a,30);v=32;print(v);print(rH(a)))
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new WriteStmt("a", new ValueExp(new IntValue(30))),
                                                                new CompStmt(
                                                                        new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new readHeapExpression(new VarExp("a"))))))),
                                                // print(v);print(rH(a))
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new readHeapExpression(new VarExp("a")))))))));
        allStatements.add(ex11);
        IStmt ex12 = new ForkStmt(
                new PrintStmt(new ValueExp(new IntValue(42)))
        );
        allStatements.add(ex12);

        //Ref int v1; int cnt;
        //new(v1,2);newSemaphore(cnt,rH(v1),1);
        //fork(acquire(cnt);wh(v1,rh(v1)*10));print(rh(v1));release(cnt));
        //fork(acquire(cnt);wh(v1,rh(v1)*10));wh(v1,rh(v1)*2));print(rh(v1));release(cnt
        //));
        //acquire(cnt);
        //print(rh(v1)-1);
        //release(cnt)
        IStmt ex13 = new CompStmt(
                new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(
                        new VarDeclStmt("cnt", new IntType()),
                        new CompStmt(
                                new NewStmt("v1", new ValueExp(new IntValue(2))),
                                new CompStmt(
                                        new NewSemaphoreStmt("cnt", new readHeapExpression(new VarExp("v1")), new ValueExp(new IntValue(1))),
                                        new CompStmt(
                                                new ForkStmt(
                                                        new CompStmt(
                                                                new AcquireStmt("cnt"),
                                                                new CompStmt(
                                                                        new WriteStmt("v1", new ArithExp("*", new readHeapExpression(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                        new CompStmt(
                                                                                new PrintStmt(new readHeapExpression(new VarExp("v1"))),
                                                                                new ReleaseStmt("cnt")
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompStmt(
                                                        new ForkStmt(
                                                                new CompStmt(
                                                                        new AcquireStmt("cnt"),
                                                                        new CompStmt(
                                                                                new WriteStmt("v1", new ArithExp("*",  new readHeapExpression(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                new CompStmt(
                                                                                        new WriteStmt("v1", new ArithExp("*",  new readHeapExpression(new VarExp("v1")), new ValueExp(new IntValue(2)))),
                                                                                        new CompStmt(
                                                                                                new PrintStmt(new readHeapExpression(new VarExp("v1"))),
                                                                                                new ReleaseStmt("cnt")
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompStmt(
                                                                new AcquireStmt("cnt"),
                                                                new CompStmt(
                                                                        new PrintStmt(new ArithExp("-", new readHeapExpression(new VarExp("v1")), new ValueExp(new IntValue(1)))),
                                                                        new ReleaseStmt("cnt")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        allStatements.add(ex13);


        IStmt ex14 = new CompStmt(new VarDeclStmt("v1", new IntType()),
                new CompStmt(new VarDeclStmt("v2", new IntType()),
                        new CompStmt(new AssignStmt("v1", new ValueExp(new IntValue(2))),
                                new CompStmt(new AssignStmt("v2", new ValueExp(new IntValue(3))),
                                        new IfStmt(new RelationalExpressions("!=", new VarExp("v1"), new ValueExp(new IntValue(0))),
                                                new PrintStmt(new MULExp(new VarExp("v1"), new VarExp("v2"))),
                                                new PrintStmt(new VarExp("v1")))))));
        allStatements.add(ex14);


        this.nrOfPrograms = allStatements.size()-1;
        return FXCollections.observableArrayList(allStatements);


    }

}