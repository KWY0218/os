package model.cpu;

import model.process.Process;
import model.schedular.RR;
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
    private final Scheduler scheduler;
    private final int P_CORE_TYPE = 2;
    private final int E_CORE_TYPE = 1;
    private final int pCoreCount;
    private final int eCoreCount;
    private final int coreCount;
    private int timeQuantum;
    private int time;
    private List<Core> embeddedCore;
    private List<Process> processList;
    private Queue<Process> readyQueue;
    private Queue<Process> outProcessQueue;


    private void initCore(int eCoreCount, int pCoreCount){
        for(int i=0; i<eCoreCount; i++)
            this.embeddedCore.add(new Core(E_CORE_TYPE, 1));
        for(int i=0; i<pCoreCount; i++)
            this.embeddedCore.add(new Core(P_CORE_TYPE, 3));
    }

    public CPU(int eCoreCount, int pCoreCount, List<Process> processList, Scheduler scheduler) {
        this.pCoreCount = pCoreCount;
        this.eCoreCount = eCoreCount;
        coreCount = pCoreCount + eCoreCount;
        this.scheduler = scheduler;
        this.embeddedCore = new ArrayList<Core>();
        this.readyQueue = new LinkedList<Process>();
        this.processList = processList;
        this.outProcessQueue = new LinkedList<Process>();
        processList.sort(Process::compareTo);

        initCore(eCoreCount, pCoreCount);
    }

    public CPU(int eCoreCount, int pCoreCount, List<Process> processList, Scheduler scheduler, int timeQuantum) {
        this(eCoreCount, pCoreCount, processList, scheduler);
        this.timeQuantum = timeQuantum;
        ((RR)scheduler).setTimeQuantum(timeQuantum);
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
    private void changeProcess(Core core, Process changeProcess){
        Process process = core.getAssignedProcess();
        if(process != null) {
            readyQueue.add(process);
            outProcessQueue.add(process);
        }
        core.setAssignedProcess(changeProcess, time);
    }

    private boolean assignProcessEmpty(Process process){
        for(int i = 0; i< coreCount; i++){
            if (!embeddedCore.get(i).isRunning()) {
                embeddedCore.get(i).setAssignedProcess(process, time);
                return true;
            }
        }
        //wait
        return false;
    }

    private boolean assignProcessKind(Process process){
        int recommendedKind = recommendCore(process.getBurstTime());
        switch(recommendedKind) {
            case P_CORE_TYPE:
                for(int i = eCoreCount; i < coreCount; i++){
                    if(!embeddedCore.get(i).isRunning()) {
                        changeProcess(embeddedCore.get(i), process);
                        return true;

                    }
                }
                break;
            case E_CORE_TYPE:
                for(int i = 0; i < eCoreCount; i++) {
                    if (!embeddedCore.get(i).isRunning()) {
                        changeProcess(embeddedCore.get(i), process);
                        return true;
                    }
                }
                break;
            default:
                //throw assignProcessError
                System.out.println("assignProcessError Process id : " + process.getPid());
        }

        //wait
        return false;
    }

    private boolean assignProcessScheduler(Process process){
        for(int i = 0; i < coreCount; i++){
            if(!scheduler.compareProcess(embeddedCore.get(i).getAssignedProcess(), process, time)) {
                changeProcess(embeddedCore.get(i), process);
                return true;

            }
        }
        //wait
        return false;
    }
    /**
     * 프로세스를 전달받아 해당 프로세스를 코어에 등록합니다.
     * 1. CPU.recommendCore() 를 이용해 추천 코어를 설장한다.
     * 2. 추천 코어가 비어있을 경우, 해당 코어에 할당한다. -> true
     * 3. 빈 코어가 있는지 확인한다. 있다면 할당한다 -> true
     * 4. 스케줄러에 의해 선택된 프로세스가 기존 프로세스보다 우선적으로 처리해야한다면 할당한다. -> true
     * 4. 할당 불가능 -> false
     * @param process 할당해야할 프로세스
     * @return true : 할당성공, false : wait
     */
    private boolean assignProcess(Process process){
        if (assignProcessKind(process)){
//            System.out.println("Kind");
            return true;
        }
        else if(assignProcessEmpty(process)){
//            System.out.println("Empty");
            return true;
        }
        else if(assignProcessScheduler(process)){
//            System.out.println("Scheduler");
            return true;
        }
        else{
            //wait
            return false;
        } // scheduler Queue에 넣어주기

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
     * 1. 현재 시간에 도착한 프로세스를 레디큐에 추가한다.(addProcess)
     * 2. 코어중 빈 코어가 있다면, 할당된 코어를 null로 변경한다. (cleanCores)
     * 3. 스케줄러해서 반환된 현재 실행해야할 프로세스리스트를 selectedProcess에 저장한다.
     * 4. 선택된 프로세스중 assignProcess를 이용하여 코어에 할당 성공한 경우 selectedProcess에서 해당 객체를 삭제한다.
     * 4.1 선택되지 않은 프로세스를 schedulerQueue에 임시로 저장한다.
     * 4.2 schedulerQueue + readyQueue + 할당 취소된 프로세스 순서로 readyQueue를 재설정한다.
     * 5. 각 코어를 동작시킨다.
     * 6. 시간을 증가시킨다.
     */
    public void run(boolean debugFlag){
        System.out.println("CPU.run start\n");
        if(debugFlag){
            printCoreStatuses();
            printProcessList();
        }


        while(remainWorking()) {                                                                                        //작업 남음
            int size = 0;
            Queue<Process> selectedProcess = new LinkedList<Process>();                                                 // 스케줄링에 의해 선택될 프로세스 큐
            Queue<Process> schedulerQueue = new LinkedList<Process>();                                                  //스케줄링에 의해 선택되었으나, wait 처리된 (대기중인) 프로세스 큐
            addProcess(time);                                                                                           //프로세스 리스트 -> readyQueue 추가
            if(debugFlag) {
                System.out.println(String.format("=====TIME : %d=====\n", time));
                printProcessList();
                printReadyQueue();
                printCoreStatuses();
            }
            cleanCores(time);                                                                                           //기존 코에어 remainWork이 0인, 작업이 끝난 프로세스 정리
            selectedProcess.addAll(scheduler.running(readyQueue, coreCount));                                           //스케줄러에 의해 선택된 프로세스들을 selectedProcess에 추가
            size = selectedProcess.size();
            for(int i=0; i<size; i++){
                Process process = selectedProcess.poll();
                if(!assignProcess(process)) {
                    schedulerQueue.add(process);
                }
            }
            for(int i=0; i<outProcessQueue.size(); i++){
                Process process = outProcessQueue.poll();
                System.out.println("OUT : " + process);
                assignProcess(process);
            }
//            printCoreStatuses();//running

            schedulerQueue.addAll(readyQueue);
            readyQueue = schedulerQueue;

            for(Core core: embeddedCore){
                core.run();
            }

            time += 1;
        }
        if(debugFlag) {
            System.out.println("CPU.run end\n");
            printCoreStatuses();//end
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
            System.out.println(String.format("Core use : %f", embeddedCore.get(i).getUsingElectricity()));
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
            System.out.println("assigned process : " + embeddedCore.get(i).getAssignedProcess() + "\n");
        }
    }

    public void printProcessList(){
        for(Process process : processList){
            System.out.println(String.format("Process id : %d", process.getPid()));
            System.out.println(String.format("AT : %d, BT : %d", process.getArrivalTime(), process.getBurstTime()));
            System.out.println(String.format("Remain Work : %d", process.getRemainWork()));
            System.out.println(String.format("Running Time : %d\n", process.getRunningTime(time)));
        }
    }

    public void printCoreHistory(){
        for(Core core : embeddedCore)
            System.out.println(core);
    }

    public void printReadyQueue(){
        System.out.println("Ready Queue\n" + readyQueue + "\n");
    }

    public void printEndCoreStatus(){
        for(int i = 0; i < coreCount; i++) {
            Core core = embeddedCore.get(i);
            System.out.println(String.format("#%d Core\nUse Electricity : %.2f W \n",i , core.getUsingElectricity()) + core + "\n");
        }
    }
}

