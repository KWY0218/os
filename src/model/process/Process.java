package model.process;

public class Process implements Comparable<Process>{
    private int arrivalTime;
    private int burstTime;
    private int pid;
    private int time;
    private int remainWork;
    private int endTime;
    private int whatTime;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.pid = pid;
        time = 0;
        remainWork = burstTime;
        whatTime = -1;
    }



    public int getWhatTime() {
        return whatTime;
    }

    public void setWhatTime(int whatTime) {
        this.whatTime = whatTime;
        System.out.println("What time is changed" + this.toString() + this.whatTime);
    }

    public int getRunningTime(int time){
        if(whatTime < 0)
            return 0;
        return time - whatTime;
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
