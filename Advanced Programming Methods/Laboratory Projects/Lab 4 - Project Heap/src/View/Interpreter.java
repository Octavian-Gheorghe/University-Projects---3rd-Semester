package View;

import Model.ADT.My_ADT_Dict;
import Model.ADT.My_ADT_Heap;
import Model.ADT.My_ADT_List;
import Model.ADT.My_ADT_Stack;
import Model.Expression.*;
import Model.Program_State.Program_State;
import Model.Statement.*;
import Model.Type.Bool_Type;
import Model.Type.Int_Type;
import Model.Type.Ref_Type;
import Model.Type.String_Type;
import Model.Value.Bool_Value;
import Model.Value.Int_Value;
import Model.Value.String_Value;
import Repository.IRepository;
import Repository.Repository;
import Service.Service;

public class Interpreter {
    public static void main(String[] args){
        I_Statement ex1 = new Compound_Statement(new Variable_Declaration_Statement("v", new Int_Type()),
                new Compound_Statement(new Assign_Statement("v", new Value_Expression(new Int_Value(2))),
                        new Print_Statement(new Variable_Expression("v"))));
        Program_State prg1 = new Program_State(new My_ADT_Stack<>(), new My_ADT_Dict<>(), new My_ADT_List<>(), ex1,  new My_ADT_Dict<>(), new My_ADT_Heap());
        IRepository repo1 = new Repository(prg1, "C:\\Projects_MAP\\A4 - Project Heap\\src\\zfiles\\log1.txt");
        Service service1 = new Service(repo1);

        I_Statement ex2 = new Compound_Statement(new Variable_Declaration_Statement("a",new Int_Type()),
                new Compound_Statement(new Variable_Declaration_Statement("b",new Int_Type()),
                        new Compound_Statement(new Assign_Statement("a", new Arithmetic_Expression('+',new Value_Expression(new Int_Value(2)),new
                                Arithmetic_Expression('*',new Value_Expression(new Int_Value(3)), new Value_Expression(new Int_Value(5))))),
                                new Compound_Statement(new Assign_Statement("b",new Arithmetic_Expression('+',new Variable_Expression("a"), new Value_Expression(new
                                        Int_Value(1)))), new Print_Statement(new Variable_Expression("b"))))));
        Program_State prg2 = new Program_State(new My_ADT_Stack<>(), new My_ADT_Dict<>(), new My_ADT_List<>(), ex2, new My_ADT_Dict<>(), new My_ADT_Heap());
        IRepository repo2 = new Repository(prg2, "C:\\Projects_MAP\\A4 - Project Heap\\src\\zfiles\\log2.txt");
        Service service2 = new Service(repo2);

        I_Statement ex3 = new Compound_Statement(new Variable_Declaration_Statement("a", new Bool_Type()),
                new Compound_Statement(new Variable_Declaration_Statement("v", new Int_Type()),
                        new Compound_Statement(new Assign_Statement("a", new Value_Expression(new Bool_Value(true))),
                                new Compound_Statement(new If_Statement(new Variable_Expression("a"),
                                        new Assign_Statement("v", new Value_Expression(new Int_Value(2))),
                                        new Assign_Statement("v", new Value_Expression(new Int_Value(3)))),
                                        new Print_Statement(new Variable_Expression("v"))))));
        Program_State prg3 = new Program_State(new My_ADT_Stack<>(), new My_ADT_Dict<>(), new My_ADT_List<>(), ex3,  new My_ADT_Dict<>(), new My_ADT_Heap());
        IRepository repo3 = new Repository(prg3, "C:\\Projects_MAP\\A4 - Project Heap\\src\\zfiles\\log3.txt");
        Service service3 = new Service(repo3);

        I_Statement ex4 = new Compound_Statement(new Variable_Declaration_Statement("varf", new String_Type()),
                new Compound_Statement(new Assign_Statement("varf", new Value_Expression(new String_Value("test.in"))),
                        new Compound_Statement(new Open_Read_File_Statement(new Variable_Expression("varf")),
                                new Compound_Statement(new Variable_Declaration_Statement("varc", new Int_Type()),
                                        new Compound_Statement(new Read_File_Statenebt(new Variable_Expression("varf"), "varc"),
                                                new Compound_Statement(new Print_Statement(new Variable_Expression("varc")),
                                                        new Compound_Statement(new Read_File_Statenebt(new Variable_Expression("varf"), "varc"),
                                                                new Compound_Statement(new Print_Statement(new Variable_Expression("varc")),
                                                                        new Close_Read_File_Statement(new Variable_Expression("varf"))))))))));

        Program_State prg4 = new Program_State(new My_ADT_Stack<>(), new My_ADT_Dict<>(), new My_ADT_List<>(), ex4, new My_ADT_Dict<>(), new My_ADT_Heap());
        IRepository repo4 = new Repository(prg4, "C:\\Projects_MAP\\A4 - Project Heap\\src\\zfiles\\log4.txt");
        Service service4 = new Service(repo4);

        I_Statement ex5 = new Compound_Statement(new Variable_Declaration_Statement("a", new Int_Type()),
                new Compound_Statement(new Variable_Declaration_Statement("b", new Int_Type()),
                        new Compound_Statement(new Assign_Statement("a", new Value_Expression(new Int_Value(5))),
                                new Compound_Statement(new Assign_Statement("b", new Value_Expression(new Int_Value(7))),
                                        new If_Statement(new Relational_Expression(">", new Variable_Expression("a"),
                                                new Variable_Expression("b")),new Print_Statement(new Variable_Expression("a")),
                                                new Print_Statement(new Variable_Expression("b")))))));

        Program_State prg5 = new Program_State(new My_ADT_Stack<>(), new My_ADT_Dict<>(), new My_ADT_List<>(), ex5, new My_ADT_Dict<>(), new My_ADT_Heap());
        IRepository repo5 = new Repository(prg5, "C:\\Projects_MAP\\A4 - Project Heap\\src\\zfiles\\log5.txt");
        Service service5 = new Service(repo5);


        I_Statement ex6 = new Compound_Statement(new Variable_Declaration_Statement("v", new Ref_Type(new Int_Type())),
                new Compound_Statement(new Heap_Allocation_Statement("v", new Value_Expression(new Int_Value(20))),
                        new Compound_Statement(new Variable_Declaration_Statement("a", new Ref_Type(new Ref_Type(new Int_Type()))),
                                new Compound_Statement(new Heap_Allocation_Statement("a", new Variable_Expression("v")),
                                        new Compound_Statement(new Print_Statement(new Variable_Expression("v")), new Print_Statement(new Variable_Expression("a")))))));
        Program_State prg6 = new Program_State(new My_ADT_Stack<>(), new My_ADT_Dict<>(), new My_ADT_List<>(), ex6, new My_ADT_Dict<>(), new My_ADT_Heap());
        IRepository repo6 = new Repository(prg6, "C:\\Projects_MAP\\A4 - Project Heap\\src\\zfiles\\log6.txt");
        Service service6 = new Service(repo6);

        I_Statement ex7 = new Compound_Statement(new Variable_Declaration_Statement("v", new Ref_Type(new Int_Type())),
                new Compound_Statement(new Heap_Allocation_Statement("v", new Value_Expression(new Int_Value(20))),
                        new Compound_Statement(new Variable_Declaration_Statement("a", new Ref_Type(new Ref_Type(new Int_Type()))),
                                new Compound_Statement(new Heap_Allocation_Statement("a", new Variable_Expression("v")),
                                        new Compound_Statement(new Print_Statement(new Read_Heap_Expression(new Variable_Expression("v"))),
                                                new Print_Statement(new Arithmetic_Expression('+',new Read_Heap_Expression(new Read_Heap_Expression(new Variable_Expression("a"))), new Value_Expression(new Int_Value(5)))))))));
        Program_State prg7 = new Program_State(new My_ADT_Stack<>(), new My_ADT_Dict<>(), new My_ADT_List<>(), ex7,  new My_ADT_Dict<>(), new My_ADT_Heap());
        IRepository repo7 = new Repository(prg7, "C:\\Projects_MAP\\A4 - Project Heap\\src\\zfiles\\log7.txt");
        Service service7 = new Service(repo7);

        I_Statement ex8 = new Compound_Statement(new Variable_Declaration_Statement("v", new Ref_Type(new Int_Type())),
                new Compound_Statement(new Heap_Allocation_Statement("v", new Value_Expression(new Int_Value(20))),
                        new Compound_Statement( new Print_Statement(new Read_Heap_Expression(new Variable_Expression("v"))),
                                new Compound_Statement(new Write_Heap_Statement("v", new Value_Expression(new Int_Value(30))),
                                        new Print_Statement(new Arithmetic_Expression('+', new Read_Heap_Expression(new Variable_Expression("v")), new Value_Expression(new Int_Value(5))))))));

        Program_State prg8 = new Program_State(new My_ADT_Stack<>(), new My_ADT_Dict<>(), new My_ADT_List<>(), ex8, new My_ADT_Dict<>(), new My_ADT_Heap());
        IRepository repo8 = new Repository(prg8, "C:\\Projects_MAP\\A4 - Project Heap\\src\\zfiles\\log8.txt");
        Service service8 = new Service(repo8);

        I_Statement ex9 = new Compound_Statement(new Variable_Declaration_Statement("v", new Ref_Type(new Int_Type())),
                new Compound_Statement(new Heap_Allocation_Statement("v", new Value_Expression(new Int_Value(20))),
                        new Compound_Statement(new Variable_Declaration_Statement("a", new Ref_Type(new Ref_Type(new Int_Type()))),
                                new Compound_Statement(new Heap_Allocation_Statement("a", new Variable_Expression("v")),
                                        new Compound_Statement(new Heap_Allocation_Statement("v", new Value_Expression(new Int_Value(30))),
                                                new Print_Statement(new Read_Heap_Expression(new Read_Heap_Expression(new Variable_Expression("a")))))))));

        Program_State prg9 = new Program_State(new My_ADT_Stack<>(), new My_ADT_Dict<>(), new My_ADT_List<>(), ex9, new My_ADT_Dict<>(), new My_ADT_Heap());
        IRepository repo9 = new Repository(prg9, "C:\\Projects_MAP\\A4 - Project Heap\\src\\zfiles\\log9.txt");
        Service service9 = new Service(repo9);

        I_Statement ex10 = new Compound_Statement(new Variable_Declaration_Statement("v", new Int_Type()),
                new Compound_Statement(new Assign_Statement("v", new Value_Expression(new Int_Value(4))),
                        new Compound_Statement(new While_Statement(new Relational_Expression(">", new Variable_Expression("v"), new Value_Expression(new Int_Value(0))),
                                new Compound_Statement(new Print_Statement(new Variable_Expression("v")), new Assign_Statement("v",new Arithmetic_Expression('-', new Variable_Expression("v"), new Value_Expression(new Int_Value(1)))))),
                                new Print_Statement(new Variable_Expression("v")))));

        Program_State prg10 = new Program_State(new My_ADT_Stack<>(), new My_ADT_Dict<>(), new My_ADT_List<>(), ex10, new My_ADT_Dict<>(), new My_ADT_Heap());
        IRepository repo10 = new Repository(prg10, "C:\\Projects_MAP\\A4 - Project Heap\\src\\zfiles\\log10.txt");
        Service service10 = new Service(repo10);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), service1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), service2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), service3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), service4));
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), service5));
        menu.addCommand(new RunExampleCommand("6", ex6.toString(), service6)); /// A4
        menu.addCommand(new RunExampleCommand("7", ex7.toString(), service7));
        menu.addCommand(new RunExampleCommand("8", ex8.toString(), service8));
        menu.addCommand(new RunExampleCommand("9", ex9.toString(), service9));
        menu.addCommand(new RunExampleCommand("10", ex10.toString(), service10));

        menu.show();
    }
}
