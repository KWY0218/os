package team.os.simulator;

public class Process implements Comparable<Object> {

	// Base
	private String PID = "";
	private int arrivalTime = 0;
	private int burstTime = 0;
	private int waitingTime = 0;
	private int turnaroundTime = 0;
	private double normalizedTT = 0.0;
	
	// Custom
	private boolean terminated = false;
	private int remainBurstTime = 0; 
	private int workingCoreIndex = -1;

	public Process(String mPid, int mArrivalTime, int mBurstTime) {

		PID = mPid;
		arrivalTime = mArrivalTime;
		burstTime = mBurstTime;
		remainBurstTime = mBurstTime;

	}

	public String getPID() {
		
		return PID;
		
	}
	
	public void setPID(String mPID) {
		
		PID = mPID;
		
	}
	
	public int getArrivalTime() {
		
		return arrivalTime;
		
	}
	
	public void setArrivalTime(int mArrivalTime) {
		
		arrivalTime = mArrivalTime;
		
	}
	
	public int getBurstTime() {
		
		return burstTime;
		
	}
	
	public void setBurstTime(int mBurstTime) {
	
		burstTime = mBurstTime;
		
	}
	
	public int getWaitingTime() {
		
		return waitingTime;
		
	}
	
	public void setWaitingTime(int mWaitingTime) {
		
		waitingTime = mWaitingTime;
		
	}
	
	public int getTurnaroundTime() {
		
		return turnaroundTime;
		
	}
	
	public void setTurnaroundTime(int mTurnaroundTime) {
		
		turnaroundTime = mTurnaroundTime;
		
	}
	
	public double getNomalizedTT() {
		
		return normalizedTT;
		
	}
	
	public void setNomalizedTT(double mNomalizedTT) {
		
		normalizedTT = mNomalizedTT;
		
	}
	
	public boolean isTerminated() {
		
		return terminated;
		
	}
	
	public void terminate() {
		
		terminated = true;
		
	}
	
	public int getRemainBurstTime() {
		
		return remainBurstTime;
		
	}
	
	public void decreaseRemainBurstTime(int decreaseTime) {
		
		remainBurstTime -= decreaseTime;
		
		if(remainBurstTime < 0)
			
			remainBurstTime = 0;
		
	}
	
	public int getWorkingCoreIndex() {
		
		return workingCoreIndex;
		
	}
	
	public void setWorkingCoreIndex(int mWorkingCoreIndex) {
		
		workingCoreIndex = mWorkingCoreIndex;
		
	}
	
	@Override
	public int compareTo(Object o) {

		int time = arrivalTime - ((Process) o).arrivalTime;

		if(time > 0) return 1;
		else if(time < 0) return -1;
		else return 0;

	}

	@Override
	public String toString() {

		return String.format("Process:{PID=%s, arrivalTime=%d, burstTime=%d, waitingTime=%d, turnaroundTime=%d, normalizedTT=%.1f, terminated=%b, remainBurstTime=%d, workingCoreIndex=%d}", PID, arrivalTime, burstTime, waitingTime, turnaroundTime, normalizedTT, terminated, remainBurstTime, workingCoreIndex);

	}

}