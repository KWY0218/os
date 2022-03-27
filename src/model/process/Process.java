package model.process;

public class Process implements Comparable<Process>{
    private int arrivalTime;
    private int burstTime;
    private int pid;
    private int remainWork;
    private int runningTime;

    public Process(int arrivalTime, int burstTime, int pid) {
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

    public void setRemainWork(int remainWork) {
        this.remainWork = remainWork;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public void worked(int able){
        remainWork = remainWork - able;

        if(remainWork < 0)
            remainWork = 0;
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
