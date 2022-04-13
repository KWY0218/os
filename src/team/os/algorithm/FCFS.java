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
		
		// 
		History history = new History();

		// 
		Queue<Process> processQueue = new LinkedList<Process>(processList);

		// 
		while(!Simulator.isTerminatedAllProcess(processQueue)) {

			System.out.printf("Total Burst Time: %d\n", totalBurstTime);

			// 
			totalBurstTime++;

			// 
			int processQueueSize = processQueue.size();

			System.out.print("Queue: {");

			for(Process process : processQueue)

				System.out.printf("%s, ", process.getPID());

			System.out.println("}");

			// 
			for(Process process : processList)

				if(process.isTerminated() && process.getWorkingCoreIndex() != -1) {

					coreList.get(process.getWorkingCoreIndex()).relax();
					System.out.printf("Core %d is relaxing.\n", process.getWorkingCoreIndex());
					process.setWorkingCoreIndex(-1);

				}

			// 
			for(int processIndex = 0; processIndex < processQueueSize; processIndex++) {

				// 
				int coreIndex = -1;

				// 
				Process process = processQueue.poll();

				// 
				if((coreIndex = process.getWorkingCoreIndex()) == -1) {

					// 
					coreIndex = Simulator.getRecommendCore(coreList, priorityType);

				} else {

					System.out.printf("Process %s have Core %d.\n", process.getPID(), coreIndex);

				}

				// 
				if(coreIndex == -1) {

					processQueue.offer(process);
					continue;

				}

				// 
				Core core = coreList.get(coreIndex).work();

				// 
				process.setWorkingCoreIndex(coreIndex);
				System.out.printf("Process %s got Core %d.\n", process.getPID(), coreIndex);

				System.out.printf("%s -> %d - %d = ", process.getPID(), process.getRemainBurstTime(), core.getPower());

				// 
				process.decreaseRemainBurstTime(core.getPower());

				System.out.println(process.getRemainBurstTime());

				// 
				if(process.getRemainBurstTime() <= 0) {

					// 
					process.setTurnaroundTime(totalBurstTime - process.getArrivalTime());
					process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
					process.setNomalizedTT((double) process.getTurnaroundTime() / process.getBurstTime());

					// 
					process.terminate();

					System.out.printf("%s is terminated.\n", process.getPID());

				}
				
				// 
				else {

					// 
					processQueue.offer(process);

					System.out.printf("%s is offered.\n", process.getPID());

				}

			}

			// 
			totalPowerConsumption += Simulator.getPowerConsumptionOfCoreList(coreList);

			// 
			totalPowerConsumption += Simulator.getStandbyPowerOfCoreList(coreList);

			System.out.println();

			// 
			history.addPage(processList);

		}

		System.out.printf("Total Burst Time: %d\n", totalBurstTime);
		System.out.printf("Total Power Consumption: %.1f\n\n", totalPowerConsumption);

		for(Process process : processList)

			System.out.println(process);

		// 
		history.setTotalBurstTime(totalBurstTime);
		history.setTotalPowerConsumption(totalPowerConsumption);

		return history;

	}

}
