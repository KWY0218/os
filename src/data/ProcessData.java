package data;

import model.process.Process;

public class ProcessData {
    private String PID = "";
    private int arrivalTime = 0;
    private int burstTime = 0;
    private int waitingTime = 0;
    private int turnaroundTime = 0;
    private double normalizedTT = 0.0;

    public ProcessData() {}

    public void setProcessData(String PID, int arrivalTime, int burstTime, int waitingTime, int turnaroundTime, double normalizedTT) {
        this.PID = PID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.waitingTime = waitingTime;
        this.turnaroundTime = turnaroundTime;
        this.normalizedTT = normalizedTT;
    }


    public ProcessData getProcessData(){
        return this;
    }

    public void setProcessData(ProcessData processData){
        this.PID = this.getPID();
        this.arrivalTime = this.getArrivalTime();
        this.burstTime = this.getBurstTime();
        this.waitingTime = this.getWaitingTime();
        this.turnaroundTime = this.getTurnaroundTime();
        this.normalizedTT = this.getNormalizedTT();
    }
    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public double getNormalizedTT() {
        return normalizedTT;
    }

    public void setNormalizedTT(double normalizedTT) {
        this.normalizedTT = normalizedTT;
    }

    @Override
    public String toString() {
        return "ProcessData{" +
                "PID='" + PID + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", waitingTime=" + waitingTime +
                ", turnaroundTime=" + turnaroundTime +
                ", normalizedTT=" + normalizedTT +
                '}';
    }
}
