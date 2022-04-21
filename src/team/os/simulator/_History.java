package team.os.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class _History {

	private List<List<_Process>> processHistory;
	private List<Queue<_Process>> readyQueue;
	private int totalBurstTime = 0;
	private double totalPowerConsumption = 0.0;

	public _History() {

		processHistory = new ArrayList<List<_Process>>();
		readyQueue = new ArrayList<Queue<_Process>>();

	}

	public void addPage(List<_Process> mProcessList, Queue<_Process> mReadyQueue) {

		List<_Process> processList = new ArrayList<_Process>();

		for(_Process process : mProcessList) {

			_Process newProcess = new _Process(process.getPID(), process.getArrivalTime(), process.getBurstTime());
			newProcess.setWorkingCoreIndex(process.getWorkingCoreIndex());
			processList.add(newProcess);

		}

		processHistory.add(processList);

		// queue 복사
		// readyQueue.add(mReadyQueue);
		
	}

	public List<List<_Process>> getHistory() {

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