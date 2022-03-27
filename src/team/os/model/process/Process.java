package team.os.model.process;

public class Process {
	String name;
	int arriveTime;
	int burstTime;
	int remain = 0;
	int runningTime = 0;
	
	public Process(String name, int aT, int bT) {
		this.name = name;
		this.arriveTime = aT;
		this.burstTime = bT;
	}
	
	public int getArriveTime() { return arriveTime; }
	public int getBurstTime() { return burstTime; }
	public int getRunningTime() { return runningTime; }
	public String getName() { return name; }
	
	public void setRunningTime(int rT) { runningTime = rT; }
	public void print() {
		System.out.print(" name: "+name+", arriveTime: "+arriveTime+", burstTime: "+burstTime+ ", runningTime: "+runningTime+", remain: "+remain);
	}
}