package Model.Statement;

import Model.ADT.My_ADT_Dictionary;
import Model.ADT.My_I_Dict;
import Model.ADT.My_I_Stack;
import Model.ADT.My_ADT_Stack;
import Model.Program_State.Program_State;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

import java.util.Map;

public class Fork_Statement implements IStatement{
    private final IStatement statement;

    public Fork_Statement(IStatement statement){
        this.statement = statement;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_Stack<IStatement> newStack = new My_ADT_Stack<>();
        newStack.push(statement);

        My_I_Dict<String, IValue> newSymTable = new My_ADT_Dictionary<>();
        for (Map.Entry<String, IValue> e: state.getSymTable().getContent().entrySet()){
            newSymTable.put(e.getKey(), e.getValue().deepCopy());
        }

        return new Program_State(newStack, newSymTable, state.getOut(), state.getFileTable(), state.getHeap());
    }

    @Override
    public IStatement deepCopy() {
        return new Fork_Statement(statement.deepCopy());
    }

    @Override
    public My_I_Dict<String, IType> typeCheck(My_I_Dict<String, IType> typeEnv) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString(){
        return String.format("Fork(%s)", statement.toString());
    }
}
