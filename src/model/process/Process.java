package model.process;

public class Process implements Comparable<Process>{
    private int arrivalTime;
    private int burstTime;
    private int pid;
    private int remainWork;
    private int endTime;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.pid = pid;
        remainWork = burstTime;
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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getRemainWork() {
        return remainWork;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public void setRemainWork(int remainWork) {
        this.remainWork = remainWork;
    }

    public void worked(int able){
        remainWork -= able;
        if(remainWork < 0)
            remainWork = 0;
    }

    public void decreaseWork(){
        this.remainWork -= 1;
    }

    @Override
    public int compareTo(Process o) {
        return this.getArrivalTime() - o.getArrivalTime();
    }

    @Override
    public String toString() {
        return "Process{" +
                "pid=" + pid +
                '}';
    }
}
