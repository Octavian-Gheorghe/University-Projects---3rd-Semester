/*
3. While Statement: Define and integrate the following While statement:
 while (expression) statement
The statement evaluation rule is as follows:
Stack1={while (exp1) Stmt1 | Stmt2|...}
SymTable1
Out1
HeapTable1
FileTable1
 ==>
If exp1 is evaluated to BoolValue then
 If exp1 is evaluated to False then Stack2={Stmt2|...}
 Else Stack2={Stmt1 | while (exp1) Stmt1 | Stmt2|...}
Else
 throws new MyException("condition exp is not a boolean")
SymTable2=SymTable1
Out2=Out1
HeapTable2=HeapTable1
FileTable2=FileTable1
Example: int v; v=4; (while (v>0) print(v);v=v-1);print(v)
 */
package model.stmt;

import model.ADT.MyIHeap;
import model.ADT.MyIDictionary;
import model.ADT.MyIStack;
import model.MyException;
import model.PrgState;
import model.exp.Exp;
import model.type.BoolType;
import model.type.Type;
import model.value.BoolValue;
import model.value.Value;

public class WhileStmt implements IStmt{
    private IStmt stmt;

    private Exp exp;

    public WhileStmt(Exp exp, IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public IStmt getStmt() {
        return stmt;
    }

    public void setStmt(IStmt stmt) {
        this.stmt = stmt;
    }
    @Override
    public String toString() {
        return "(while(" + exp.toString() + ")" + stmt.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getExeStack();
        MyIDictionary<String, Value> symTable = state.getSymTable();
        MyIHeap<Integer, Value> heap = state.getHeap();

        Value v = exp.eval(symTable, heap);
        if (v.getType() instanceof BoolType) {
            BoolValue boolV = (BoolValue) v;
            if (boolV.getVal()) {
                stack.push(this);
                stack.push(stmt);
            }
        }
        else {
            throw new MyException("Expression type must be BoolType!");
        }
        return null;
    }

    @Override
    public IStmt deepcopy() {
        return new WhileStmt(exp, stmt);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new MyException("The condition of WHILE has not the type bool");
    }

}