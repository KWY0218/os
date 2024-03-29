package model.cpu;

import data.HistoryData;
import model.process.Process;
import model.schedular.Scheduler;

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
	
	private int time = 0;
	
	private List<Process> processList;
	private List<Core> embeddedCore;
	private Queue<Process> readyQueue;
	private HistoryData historyData;

	public CPU(int eCoreCount, int pCoreCount, List<Process> processList, Scheduler scheduler, int mTimeQuantum) {

		this.pCoreCount = pCoreCount;
		this.eCoreCount = eCoreCount;
		coreCount = pCoreCount + eCoreCount;
		this.scheduler = scheduler;
		this.processList = processList;
		
		embeddedCore = new ArrayList<Core>();
		readyQueue = new LinkedList<Process>();
		historyData = new HistoryData();
		
		processList.sort(Process::compareTo);

		initCore(eCoreCount, pCoreCount);

		this.timeQuantum = mTimeQuantum;

	}

	private void initCore(int eCoreCount, int pCoreCount) {

		for(int i = 0; i < eCoreCount; i++)

			this.embeddedCore.add(new Core(E_CORE_TYPE, 1));

		for(int i = 0; i < pCoreCount; i++)

			this.embeddedCore.add(new Core(P_CORE_TYPE, 3));

	}

	/**
	 * 프로세스의 버스트타임을 기준으로 코어를 추천함
	 * burstTime을 입력받고 해당 BurstTime이 4이상일 경우 P_CORE_TYPE = 3
	 * 아닐 경우, E_CORE_TYPE = 1을 반환함
	 * @param burstTime : Process's BurstTime
	 * @return burstTime >= 4 ? P_CORE : E_CORE
	 */

	public int recommendCore(Process process) {

		if(process.getBurstTime() >= 4)

			return P_CORE_TYPE;

		else

			return E_CORE_TYPE;

	}

	private void changeProcess(Core core, Process process) {

		core.emptyProcess();
		core.setAssignedProcess(process, time);

	}

	private boolean assignProcessEmpty(Process process) {

		for(int i = 0; i< coreCount; i++) {

			if (!embeddedCore.get(i).isRunning()) {

				embeddedCore.get(i).setAssignedProcess(process, time);

				return true;

			}

		}

		//wait
		return false;

	}

	/**
	 * @param process
	 * @return
	 */

	private boolean assignProcessKind(Process process) {

		switch(recommendCore(process)) {
		
		case P_CORE_TYPE:
			
			for(int i = eCoreCount; i < coreCount; i++) {
				
				if(!embeddedCore.get(i).isRunning()) {
					
					embeddedCore.get(i).setAssignedProcess(process, time);
					return true;

				}
				
			}
			
			break;
			
		case E_CORE_TYPE:
			
			for(int i = 0; i < eCoreCount; i++) {
				
				if (!embeddedCore.get(i).isRunning()) {
					
					embeddedCore.get(i).setAssignedProcess(process, time);
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

	/**
	 * @param process
	 * @return
	 */

	private boolean assignProcessScheduler(Process process) {

		int properCoreIndex = -1;
		Process properProcess = null;

		if(coreCount <= 1) {
			
			return (!scheduler.compareProcess(embeddedCore.get(0).getAssignedProcess(), process, time));
			
		}
		
		for(int i = 0; i < coreCount; i++) {
			
			Process tempProcess = embeddedCore.get(i).getAssignedProcess();
			
			if(!scheduler.compareProcess(tempProcess, properProcess, time)) {
				
				properCoreIndex = i;
				properProcess = tempProcess;
				
			}
			
		}

		if(properCoreIndex > -1) {
			
			changeProcess(embeddedCore.get(properCoreIndex), process);
			readyQueue.add(properProcess);
			return true;
			
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

	private boolean assignProcess(Process process) {

		if (assignProcessKind(process)) {

			// System.out.println("Kind");
			return true;

		} else if(assignProcessEmpty(process)) {

			// System.out.println("Empty");
			return true;

		} else if(assignProcessScheduler(process)) {

			// System.out.println("Scheduler");
			return true;

		} else {
			//wait
			return false;

		} // scheduler Queue에 넣어주기

	}

	/**
	 * Time정보를 입력받아. 입력받은 ProcessList에서 현재 Time정보에 맞는 ArrivalTime을 레디큐에 추가한다.
	 * @param time : compare to Process.arrivalTime
	 * @return
	 */
	
	public void addProcess(int time) {

		for(Process p:processList) {

			if(p.getArrivalTime() == time)

				readyQueue.add(p);

		}

	}
	
	//    private void outProcessRun() {
	//        while(!outProcessQueue.isEmpty()) {
	//            Process process = outProcessQueue.poll();
	////            System.out.println("OUT : " + process);
	//            if(!assignProcess(process)) {
	//                readyQueue.add(process);
	//            }
	////            System.out.println("outQueue : " +outProcessQueue.size());
	//        }
	//    }
	
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
	
	public void run(boolean debugFlag) {
		
		System.out.println("CPU.run start\n");

		while(isRemainWorking() && embeddedCore.size() != 0) {
			
			int size = 0;
			// this.outProcessQueue = new LinkedList<Process>();
			Queue<Process> selectedProcess = new LinkedList<Process>();
			Queue<Process> schedulerQueue = new LinkedList<Process>();

			addProcess(time);

			if(debugFlag) {
				
				System.out.println(String.format("==========TIME : %d==========\n", time));
				
				printProcessList();
				
			}

			selectedProcess.addAll(scheduler.running(readyQueue, pCoreCount, eCoreCount, time, timeQuantum));
			
			size = selectedProcess.size();

			for(int i=0; i<size; i++) {
				Process process = selectedProcess.poll();
				if(!assignProcess(process)) {
					schedulerQueue.add(process);
				}
			}
			//
			//            outProcessRun();

			//            printCoreStatuses();//running

			schedulerQueue.addAll(readyQueue);
			readyQueue = schedulerQueue;

			if(debugFlag) {
				
				if(time > 10)
					
					break;
				
				printReadyQueue();
				printCoreStatuses();
				printProcessList();
				
			}

			for(Core core : embeddedCore)
				
				core.run();
			
			time += 1;

			cleanCores(time);
			historyData.addReadyQueueHistory(readyQueue);
			
		}
		
		if(debugFlag) {
			
			System.out.println("CPU.run end\n");
			printCoreStatuses();//end
			
		}

		finishRunning();
		
	}

	void finishRunning() {
		
		for(Core core : embeddedCore) {
			
			historyData.addCoreInfo(core.getHistory(), core.getUsingElectricityHistory());
			
		}
		
	}

	/**
	 * 전체 프로세스를 조사하여, 잔여 작업량이 남은 경우 -> true
	 * 아니면 -> false
	 * @return work is remaining
	 */
	
	public boolean isRemainWorking() {
		
		for(Process process : processList)
			
			if(process.getRemainWork() > 0)
				
				return true;
		
		return false;
		
	}

	/**
	 * 각 코어별 할당된 작업이 끝난 코어를 조사한다.
	 * 만약 끝난 코어가 존재한다면, 해당 코어의 할당 프로세스의 endTime을 설정한다.
	 * @param time
	 */
	public void cleanCores(int time) {
		
		for(Core core : embeddedCore) {
			
			Process inProcess = core.getAssignedProcess();
			
			if(inProcess == null || core.getAssignedProcess().getRemainWork() != 0)
				
				continue;
			
			core.getAssignedProcess().setEndTime(time);
			core.emptyProcess();

		}
		
	}

	public HistoryData getHistoryData() {
		
		return historyData;
		
	}

	public void setHistoryData(HistoryData mHistoryData) {
		
		this.historyData = mHistoryData;
		
	}

	/**
	 * print status of core to console
	 */

	public void printCoreStatuses() {

		if (embeddedCore.size() == 0)

			System.out.println("Core is not defined");

		else {

			for (int i = 0; i < coreCount; i++) {

				System.out.println(String.format("Core #%d", i));
				System.out.println(String.format("Core use : %f", embeddedCore.get(i).getUsingElectricity()));
				System.out.print(String.format("Core Type : "));

				switch (embeddedCore.get(i).getCoreType()) {
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

	}

	public void printProcessList() {
 
		for(Process process : processList) {
			
			System.out.println(process);
			System.out.println(String.format("Running Time : %d\n", process.getRunningTime(time)));
			
		}

	}

	public void printCoreHistory() {

		if(embeddedCore.size() == 0)

			System.out.println("Core is not defined");

		else {

			for (Core core : embeddedCore)

				System.out.println(core);

		}

	}

	public void printReadyQueue() {

		String pids = "";

		for(Process process : readyQueue)
		
			pids += process.getPid() + ", ";
			
		System.out.printf("Ready Queue=[%s]\n", pids);

	}

	public void printEndCoreStatus() {

		if (embeddedCore.size() == 0)

			System.out.println("Core is not defined");

		else {

			for (int i = 0; i < coreCount; i++) {

				Core core = embeddedCore.get(i);
				System.out.println(String.format("#%d Core\nUse Electricity : %.2f W \n", i, core.getUsingElectricity()) + core + "\n");

			}

		}

	}

}

