package view;

import model.PrgState;
import model.MyException;
import model.ADT.MyIStack;
import model.ADT.MyStack;
import model.exp.*;
import model.stmt.AssignStmt;
import model.stmt.CompStmt;
import model.stmt.IStmt;
import model.stmt.VarDeclStmt;
import model.type.StringType;
import model.value.StringValue;
import model.exp.ValueExp;
import model.ADT.MyIFileTable;
import model.ADT.MyFileTable;
import model.exp.VarExp;
import model.ADT.*;
import model.stmt.*;
import model.type.*;
import model.value.*;
import repository.*;
import controller.*;
//import view.*;
import java.util.List;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args){
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));


        menu.show();
    }
}
