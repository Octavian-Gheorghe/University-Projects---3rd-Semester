
package model.stmt;

        import model.ADT.*;
        import model.MyException;
        import model.PrgState;
        import model.type.Type;
        import model.value.Value;

        import java.io.BufferedReader;

public class ForkStmt implements IStmt {
    private IStmt statement;

    public ForkStmt(IStmt stmt) {
        this.statement = stmt;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = new MyStack<>();
       // MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIDictionary<String, Value> newDictionary = symTable.deepCopy();
        MyIList<Value> out = state.getOut();
        MyIFileTable<String, BufferedReader> fileTable = state.getFileTable();
        MyIHeap<Integer, Value> heap = state.getHeap();
        //MyIToySemaphoreTable toySemaphoreTable = state.getToySemaphoreTable();

        return new PrgState(stack, newDictionary, out, fileTable, heap,  this.statement);

    }

    @Override
    public IStmt deepcopy() {
        return new ForkStmt(statement);
    }


    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        statement.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Fork( " + statement + ")";

    }
}