package team.os.simulator;
import java.util.ArrayList;
import java.util.List;

public class History {

	private List<List<Process>> history;
	private int totalBurstTime = 0;
	private double totalPowerConsumption = 0.0;

	public History() {

		history = new ArrayList<List<Process>>();

	}

	public void addPage(List<Process> mProcessList) {

		List<Process> processList = new ArrayList<Process>();

		for(Process process : mProcessList) {

			Process newProcess = new Process(process.getPID(), process.getArrivalTime(), process.getBurstTime());
			newProcess.setWorkingCoreIndex(process.getWorkingCoreIndex());
			processList.add(newProcess);

		}

		history.add(processList);

	}

	public List<List<Process>> getHistory() {

		return history;

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