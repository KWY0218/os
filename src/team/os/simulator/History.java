package team.os.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class History {

	private List<List<Process>> processHistory;
	private List<Queue<Process>> readyQueue;
	private int totalBurstTime = 0;
	private double totalPowerConsumption = 0.0;

	public History() {

		processHistory = new ArrayList<List<Process>>();
		readyQueue = new ArrayList<Queue<Process>>();

	}

	public void addPage(List<Process> mProcessList, Queue<Process> mReadyQueue) {

		List<Process> processList = new ArrayList<Process>();

		for(Process process : mProcessList) {

			Process newProcess = new Process(process.getPID(), process.getArrivalTime(), process.getBurstTime());
			newProcess.setWorkingCoreIndex(process.getWorkingCoreIndex());
			processList.add(newProcess);

		}

		processHistory.add(processList);

		// queue 복사
		// readyQueue.add(mReadyQueue);
		
	}

	public List<List<Process>> getHistory() {

		return processHistory;

	}

	public int getTotalBurstTime() {
		
		return totalBurstTime;

	}

	public void setTotalBurstTime(int mTotalBurstTime) {

		totalBurstTime = mTotalBurstTime;

	}

	public double getTotalPoewrConsumption() {
		
		return totalPowerConsumption;
		
	}
	
	public void setTotalPowerConsumption(double mTotalPowerConsumption) {

		totalPowerConsumption = mTotalPowerConsumption;

	}

}