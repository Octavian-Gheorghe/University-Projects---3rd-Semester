package View;

import Model.ADT.*;
import Model.Expression.Arithmetic_Expression;
import Model.Expression.Value_Expression;
import Model.Expression.Variable_Expression;
import Model.Program_State.Program_State;
import Model.Statement.*;
import Model.Type.Bool_Type;
import Model.Type.Int_Type;
import Model.Value.Bool_Value;
import Model.Value.IValue;
import Model.Value.Int_Value;
import Repository.IRepository;
import Repository.Repository;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;
import Service.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class View {
    private void printMenu(){
        System.out.print("\nMenu: \n" +
                "\t0. Exit\n" +
                "\t1. Run program1\n" +
                "\t2. Run program2\n" +
                "\t3. Run Program3\n " +
                "Option: ");
    }

    public void run(){
        while(true){
            try{
                printMenu();

                Scanner scn = new Scanner(System.in);
                int cmd = scn.nextInt();

                if(cmd == 0)
                    break;

                else if (cmd == 1)
                    runPrg1();

                else if (cmd == 2)
                    runPrg2();

                else if (cmd == 3)
                    runPrg3();

                else
                    System.out.println("Invalid command!");


            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    private void runPrg1() throws Statement_Execution_Exception, ADT_Exception, Expression_Evaluation_Exception, Division_By_Zero_Exception, IOException, InterruptedException {
        IStatement stmt = new Compound_Statement(new Variable_Declaration_Statement("v", new Int_Type()),
                new Compound_Statement(new Assign_Statement("v", new Value_Expression(new Int_Value(2))),
                        new Print_Statement(new Variable_Expression("v"))));

        runStatement(stmt);
    }

    private void runPrg2() throws Statement_Execution_Exception, ADT_Exception, Expression_Evaluation_Exception, Division_By_Zero_Exception, IOException, InterruptedException {
        IStatement stmt = new Compound_Statement(new Variable_Declaration_Statement("a",new Int_Type()),
                new Compound_Statement(new Variable_Declaration_Statement("b",new Int_Type()),
                        new Compound_Statement(new Assign_Statement("a", new Arithmetic_Expression('+',
                                new Value_Expression(new Int_Value(2)), new Arithmetic_Expression('*',
                                new Value_Expression(new Int_Value(3)), new Value_Expression(new Int_Value(5))))),
                                new Compound_Statement(new Assign_Statement("b", new Arithmetic_Expression('+',
                                        new Variable_Expression("a"), new Value_Expression(new Int_Value(1)))),
                                        new Print_Statement(new Variable_Expression("b"))))));
        runStatement(stmt);
    }

    private void runPrg3() throws Statement_Execution_Exception, ADT_Exception, Expression_Evaluation_Exception, Division_By_Zero_Exception, IOException, InterruptedException {
        IStatement stmt = new Compound_Statement(new Variable_Declaration_Statement("a", new Bool_Type()),
                new Compound_Statement(new Variable_Declaration_Statement("v", new Int_Type()),
                        new Compound_Statement(new Assign_Statement("a", new Value_Expression(new Bool_Value(true))),
                                new Compound_Statement(new If_Statement(new Variable_Expression("a"),
                                        new Assign_Statement("v", new Value_Expression(new Int_Value(2))),
                                        new Assign_Statement("v", new Value_Expression(new Int_Value(3)))),
                                        new Print_Statement(new Variable_Expression("v"))))));

        runStatement(stmt);

    }

    private void runStatement(IStatement stmt) throws Expression_Evaluation_Exception, Statement_Execution_Exception, ADT_Exception, Division_By_Zero_Exception, IOException, InterruptedException {
        My_I_Stack<IStatement> executionStack = new My_ADT_Stack<>();
        My_I_Dict<String, IValue> symbolTable = new My_ADT_Dictionary<>();
        My_I_List<IValue> out = new My_ADT_List<>();
        My_I_Dict<String, BufferedReader> fileTable = new My_ADT_Dictionary<>();
        My_I_Heap heap = new My_ADT_Heap();

        Program_State programState = new Program_State(executionStack, symbolTable, out, stmt, fileTable, heap);

        IRepository repo = new Repository(programState, "log.txt");
        Service serv = new Service(repo);

        System.out.print("Print steps?(y/n) ");
        Scanner scn = new Scanner(System.in);
        String cmd = scn.next();
        serv.changePrintStep(Objects.equals(cmd, "y"));

        serv.allSteps();
        System.out.println("\nResult is" + programState.getOut().toString().replace('[', ' ').replace(']', ' '));
    }
}
