package controller;
//import jdk.jshell.spi.SPIResolutionException;
import model.ADT.MyIHeap;
import model.ADT.MyIList;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;
import model.MyException;
import model.PrgState;
import model.ADT.MyIStack;
import model.stmt.IStmt;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepository repository;
    //the executor makes sure that the threads are used optimally
    private ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public IRepository getRepository() {
        return repository;
    }

    public void setRepository(IRepository repository) {
        this.repository = repository;
    }

    public List<PrgState> getProgramStates() {
        return repository.getPrgList();
    }

    //add a method that gets the output list not the exe stack
    public MyIList<Value> getOut(PrgState state) {
        return state.getOut();
    }

    public void setProgramStates(List<PrgState> prgStates) {
        repository.setPrgList(prgStates);
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream() // converts the entry set into a stream
                .filter(e -> symTableAddr.contains(e.getKey())) // keeps only those entries that are present
                // in the symbolTable
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)); // collects the filtered
        // entries into a new map
    }

    // This method seems to implement a safer garbage collection strategy compared to
    // the "unsafe" version, ensuring that only addresses referenced in the symbol table or heap
    // references are retained.
    // SAFE GARBAGE COLLECTOR----
    private Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddresses, List<Integer> heapReferencedAddresses, PrgState program) {
        MyIHeap<Integer, Value> heap = program.getHeap();
        return heap.getContent().entrySet().stream()
                .filter(e -> symbolTableAddresses.contains(e.getKey()) || heapReferencedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /*
    -> takes a collection of Value objects (symTableValues)
    -> it filters the collection to keep only the instances of RefValue
    -> extracts the addresses from each RefValue
    -> and collects these addresses into a list of integers
     */
    private List<Integer> getAddrFromSymbolTable(Collection<Value> symbolTableValues) {
        //return the list of all RefValue addresses from the symbol table
        //creates a stream of Stream<Value>
        return symbolTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    //:: method reference operator used, behave like lambda expression with the help of the class directly ; can refer static methods, instances, void etc.
    //-> : lambda expression : separates parameters from the actual implementation
    private List<Integer> getAddrFromHeapTable(Collection<Value> heapTableValues) {
        return heapTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }


    public void oneStepForAllPrograms(List<PrgState> programList) throws MyException, InterruptedException {
        programList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        // list of callables ( a list of tasks for each thread) =  the next step they need to take
        List<Callable<PrgState>> callList = programList.stream()
                .filter(p -> !p.getExeStack().isEmpty()) // filter the active threads
                .map(p -> (Callable<PrgState>) (() -> { // create a list of 'next task'
                    if (!p.getExeStack().isEmpty()) {
                        return p.oneStep(); // if its not empty, progress the program with one step
                    } else {
                        return p;// otherwise dont make changes
                    }
                }))
                .collect(Collectors.toList());

        // simultan execution
        List<PrgState> newProgramList = executor.invokeAll(callList).stream()//a blocking call, and it waits for all
                // the tasks to complete before returning the list of Future objects.
                .map(future -> {
                    try {
                        return future.get(); // future = result of a task
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        // collect the newly created threads into the prg list
        programList.addAll(newProgramList);

        programList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        repository.setPrgList(programList);
    }

    /*private void printThings() {
        PrgState programState = repository.getCrtPrg();
        System.out.print("------------------------------------------------------\n");

        System.out.print("*****ExecutionStack*****\n");
        System.out.print(programState.getExeStack().toString() + "\n");

        System.out.print("*****OutputList*****\n");
        System.out.print(programState.getOut() + "\n");

        System.out.print("*****SymbolTable*****\n");
        System.out.print(programState.getSymTable().toString() + "\n");


        System.out.print("*****FileTable*****\n");
        System.out.print(programState.getFileTable().toString() + "\n");

        System.out.print("------------------------------------------------------\n");
    }
*/
    // filter out completed program states from the input list
    // provides a list of ongoing program statea
    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted).collect(Collectors.toList());
    }

    public void allStep() throws MyException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while (prgList.size() > 0) {
            //HERE you can call conservativeGarbageCollector
            // cleaning up unreferenced memory
            prgList.forEach(prg -> prg.getHeap().setContent(safeGarbageCollector(getAddrFromSymbolTable(prg.getSymTable().getContent().values()), getAddrFromHeapTable(prg.getHeap().getContent().values()),prg)));
            oneStepForAllPrograms(prgList);
            //remove the completed programs
            prgList = removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        // update the repository state
        repository.setPrgList(prgList);
    }

    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymbolTable(p.getSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        List<Integer> heapTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromHeapTable(p.getHeap().getAllValues()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());

        programStates.forEach(p -> p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, heapTableAddresses, p)));
    }

    public void oneStep() throws InterruptedException, MyException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repository.getPrgList());
        oneStepForAllPrograms(programStates);
        conservativeGarbageCollector(programStates);
        executor.shutdownNow();
    }
}