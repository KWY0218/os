package team.os.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import team.os.simulator.Core;
import team.os.simulator.History;
import team.os.simulator.PriorityType;
import team.os.simulator.Process;
import team.os.simulator.Simulator;

public class FCFS implements SchedulingAlgorithm {

	@Override
	public History schedule(List<Process> processList, List<Core> coreList, PriorityType priorityType) {

		double totalPowerConsumption = 0;
		int totalBurstTime = 0;
		
		// 히스토리를 생성한다.
		History history = new History();

		// 프로세스 리스트를 큐로 복사한다.
		Queue<Process> processQueue = new LinkedList<Process>(processList);

		// 모든 프로세스가 끝나지 않았다면
		while(!Simulator.isTerminatedAllProcess(processQueue)) {

			System.out.printf("Total Burst Time: %d\n", totalBurstTime);

			// 수행 시간을 1초 증가한다.
			totalBurstTime++;

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
					coreIndex = Simulator.getRecommendCore(coreList, priorityType);

				} else {

					System.out.printf("Process %s have Core %d.\n", process.getPID(), coreIndex);

				}

				// 사용 가능한 코어가 없으면 프로세스를 큐에 입력하고 컨티뉴한다.
				if(coreIndex == -1) {

					processQueue.offer(process);
					continue;

				}

				// 코어를 할당한다.
				Core core = coreList.get(coreIndex).work();

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

				}
				
				// 프로세스의 남은 작업시간이 1 이상이라면
				else {

					// 프로세스를 큐에 삽입한다.
					processQueue.offer(process);

					System.out.printf("%s is offered.\n", process.getPID());

				}

			}

			// 총 소비전력에 소비전력을 증가한다.
			totalPowerConsumption += Simulator.getPowerConsumptionOfCoreList(coreList);

			// 총 소비전력에 대기전력을 증가한다.
			totalPowerConsumption += Simulator.getStandbyPowerOfCoreList(coreList);

			System.out.println();

			// 히스토리를 추가한다.
			history.addPage(processList);

		}

		System.out.printf("Total Burst Time: %d\n", totalBurstTime);
		System.out.printf("Total Power Consumption: %.1f\n\n", totalPowerConsumption);

		for(Process process : processList)

			System.out.println(process);

		// 히스토리에 시간 및 전력정보를 대입한다.
		history.setTotalBurstTime(totalBurstTime);
		history.setTotalPowerConsumption(totalPowerConsumption);

		return history;

	}

}
