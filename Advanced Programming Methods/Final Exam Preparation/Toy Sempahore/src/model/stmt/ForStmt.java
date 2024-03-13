/*
1. (0.5p by default). Problem 1: Implement For statement in Toy Language.
a. (2.75p). Define the new statement:

for(v=exp1;v<exp2;v=exp3) stmt
Its execution on the ExeStack is the following:
- pop the statement
- create the following statement:

int v; v=exp1;(while(v<exp2) stmt;v=exp3)

- push the new statement on the stack
The typecheck method of for statement verifies if v, exp1, exp2, and exp3 have
the type int.
 */
package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyIHeap;
import model.ADT.MyIStack;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.exp.RelationalExpressions;
import model.exp.VarExp;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class ForStmt implements IStmt {

    private IStmt stmt;
    private String variable;
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;


    public ForStmt(String variable, Exp exp1, Exp exp2, Exp exp3, IStmt stmt){

        this.variable = variable;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;

    }

    //for(v=exp1;v<exp2;v=exp3) stmt
    @Override
    public String toString(){
        return "(for(" + exp1.toString() + " ; " + exp2.toString() + " ; " + exp3.toString() + " ; " + ")" + stmt.toString() + ")";
    }


    /*
    Its execution on the ExeStack is the following:
    - pop the statement
    - create the following statement:

    int v; v=exp1;(while(v<exp2) stmt;v=exp3)

    - push the new statement on the stack
*/

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack  = state.getExeStack();
        // transform for in while
        IStmt forToWhile = new CompStmt(new AssignStmt(variable, exp1), new WhileStmt(new RelationalExpressions("<", new VarExp(variable), exp2),
                           new CompStmt(stmt, new AssignStmt(variable, exp3))));

        stack.push(forToWhile);
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return null;
    }

    // The typecheck method of for statement verifies if v, exp1, exp2, and exp3 have
    //the type int.
    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typeExp1 = exp1.typecheck(typeEnv);
        Type typeExp2 = exp2.typecheck(typeEnv);
        Type typeExp3 = exp3.typecheck(typeEnv);
        if(typeExp1.equals(new IntType()) && typeExp2.equals(new IntType()) && typeExp3.equals(new IntType()))
        {
            return typeEnv;
        }
        else
            throw new MyException("For Statement has wrong types. They should be int!");
    }
}
