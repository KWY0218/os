package model.cpu;

import model.process.Process;
import model.schedular.Scheduler;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class CPU {
    /*
    각각의 코어는 처리능력으로 구분함
     */
    private final int P_CORE_TYPE = 2;
    private final int E_CORE_TYPE = 1;
    private int pCoreCount;
    private int eCoreCount;
    private int coreCount;
    private List<Core> embeddedCore;
    private List<Process> processList;
    private Queue<Process> readyQueue;
    private Scheduler scheduler;

    public CPU(int pCoreCount, int eCoreCount, List<Process> processList, Scheduler scheduler) {
        this.pCoreCount = pCoreCount;
        this.eCoreCount = eCoreCount;
        this.coreCount = coreCount = pCoreCount + eCoreCount;
        this.scheduler = scheduler;
        this.embeddedCore = new ArrayList<Core>();
        this.readyQueue = new LinkedList<Process>();
        this.processList = processList;
        processList.sort(Process::compareTo);

        for(int i=0; i<eCoreCount; i++)
            embeddedCore.add(new Core(E_CORE_TYPE, 1));
        for(int i=eCoreCount; i<coreCount; i++)
            embeddedCore.add(new Core(P_CORE_TYPE, 3));
    }

    /**
     * 프로세스의 버스트타임을 기준으로 코어를 추천함
     * burstTime을 입력받고 해당 BurstTime이 4이상일 경우 P_CORE_TYPE = 3
     * 아닐 경우, E_CORE_TYPE = 1을 반환함
     * @param burstTime : Process's BurstTime
     * @return burstTime >= 4 ? P_CORE : E_CORE
     */
    private int recommendCore(int burstTime){
        if(burstTime >= 4)
            return P_CORE_TYPE;

        return E_CORE_TYPE;
    }

    /**
     * 프로세스를 전달받아 해당 프로세스를 코어에 등록합니다.
     * 1. CPU.recommendCore() 를 이용해 추천 코어를 설장한다.
     * 2. 추천 코어가 비어있을 경우, 해당 코어에 할당한다. -> true
     * 3. 빈 코어가 있는지 확인한다. 있다면 할당한다 -> true
     * 4. 할당 불가능 -> false
     * @param process 할당해야할 프로세스
     * @return true : 할당성공, false : wait
     */
    private boolean assignProcess(Process process){
        int recommendedKind = recommendCore(process.getBurstTime());
        switch(recommendedKind) {
            case P_CORE_TYPE:
                for(int i = eCoreCount; i < coreCount; i++){
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

     * Time정보를 입력받아. 입력받은 ProcessList에서 현재 Time정보에 맞는 ArrivalTime을 레디큐에 추가한다.

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

    /**
     * 스케줄링을 실행한다.
     * 프로세스리스트에서 작업량이 남은 프로세스가 존재한다면
     * 1. addProcess를 이용하여, 현재 시간에 도착한 프로세스를 레디큐에 추가한다.
     * 2. 코어중 빈 코어가 있다면, 할당된 코어를 null로 변경한다.
     * 3. 스케줄러해서 반환된 현재 실행해야할 프로세스리스트를 selectedProcess에 저장한다.
     * 4. 선택된 프로세스중 assignProcess를 이용하여 코어에 할당 성공한 경우 selectedProcess에서 해당 객체를 삭제한다.
     * 5. 각 코어를 동작시킨다.
     * 6. 시간을 증가시킨다.
     */
    public void run(){
        int time = 0;
        List<Process> selectedProcess = new ArrayList<Process>();
        System.out.println("CPU.run");

        while(remainWorking()) {
            //System.out.println(String.format("=====TIME : %d=====\n", time));
            //printProcessList();

            addProcess(time);
            cleanCores(time);
            selectedProcess.addAll(scheduler.running(readyQueue, countRunAbleCore()));
            selectedProcess.removeIf(this::assignProcess);                              // 조건문으로 변경 -> 프로세스 삭제시,  설정 가능, time 인수로 전달
            //printCoreStatuses();
            for(Core core: embeddedCore){
                core.run();
            }
//            if(time >= 100)
//                break;
            time += 1;
        }
    }

    /**
     * 전체 프로세스를 조사하여, 잔여 작업량이 남은 경우 -> true
     * 아니면 -> false
     * @return work is remaining
     */
    public boolean remainWorking(){
        for(Process process : processList){
            if(process.getRemainWork() > 0)
                return true;
        }
        return false;
    }

    /**
     * 사용 가능한 코어를 조사하여, 그 개수를 반환한다.
     * @return
     */
    public int countRunAbleCore(){
        int count = 0;
        for(Core core:embeddedCore){
            if(!core.isRunning())
                count++;
        }

        return count;
    }

    /**
     * 각 코어별 할당된 작업이 끝난 코어를 조사한다.
     * 만약 끝난 코어가 존재한다면, 해당 코어의 할당 프로세스의 endTime을 설정한다.
     * @param time
     */
    public void cleanCores(int time){
        for(Core core:embeddedCore){
            Process inProcess = core.getAssignedProcess();
            if(inProcess == null || core.getAssignedProcess().getRemainWork() != 0)
                continue;
            core.getAssignedProcess().setEndTime(time);
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
