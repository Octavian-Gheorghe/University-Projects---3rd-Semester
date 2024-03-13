package Service;

import Model.Program_State.Program_State;
import Model.Value.IValue;
import Model.Value.Ref_Value;
import Repository.IRepository;
import Exception.ADT_Exception;
import Exception.Division_By_Zero_Exception;
import Exception.Expression_Evaluation_Exception;
import Exception.Statement_Execution_Exception;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Service {
    IRepository repository;
    boolean printStep = false;

    ExecutorService executorService;

    public Service(IRepository repo){
        repository = repo;
    }

    public void changePrintStep(boolean v){
        printStep = v;
    }

    public List<Program_State> removeCompletedProgram(List<Program_State> inPrgList){
        return inPrgList.stream().filter(p -> !p.isNotCompleted()).collect(Collectors.toList());
    }

//    public ProgramState oneStep(ProgramState prgState) throws ExprEvalException, StmtExeException, ADTException, DivisionByZero {
//        MyIStack<IStatement> stk = prgState.getExeStack();
//        if(stk.isEmpty())
//            throw new StmtExeException("ERROR: EMPTY PROGRAM STATE!");
//
//        IStatement thisStmt = stk.pop();
//        prgState.setExeStack(stk);
//
//        return thisStmt.execute(prgState);
//    }

    private void showStep(Program_State programState){
        if(printStep)
            System.out.println(programState.toString());
    }

    public List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues){
        return symTableValues.stream()
                .filter(v -> v instanceof Ref_Value)
                .map(v -> {
                    Ref_Value v1 = (Ref_Value) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public List<Integer> getAddrFromHeap(Collection<IValue> heapValues) {
        return heapValues.stream()
                .filter(v -> v instanceof Ref_Value)
                .map(v -> {
                    Ref_Value v1 = (Ref_Value) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public Map<Integer, IValue> safeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> ( symTableAddr.contains(e.getKey()) || heapAddr.contains(e.getKey())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void oneStepForAllPrograms(List<Program_State> programStates) throws InterruptedException {
        programStates.forEach(programState -> {
            try{
                repository.logPrgStateExec(programState);
                showStep(programState);
            } catch (IOException | ADT_Exception e){
                System.out.println(e.getMessage());
            }
        });

        List<Callable<Program_State>> callableList = programStates.stream()
                .map((Program_State p) -> (Callable <Program_State>) (p::oneStep))
                .collect(Collectors.toList());

        List<Program_State> newProgramList = executorService.invokeAll(callableList).stream()
                .map(future -> {
                    try{
                        return future.get();
                    } catch (ExecutionException | InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                    return null;
                }).filter(Objects::nonNull)
                .collect(Collectors.toList());

        programStates.addAll(newProgramList);

        programStates.forEach(programState -> {
            try{
                repository.logPrgStateExec(programState);
                showStep(programState);
            } catch (IOException | ADT_Exception e){
                System.out.println(e.getMessage());
            }
        });

        repository.setProgramList(programStates);
    }


    public void allSteps() throws Expression_Evaluation_Exception, Statement_Execution_Exception, ADT_Exception, Division_By_Zero_Exception, IOException, InterruptedException {
        executorService = Executors.newFixedThreadPool(2);

        /// remove the completed programs
        List<Program_State> prgList = removeCompletedProgram(repository.getProgramList());

        while(prgList.size() > 0){
            conservativeGarbageCollector(prgList);
            oneStepForAllPrograms(prgList);
            prgList = removeCompletedProgram(repository.getProgramList());
        }

        executorService.shutdownNow();

        // HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository

        // update the repository state

        repository.setProgramList(prgList);

    }

    public void conservativeGarbageCollector(List<Program_State> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());

        programStates.forEach(p -> {
            p.getHeap().setContent((HashMap<Integer, IValue>) safeGarbageCollector(symTableAddresses, getAddrFromHeap(p.getHeap().getContent().values()), p.getHeap().getContent()));
        });
    }


}