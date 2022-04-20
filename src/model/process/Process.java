package model.process;

import data.ProcessData;

public class Process implements Comparable<Process>{
    private final int arrivalTime;
    private final int burstTime;
    private final String pid;
    private int time;
    private int remainWork;
    private int endTime;
    private int startTime;
    private int workStartTime;

    public Process(String pid, int arrivalTime, int burstTime) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.pid = pid;
        time = 0;
        remainWork = burstTime;
        endTime = -1;
        startTime = -1;
        workStartTime = -1;
    }



    public int getworkStartTime() {
        return workStartTime;
    }


    public void setworkStartTime(int workStartTime) {
        this.workStartTime = workStartTime;
//        System.out.println("What time is changed" + this.toString() + this.workStartTime);
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        if(this.startTime == -1)
            this.startTime = startTime;
    }

    public int getRunningTime(int time){
        if(workStartTime < 0)
            return 0;
        return time - workStartTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }


    public String getPid() {
        return pid;
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

    public ProcessData getProcessData(){
        ProcessData processData = new ProcessData();
        int waitTime = startTime - arrivalTime;
        int turnAroundTime = endTime - arrivalTime;
        double normalizedTT = (double)  turnAroundTime / burstTime;

        processData.setProcessData(pid,arrivalTime,burstTime,waitTime,turnAroundTime,normalizedTT);

        return processData;
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
