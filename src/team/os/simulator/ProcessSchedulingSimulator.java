package team.os.simulator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class ProcessSchedulingSimulator {

	private static final int MAX_CORE_SIZE = 4;
	private static int TIME_QUANTUM = 4;

	private enum PriorityType {

		POWER, POWER_CONSUMPTION

	}
	
	private static History FCFS(List<Process> processList, List<Core> coreList, PriorityType schedulingType) {

		History history = new History();
		
		double totalPowerConsumption = 0; // need to change bigdecimal
		int totalBurstTime = 0;

		// 프로세스 리스트를 큐로 복사한다.
		Queue<Process> processQueue = new LinkedList<Process>();

		for(Process process : processList)

			processQueue.offer(process);

		// 모든 프로세스가 끝나지 않았다면
		while(!isTerminatedAllProcess(processQueue)) {

			System.out.printf("Total Burst Time: %d\n", totalBurstTime);

			// 수행 시간을 1초 증가한다.
			totalBurstTime++;

			try {Thread.sleep(20); } catch(Exception ex) {}

			// 큐가 변하기 때문에 크기를 미리 저장한다.
			int processQueueSize = processQueue.size();

			System.out.print("Queue: {");
			
			for(Process process : processQueue)
				
				System.out.printf("%s, ", process.getPID());
			
			System.out.println("}");
			
			// 종료된 프로세스에 할당된 코어들을 휴식시킨다.
			for(Process process : processList)
				
				if(process.isTerminated() && process.getWorkingCoreIndex() != -1) {
					
					coreList.get(process.getWorkingCoreIndex()).relax();
					System.out.printf("Core %d is relaxing.\n", process.getWorkingCoreIndex());
					process.setWorkingCoreIndex(-1);
				
				}
				
			// 큐에 저장된 만큼 반복한다.
			for(int processIndex = 0; processIndex < processQueueSize; processIndex++) {
				
				// 코어 인덱스를 초기화한다.
				int coreIndex = -1;
				
				// 큐에서 프로세스를 선택한다.
				Process process = processQueue.poll();
				
				// 프로세스에 할당된 코어가 없다면
				if((coreIndex = process.getWorkingCoreIndex()) == -1) {
					
					// 코어를 추천받는다.
					coreIndex = getRecommendCore(coreList, schedulingType);
					
				} else {
					
					System.out.printf("Process %s have Core %d.\n", process.getPID(), coreIndex);
					
				}
					
				// 사용 가능한 코어가 없으면 프로세스를 큐에 입력하고 컨티뉴한다.
				if(coreIndex == -1) {
				
					processQueue.offer(process);
					continue;
					
				}
				
				// 코어를 할당한다.
				Core core = coreList.get(coreIndex);
				core.work();

				// 프로세스에 코어 인덱스를 입력한다.
				process.setWorkingCoreIndex(coreIndex);
				System.out.printf("Process %s got Core %d.\n", process.getPID(), coreIndex);
				
				System.out.printf("%s -> %d - %d = ", process.getPID(), process.getRemainBurstTime(), core.getPower());
				
				// 프로세스의 남은 작업 시간을 코어의 파워만큼 감소한다.
				process.decreaseRemainBurstTime(core.getPower());

				System.out.println(process.getRemainBurstTime());
				
				// 프로세스의 남은 작업시간이 0 이하라면
				if(process.getRemainBurstTime() <= 0) {

					// 시간 정보를 기록한다.
					process.setTurnaroundTime(totalBurstTime - process.getArrivalTime());
					process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
					process.setNomalizedTT((double) process.getTurnaroundTime() / process.getBurstTime());

					// 프로세스를 종료한다.
					process.terminate();
					
					System.out.printf("%s is terminated.\n", process.getPID());
					
				} else {

					processQueue.offer(process);

					System.out.printf("%s is offered.\n", process.getPID());

				}

			}

			// 총 소비전력에 소비전력을 증가한다.
			totalPowerConsumption += getPowerConsumptionOfCoreList(coreList);

			// 총 소비전력에 대기전력을 증가한다.
			totalPowerConsumption += getStandbyPowerOfCoreList(coreList);

			System.out.println();
			
			// 히스토리를 추가합니다.
			history.addPage(processList);
			
		}
		
		System.out.printf("Total Burst Time: %d\n", totalBurstTime);
		System.out.printf("Total Power Consumption: %.1f\n\n", totalPowerConsumption);

		for(Process process : processList)
			
			System.out.println(process);
		
		history.setTotalBurstTime(totalBurstTime);
		history.setTotalPowerConsumption(totalPowerConsumption);
		
		return history;
		
	}

	private static void RR(List<Process> processList, List<Core> coreList, PriorityType schedulingType) {

		System.out.printf("Time Quantum: %d\n", TIME_QUANTUM);
		
	}

	private static void SPN(List<Process> processList, List<Core> coreList, PriorityType schedulingType) {

	}

	private static void SRTN(List<Process> processList, List<Core> coreList, PriorityType schedulingType) {

	}

	private static void HRRN(List<Process> processList, List<Core> coreList, PriorityType schedulingType) {

	}

	private static void Own(List<Process> processList, List<Core> coreList, PriorityType schedulingType) {

	}

	/**
	 * @param coreList
	 * @return Power consumption of coreList
	 */

	private static int getPowerConsumptionOfCoreList(List<Core> coreList) {

		int powerConsumption = 0;

		for(Core core : coreList)

			if(core.isWorking())

				powerConsumption =+ core.getPowerConsumption();

		return powerConsumption;

	}

	/**
	 * @param coreList
	 * @return Standby power of coreList
	 */

	private static double getStandbyPowerOfCoreList(List<Core> coreList) {

		double standbyPower = 0;

		for(Core core : coreList)

			if(!core.isWorking())

				standbyPower += 0.1;

		return standbyPower;

	}

	/**
	 * @param coreList
	 * @param schedulingType
	 * @return Index of Recommended Core in CoreList
	 */

	private static int getRecommendCore(List<Core> coreList, PriorityType schedulingType) {

		int locationOfFirst = -1;
		int locationOfPCore = -1;
		int locationOfECore = -1;
		int location = -1;

		for(int coreIndex = 0; coreIndex < coreList.size(); coreIndex++) {

			Core core = coreList.get(coreIndex);

			if(!core.isWorking()) {

				if(locationOfFirst == -1)

					locationOfFirst = coreIndex;

				if(core instanceof PCore && locationOfPCore == -1)

					locationOfPCore = coreIndex;

				else if(core instanceof ECore && locationOfECore == -1)

					locationOfECore = coreIndex;

			}

		}

		if(schedulingType.equals(PriorityType.POWER) && locationOfPCore != -1)

			location = locationOfPCore;

		else if(schedulingType.equals(PriorityType.POWER_CONSUMPTION) && locationOfECore != -1)

			location = locationOfECore;

		else

			location = locationOfFirst;

		return location;

	}

	/**
	 * @param processQueue
	 * @return All of process in process queue were terminated
	 */

	private static boolean isTerminatedAllProcess(Queue<Process> processQueue) {

		for(Process process : processQueue)

			if(!process.isTerminated())

				return false;

		return true;

	}

	public static void main(String[] args) {

		// 프로세스 리스트를 생성한다.
		List<Process> processList = new ArrayList<Process>();

		for(int i = 0; i < 15; i++)

			processList.add(new Process(String.format("P%d", processList.size() + 1), new Random().nextInt(15), new Random().nextInt(5) + 1));

		// 프로세스 리스트를 정렬한다.
		processList.sort(Comparator.naturalOrder());

		// 코어 리스트를 생성한다.
		List<Core> coreList = new LinkedList<Core>();

		for(int i = 0; i < MAX_CORE_SIZE; i++)

			// case 2는 disable
			switch(new Random().nextInt(3)) { // 3

			case 0: coreList.add(new ECore()); break;
			case 1: coreList.add(new PCore()); break;

			}

		// 예제 프로세스 및 코어
//		processList.clear();
//		processList.add(new Process("P1", 0, 3));
//		processList.add(new Process("P2", 1, 7));
//		processList.add(new Process("P3", 3, 2));
//		processList.add(new Process("P4", 5, 5));
//		processList.add(new Process("P5", 6, 3));
//		
//		coreList.clear();
//		coreList.add(new PCore());
		
		// 프로세스 및 코어 리스트를 출력한다.
		for(Process process : processList)

			System.out.println(process);

		for(Core core : coreList)

			System.out.println(core);

		System.out.println("-------- FCFS --------");
		History history = FCFS(processList, coreList, PriorityType.POWER);
		
		// 히스토리 테스트
		System.out.println("-------- History --------");
		int bt = 0;
		
		for(List<Process> pl : history.getHistory()) {
			
			System.out.printf("%2d ", bt++);
			
			for(Process p : pl) {
				
				System.out.printf("%s -> %2d ", p.getPID(), p.getWorkingCoreIndex());
				
			}
			
			System.out.println();
			
		}
		
		System.out.println("History.getTotalBurstTime(): " + history.getTotalBurstTime());
		
		System.out.println("-------- RR --------");
		RR(processList, coreList, PriorityType.POWER);
		SPN(processList, coreList, PriorityType.POWER);
		SRTN(processList, coreList, PriorityType.POWER);
		HRRN(processList, coreList, PriorityType.POWER);
		Own(processList, coreList, PriorityType.POWER);

	}

}


