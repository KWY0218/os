package model.cpu;

import model.process.Process;
import model.schedular.Scheduler;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class CPU {

    private final int P_CORE_TYPE = 3;
    private final int E_CORE_TYPE = 1;
    private int pCoreCount;
    private int eCoreCount;
    private int coreCount;
    private List<Core> embeddedCore;
    private List<Process> processList;
    private Queue<Process> readyQueue;
    private Scheduler scheduler;

    public CPU(int pCoreCount, int eCoreCount, int coreCount, List<Process> processList, Scheduler scheduler) {
        this.pCoreCount = pCoreCount;
        this.eCoreCount = eCoreCount;
        this.coreCount = coreCount;
        this.scheduler = scheduler;
        this.embeddedCore = new ArrayList<Core>();
        this.readyQueue = new LinkedList<Process>();
        this.processList = processList;
        processList.sort(Process::compareTo);

        for(int i=0; i<eCoreCount; i++)
            embeddedCore.add(new Core(E_CORE_TYPE, 1));
        for(int i=0; i<pCoreCount; i++)
            embeddedCore.add(new Core(P_CORE_TYPE, 3));
    }

    // 기준에 맞는 코어 추천
    private int recommendCore(int burstTime){
        if(burstTime >= 4)
            return P_CORE_TYPE;

        return E_CORE_TYPE;
    }

    //Core에 프로세스 할당
    private boolean assignProcess(Process process){
        int recommendedKind = recommendCore(process.getBurstTime());
        switch(recommendedKind) {
            case P_CORE_TYPE:
                for(int i = eCoreCount; i < pCoreCount; i++){
                    if(!embeddedCore.get(i).isRunning()) {
                        embeddedCore.get(i).setAssignedProcess(process);
                        return true;

                    }
                }
                break;
            case E_CORE_TYPE:
                for(int i = 0; i < eCoreCount; i++) {
                    if (!embeddedCore.get(i).isRunning()) {
                        embeddedCore.get(i).setAssignedProcess(process);
                        return true;
                    }
                }
                break;
            default:
                //throw assignProcessError
                System.out.println("assignProcessError Process id : " + process.getPid());
        }

        for(int i = 0; i< coreCount; i++){
            if (!embeddedCore.get(i).isRunning()) {
                embeddedCore.get(i).setAssignedProcess(process);
                return true;
            }
        }
        //wait
        return false;
    }

    /**

     * ProcessList에서 arrivalTime이 time인 Process readyQueue에 추가

     *

     * @param time : compare to Process.arrivalTime

     * @return

     */
    public void addProcess(int time){
        for(Process p:processList){
            if(p.getArrivalTime() == time)
                readyQueue.add(p);
        }
    }

    public void run(){
        int time = 0;
        List<Process> selectedProcess;
        System.out.println("CPU.run");

        while(remainWorking()) {
            System.out.println(String.format("=====TIME : %d=====\n", time));
            printProcessList();

            addProcess(time);
            cleanCores();
            selectedProcess = scheduler.running(readyQueue, countRunAbleCore());
            for(Process process:selectedProcess)
                assignProcess(process);
            for(Core core: embeddedCore){
                core.run();
            }
            printCoreStatuses();
            if(time >= 100)
                break;
            time += 1;
        }
    }

    public boolean remainWorking(){
        for(Process process : processList){
            if(process.getRemainWork() > 0)
                return true;
        }
        return false;
    }

    public int countRunAbleCore(){
        int count = 0;
        for(Core core:embeddedCore){
            if(!core.isRunning())
                count++;
        }

        return count;
    }
    public void cleanCores(){
        for(Core core:embeddedCore){
            Process inProcess = core.getAssignedProcess();
            if(inProcess == null || core.getAssignedProcess().getRemainWork() != 0)
                continue;

            core.emptyProcess();

        }
    }

    /**
     * print status of core to console
     * @see
     *
     *
     */
    public void printCoreStatuses(){
        for(int i=0; i<coreCount; i++){
            System.out.println(String.format("Core #%d", i));
            System.out.print(String.format("Core Type : "));
            switch(embeddedCore.get(i).getCoreType()){
                case P_CORE_TYPE:
                    System.out.println("P");
                    break;
                case E_CORE_TYPE:
                    System.out.println("E");
                    break;
                default:
                    System.out.println("ERROR");
            }
            System.out.println(embeddedCore.get(i).getAssignedProcess());
        }
    }

    public void printProcessList(){
        for(Process process : processList){
            System.out.println(String.format("Process id : %d", process.getPid()));
            System.out.println(String.format("AT : %d, BT : %d", process.getArrivalTime(), process.getBurstTime()));
            System.out.println(String.format("Remain Work : %d", process.getRemainWork()));
        }
    }

    public void printCoreHitory(){
        for(Core core : embeddedCore)
            System.out.println(core);
    }
}

class Core{
    private Process assignedProcess;
    private double usingElectricity;
    private final int ABLE_WORK; // E(1) or P(3)
    private final int ELECTRICITY;
    private List<Integer> history;

    public Core(int ableWork, int electricity) {
        this.ABLE_WORK = ableWork;
        assignedProcess = null;
        usingElectricity = 0;
        this.ELECTRICITY = electricity;
        history = new ArrayList<Integer>();
    }

    public double getUsingElectricity() {
        return usingElectricity;
    }

    public boolean isRunning(){
        return assignedProcess != null;
    }

    public void setAssignedProcess(Process assignedProcess) {
        this.assignedProcess = assignedProcess;
    }

    public void emptyProcess(){
        assignedProcess = null;
    }

    /**
     * return id of assignedProcess or return -1 if assignedProcess is null
     * @return null:-1 or pid:assignedProcess.getPid
     */
    public int getAssignedProcessId(){
        if(assignedProcess == null)
            return - 1;
        return assignedProcess.getPid();
    }

    public Process getAssignedProcess() {
        return assignedProcess;
    }

    public List<Integer> getHistory() {
        return history;
    }

    public void run(){
        if(assignedProcess != null) {
            usingElectricity+=ELECTRICITY;
            history.add(assignedProcess.getPid());
            assignedProcess.worked(ABLE_WORK);
        }
        else {
            usingElectricity += 0.1;
            history.add(-1);
        }

    }

    public int getCoreType() {
        return ABLE_WORK;
    }

    @Override
    public String toString() {
        return history.toString();
    }
}
