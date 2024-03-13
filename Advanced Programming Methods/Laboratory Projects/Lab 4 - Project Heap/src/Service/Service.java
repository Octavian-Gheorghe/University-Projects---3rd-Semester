package Service;

import Model.ADT.My_I_Stack;
import Model.Program_State.Program_State;
import Model.Statement.I_Statement;
import Model.Value.I_Value;
import Model.Value.RefValue;
import Repository.IRepository;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service {
    IRepository repository;
    boolean printStep = false;

    public Service(IRepository repo){
        repository = repo;
    }

    public void changePrintStep(boolean v){
        printStep = v;
    }

    public Program_State oneStep(Program_State prgState) throws Expression_Evaluation_Exception, Statement_Execution_Exception, ADT_Exception, Division_By_Zero_Exception {
        My_I_Stack<I_Statement> stk = prgState.getExeStack();
        if(stk.isEmpty())
            throw new Statement_Execution_Exception("ERROR: EMPTY PROGRAM STATE!");

        I_Statement thisStmt = stk.pop();
        prgState.setExeStack(stk);

        return thisStmt.execute(prgState);
    }

    private void showStep(){
        if(printStep)
            System.out.println(repository.getCurrentState().toString());
    }

    public List<Integer> getAddrFromSymTable(Collection<I_Value> symTableValues){
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<I_Value> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public Map<Integer, I_Value> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, I_Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public void allSteps() throws Expression_Evaluation_Exception, Statement_Execution_Exception, ADT_Exception, Division_By_Zero_Exception, IOException {
        Program_State prg = repository.getCurrentState();
        repository.logPrgStateExec();
        showStep();
        while(!prg.getExeStack().isEmpty()){
            oneStep(prg);
            repository.logPrgStateExec();

            prg.getHeap().setContent((HashMap<Integer, I_Value>) safeGarbageCollector(
                    getAddrFromSymTable(prg.getSymTable().getContent().values()), getAddrFromHeap(prg.getHeap().getContent().values()),
                    prg.getHeap().getContent()));
            
            repository.logPrgStateExec();
            showStep();
        }
    }
}