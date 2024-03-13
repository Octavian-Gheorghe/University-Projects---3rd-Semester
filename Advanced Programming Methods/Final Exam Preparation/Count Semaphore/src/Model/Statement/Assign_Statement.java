package Model.Statement;

import Model.ADT.My_I_Dict;
import Model.Expression.IExpression;
import Model.Program_State.Program_State;
import Model.Type.IType;
import Model.Value.IValue;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

public class Assign_Statement implements IStatement{
    private final String key;
    private final IExpression expression;

    public Assign_Statement(String k, IExpression e){
        key = k;
        expression = e;
    }

    @Override
    public Program_State execute(Program_State state) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_Dict<String, IValue> symbolTable = state.getSymTable();

        if (symbolTable.contains(key)) {
            IValue value = expression.eval(symbolTable, state.getHeap());
            IType typeId = (symbolTable.lookUp(key)).getType();
            if (value.getType().equals(typeId)) {
                symbolTable.update(key, value);
            } else {
                throw new Statement_Execution_Exception("ERROR: Assigment mismatch:  " + key);
            }
        } else {
            throw new Statement_Execution_Exception("ERROR: Undeclared variable:  " + key);
        }
        state.setSymTable(symbolTable);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new Assign_Statement(key, expression.deepCopy());
    }

    @Override
    public My_I_Dict<String, IType> typeCheck(My_I_Dict<String, IType> typeEnv) throws Statement_Execution_Exception, Expression_Evaluation_Exception, ADT_Exception {
        IType typeVar = typeEnv.lookUp(key);
        IType typeExpr = expression.typeCheck(typeEnv);
        if (typeVar.equals(typeExpr))
            return typeEnv;
        else
            throw new Statement_Execution_Exception("ERROR: Assignment: right hand side and left hand side have different types.");
    }

    @Override
    public String toString() {
        return String.format("%s = %s", key, expression.toString());
    }
}
