package model.stmt;

import model.ADT.MyIDictionary;
import model.MyException;
import model.PrgState;
import model.ADT.MyIStack;
import model.type.Type;

public class CompStmt implements IStmt{
    private IStmt first;
    private IStmt second;
    public CompStmt(IStmt v, IStmt v2){
        first=v;
        second =v2;
    }
    public IStmt getFirst() {
        return first;
    }
    public void setFirst(IStmt first) {
        this.first = first;
    }
    public IStmt getSecond() {
        return second;
    }
    public void setSecond(IStmt second) {
        this.second = second;
    }
    public String toString(){
        return "("+first.toString()+";"+ second.toString()+")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> exeStack=state.getExeStack();
        exeStack.push(second);
        exeStack.push(first);
        //return state;
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new CompStmt(first, second);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        //MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        //MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        //return typEnv2;
        return second.typecheck(first.typecheck(typeEnv));
    }

}
