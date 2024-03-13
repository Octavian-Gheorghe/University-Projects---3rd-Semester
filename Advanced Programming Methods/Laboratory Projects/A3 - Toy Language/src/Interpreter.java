import Model.Program_State;
import Model.ADT.I_Stack;
import Model.ADT.ADT_Stack;
import Model.Expression.*;
import Model.Statement.Assign_Statement;
import Model.Statement.Compound_Statement;
import Model.Statement.I_Statement;
import Model.Statement.Variable_Declaration_Statement;
import Model.Type.String_Type;
import Model.Value.String_Value;
import Model.Expression.Value_Expression;
import Model.ADT.I_File_Table;
import Model.ADT.ADT_File_Table;
import Model.Expression.Variable_Expression;
import Model.ADT.*;
import Model.Statement.*;
import Model.Type.*;
import Model.Value.*;
import Repository.*;
import Controller.*;
import View.*;
import java.util.List;

import java.io.BufferedReader;

public class Interpreter {
    public static void main(String[] args){
        Text_Menu menu = new Text_Menu();
        menu.add_command(new Exit_Command("0", "exit"));

        //int v; v=2;Print(v)
        I_Statement ex4= new Compound_Statement(new Variable_Declaration_Statement("v",new Integer_Type()),
                new Compound_Statement(new Assign_Statement("v",new Value_Expression(new Integer_Value(2))), new Print_Statement(new
                        Variable_Expression("v"))));
        I_Stack<I_Statement> stk4 = new ADT_Stack<I_Statement>();
        I_Dictionary<String, I_Value> symtbl4 = new ADT_Dictionary<String, I_Value>();
        I_List<I_Value> out4 = new ADT_List<I_Value>();
        I_File_Table<String, BufferedReader> fileTable4 = new ADT_File_Table<String, BufferedReader>();

        Program_State prg4 = new Program_State(stk4,symtbl4,out4,fileTable4,ex4);
        List<Program_State> l4 = List.of(prg4);
        I_Repository repo4 = new Repository(l4,"log1.txt");
        Controller ctr4 = new Controller(repo4);
        menu.add_command(new Run_Example_Command("1", ex4.toString(), ctr4));

        //int a;int b; a=2+3*5;b=a+1;Print(b)
        /*I_Statement ex2 = new Compound_Statement( new Variable_Declaration_Statement("a",new Integer_Type()),
                new Compound_Statement(new Variable_Declaration_Statement("b",new Integer_Type()),
                        new Compound_Statement(new Assign_Statement("a", new Arithmetic_Expression("+",new Value_Expression(new Integer_Value(2)),new
                                Arithmetic_Expression("*",new Value_Expression(new Integer_Value(3)), new Value_Expression(new Integer_Value(5))))),
                                new Compound_Statement(new Assign_Statement("b",new Arithmetic_Expression("+",new Variable_Expression("a"), new Value_Expression(new
                                        Integer_Value(7)))), new Print_Statement(new Variable_Expression("b"))))));*/
        I_Statement ex2 = new Compound_Statement( new Variable_Declaration_Statement("a",new Integer_Type()),
                new Compound_Statement(new Variable_Declaration_Statement("b",new Integer_Type()),
                        new Compound_Statement(new Assign_Statement("a", new Arithmetic_Expression("+",new Value_Expression(new Integer_Value(2)),new
                                Arithmetic_Expression("*",new Value_Expression(new Integer_Value(3)), new Value_Expression(new Integer_Value(5))))),
                                new Compound_Statement(new Assign_Statement("b",new Arithmetic_Expression("+",new Variable_Expression("a"), new Arithmetic_Expression("-",
                                      new Value_Expression(new Integer_Value(7)), new Arithmetic_Expression("/", new Value_Expression(new Integer_Value(4)), new Value_Expression(new Integer_Value(2)))))), new Print_Statement(new Variable_Expression("b"))))));
        I_Stack<I_Statement> stk2 = new ADT_Stack<>();
        I_Dictionary<String, I_Value> symtbl2 = new ADT_Dictionary<>();
        I_List<I_Value> out2 = new ADT_List<>();
        I_File_Table<String, BufferedReader> fileTable2 = new ADT_File_Table<>();

        Program_State prg2 = new Program_State(stk2, symtbl2, out2,fileTable2, ex2);
        List<Program_State> myPrgList2 = List.of(prg2);
        I_Repository repo2 = new Repository(myPrgList2, "log2.txt");
        Controller ctr2 = new Controller(repo2);
        menu.add_command(new Run_Example_Command("2", ex2.toString(), ctr2));


        //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        I_Statement ex3 = new Compound_Statement(new Variable_Declaration_Statement("a",new Boolean_Type()),
                new Compound_Statement(new Variable_Declaration_Statement("v", new Integer_Type()),
                        new Compound_Statement(new Assign_Statement("a", new Value_Expression(new Boolean_Value(true))),
                                new Compound_Statement(new If_Statement(new Variable_Expression("a"),new Assign_Statement("v",new Value_Expression(new
                                        Integer_Value(2))), new Assign_Statement("v", new Value_Expression(new Integer_Value(3)))), new Print_Statement(new
                                        Variable_Expression("v"))))));
        I_Stack<I_Statement> stk3 = new ADT_Stack<>();
        I_Dictionary<String, I_Value> symtbl3 = new ADT_Dictionary<>();
        I_List<I_Value> out3 = new ADT_List<>();
        I_File_Table<String, BufferedReader> fileTable3 = new ADT_File_Table<>();

        Program_State prgState3 = new Program_State(stk3, symtbl3, out3,fileTable3, ex3);
        List<Program_State> myPrgList3 = List.of(prgState3);
        I_Repository repo3 = new Repository(myPrgList3, "log3.txt");
        Controller ctr3 = new Controller(repo3);
        menu.add_command(new Run_Example_Command("3", ex3.toString(), ctr3));

        //THE READ FROM FILE EXAMPLE:
        I_Statement example1 = new Compound_Statement(new Variable_Declaration_Statement("varf", new String_Type()), new Compound_Statement(new Assign_Statement("varf", new Value_Expression(new String_Value("test.in"))), new Compound_Statement(new Open_Read_File_Statement(new Variable_Expression("varf")), new Compound_Statement(new Variable_Declaration_Statement("varc", new Integer_Type()), new Compound_Statement(new Open_File_Statement(new Variable_Expression("varf"), "varc"), new Compound_Statement(new Print_Statement(new Variable_Expression("varc")), new Compound_Statement(new Open_File_Statement(new Variable_Expression("varf"), "varc"), new Compound_Statement(new Print_Statement(new Variable_Expression("varc")), new Close_Read_File_Statement(new Variable_Expression("varf"))))))))));
        I_Stack<I_Statement> stk1 = new ADT_Stack<I_Statement>();
        I_Dictionary<String, I_Value> symtbl1 = new ADT_Dictionary<String, I_Value>();
        I_List<I_Value> out1 = new ADT_List<I_Value>();
        I_File_Table<String, BufferedReader> fileTable1 = new ADT_File_Table<String, BufferedReader>();

        Program_State prg1 = new Program_State(stk1,symtbl1,out1,fileTable1,example1);
        List<Program_State> l1 = List.of(prg1);
        I_Repository repo1 = new Repository(l1,"testOpenReadClose.txt");
        Controller ctr1 = new Controller(repo1);
        menu.add_command(new Run_Example_Command("4", example1.toString(), ctr1));

        menu.show();

    }
}
