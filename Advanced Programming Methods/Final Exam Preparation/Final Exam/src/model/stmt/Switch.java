package model.stmt;

import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.exp.RelationalExpressions;
import model.type.Type;

public class Switch implements IStmt{
    private Exp exp;
    private Exp exp1;
    private IStmt stmt1;
    private Exp exp2;
    private IStmt stmt2;
    private IStmt stmt3;

    public Switch(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public String toString() {
        return "Switch(" + exp +
                ") (case " + exp1 +
                ": " + stmt1 +
                ") (case " + exp2 +
                ": " + stmt2 +
                ") (default: " + stmt3 +
                ')';
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        IStmt converted = new IfStmt(new RelationalExpressions("==", exp, exp1), stmt1,
                new IfStmt(new RelationalExpressions("==", exp, exp2), stmt2, stmt3));
        stack.push(converted);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        Type type1 = exp1.typecheck(typeEnv);
        Type type2 = exp2.typecheck(typeEnv);
        if (type.equals(type1) && type.equals(type2)) {
            stmt1.typecheck(typeEnv.deepCopy());
            stmt2.typecheck(typeEnv.deepCopy());
            stmt3.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else {
            throw new MyException("The expression types do not match in the switch statement!");
        }
    }

    @Override
    public IStmt deepcopy() {
        return new Switch(exp, exp1, stmt1, exp2, stmt2, stmt3);
    }
}
