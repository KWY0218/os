package model.cpu;

import model.process.Process;
import model.schedular.Scheduler;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class CPU {

    private final int pCore = 0;
    private final int eCore = 1;
    private int pCoreCount;
    private int eCoreCount;
    private int coreCount;
    private List<Core> embeddedCore;
    private List<Process> processList;
    private Queue<Process> readyQueue;
    private Scheduler scheduler;

    public CPU(int pCoreCount, int eCoreCount, int coreCount, List<Process> processList, Scheduler scheduler) {
        this.embeddedCore = new ArrayList<Core>();
        this.pCoreCount = pCoreCount;
        this.eCoreCount = eCoreCount;
        this.coreCount = coreCount;
        this.processList = processList;
        this.scheduler = scheduler;

        processList.sort(Process::compareTo);
        for(int i=0; i<eCoreCount; i++)
            embeddedCore.add(new Core(1));
        for(int i=0; i<pCoreCount; i++)
            embeddedCore.add(new Core(1));
    }

    // 기준에 맞는 코어 추천
    private int recommendCore(int burstTime){
        if(burstTime >= 4)
            return pCore;

        return eCore;
    }

    //Core에 프로세스 할당
    private boolean assignProcess(Process process){
        int recommendedKind = recommendCore(process.getBurstTime());

        switch(recommendedKind) {
            case pCore:
                for(int i = eCoreCount; i < pCoreCount; i++){
                    if(embeddedCore.get(i).isRunning()) {
                        embeddedCore.get(i).setAssignedProcess(process);
                        return true;

                    }
                }
                break;
            case eCore:
                for(int i = 0; i < eCoreCount; i++) {
                    if (embeddedCore.get(i).isRunning()) {
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
            if (embeddedCore.get(i).isRunning()) {
                if(process.getRemainWork() == 1)
                    return false;
                embeddedCore.get(i).setAssignedProcess(process);
            }
        }
        //wait
        return false;
    }

    public void addProcess(int time){
        for(Process p:processList){
            if(p.getArrivalTime() == time)
                readyQueue.add(p);
        }
    }

    public void run(){
        int time = 0;
        System.out.println("run");

        while(remainWorking()) {
            addProcess(time);

            for (int i = 0; i < coreCount; i++) {
                Core core = embeddedCore.get(i);
                Process selectedProcess = scheduler.running(readyQueue, core.getAssignedProcess());//시간 정보?

                if (core.getAssignedPid() != selectedProcess.getPid()) {
                    assignProcess(selectedProcess);
                    core.emptyProcess();
                }
                core.run();
                core.clean();
                collectInfo();
            }

            time += 1;
        }
    }

    public double collectInfo(){
        double usingElectricity = 0;

        for(int i=0; i<coreCount; i++){
            usingElectricity += embeddedCore.get(i).getUsingElectricity();
        }

        return usingElectricity;
    }

    public boolean remainWorking(){
        for(Process process : processList){
            if(process.getRemainWork() > 0)
                return true;
        }
        return false;
    }
}

//Core 히스토리
//스케쥴러 스케줄러 작업만
//공통된 부분
//readyQueue
//QueueList
//Scheduler Running -> assignedProcess 매개변수 추가
//time추가
class Core{
    private Process assignedProcess;
    private double usingElectricity;
    private int ableWork; // E(1) or P(3)
    private List<Integer> history;

    public Core(int ableWork) {
        this.ableWork = ableWork;
        assignedProcess = null;
        usingElectricity = 0;
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
    public int getAssignedPid(){
        return assignedProcess.getPid();
    }

    public Process getAssignedProcess() {
        return assignedProcess;
    }

    public void run(){
        if(assignedProcess != null)
            history.add(assignedProcess.getPid());
        else
            history.add(-1);
        assignedProcess.worked(ableWork);
    }

    public void clean(){
        emptyProcess();
    }
}
