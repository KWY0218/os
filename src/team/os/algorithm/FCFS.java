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
		
		// �����丮�� �����Ѵ�.
		History history = new History();

		// ���μ��� ����Ʈ�� ť�� �����Ѵ�.
		Queue<Process> processQueue = new LinkedList<Process>(processList);

		// ��� ���μ����� ������ �ʾҴٸ�
		while(!Simulator.isTerminatedAllProcess(processQueue)) {

			System.out.printf("Total Burst Time: %d\n", totalBurstTime);

			// ���� �ð��� 1�� �����Ѵ�.
			totalBurstTime++;

			// ť�� ���ϱ� ������ ũ�⸦ �̸� �����Ѵ�.
			int processQueueSize = processQueue.size();

			System.out.print("Queue: {");

			for(Process process : processQueue)

				System.out.printf("%s, ", process.getPID());

			System.out.println("}");

			// ����� ���μ����� �Ҵ�� �ھ���� �޽Ľ�Ų��.
			for(Process process : processList)

				if(process.isTerminated() && process.getWorkingCoreIndex() != -1) {

					coreList.get(process.getWorkingCoreIndex()).relax();
					System.out.printf("Core %d is relaxing.\n", process.getWorkingCoreIndex());
					process.setWorkingCoreIndex(-1);

				}

			// ť�� ����� ��ŭ �ݺ��Ѵ�.
			for(int processIndex = 0; processIndex < processQueueSize; processIndex++) {

				// �ھ� �ε����� �ʱ�ȭ�Ѵ�.
				int coreIndex = -1;

				// ť���� ���μ����� �����Ѵ�.
				Process process = processQueue.poll();

				// ���μ����� �Ҵ�� �ھ ���ٸ�
				if((coreIndex = process.getWorkingCoreIndex()) == -1) {

					// �ھ ��õ�޴´�.
					coreIndex = Simulator.getRecommendCore(coreList, priorityType);

				} else {

					System.out.printf("Process %s have Core %d.\n", process.getPID(), coreIndex);

				}

				// ��� ������ �ھ ������ ���μ����� ť�� �Է��ϰ� ��Ƽ���Ѵ�.
				if(coreIndex == -1) {

					processQueue.offer(process);
					continue;

				}

				// �ھ �Ҵ��Ѵ�.
				Core core = coreList.get(coreIndex).work();

				// ���μ����� �ھ� �ε����� �Է��Ѵ�.
				process.setWorkingCoreIndex(coreIndex);
				System.out.printf("Process %s got Core %d.\n", process.getPID(), coreIndex);

				System.out.printf("%s -> %d - %d = ", process.getPID(), process.getRemainBurstTime(), core.getPower());

				// ���μ����� ���� �۾� �ð��� �ھ��� �Ŀ���ŭ �����Ѵ�.
				process.decreaseRemainBurstTime(core.getPower());

				System.out.println(process.getRemainBurstTime());

				// ���μ����� ���� �۾��ð��� 0 ���϶��
				if(process.getRemainBurstTime() <= 0) {

					// �ð� ������ ����Ѵ�.
					process.setTurnaroundTime(totalBurstTime - process.getArrivalTime());
					process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
					process.setNomalizedTT((double) process.getTurnaroundTime() / process.getBurstTime());

					// ���μ����� �����Ѵ�.
					process.terminate();

					System.out.printf("%s is terminated.\n", process.getPID());

				}
				
				// ���μ����� ���� �۾��ð��� 1 �̻��̶��
				else {

					// ���μ����� ť�� �����Ѵ�.
					processQueue.offer(process);

					System.out.printf("%s is offered.\n", process.getPID());

				}

			}

			// �� �Һ����¿� �Һ������� �����Ѵ�.
			totalPowerConsumption += Simulator.getPowerConsumptionOfCoreList(coreList);

			// �� �Һ����¿� ��������� �����Ѵ�.
			totalPowerConsumption += Simulator.getStandbyPowerOfCoreList(coreList);

			System.out.println();

			// �����丮�� �߰��Ѵ�.
			history.addPage(processList);

		}

		System.out.printf("Total Burst Time: %d\n", totalBurstTime);
		System.out.printf("Total Power Consumption: %.1f\n\n", totalPowerConsumption);

		for(Process process : processList)

			System.out.println(process);

		// �����丮�� �ð� �� ���������� �����Ѵ�.
		history.setTotalBurstTime(totalBurstTime);
		history.setTotalPowerConsumption(totalPowerConsumption);

		return history;

	}

}
