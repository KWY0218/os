package model.schedular;

import model.process.Process;

import java.util.LinkedList;
import java.util.Queue;

public class RR implements Scheduler{
    int timeQuantum;

    public int getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    @Override
    public Queue<Process> running(Queue<Process> processList, int counter) {
        System.out.println("RR Run");
        Queue<Process> resultList = new LinkedList<Process>();
        for(int i=0; (i < counter && !processList.isEmpty()); i++){
            resultList.add(processList.poll());
        }
        System.out.println(resultList);
        return resultList;
    }

    @Override
    public boolean compareProcess(Process runningProcess, Process inProcess, int time) {
        int temp = runningProcess.getRunningTime(time);
//        System.out.println(time);
//        System.out.println(temp);
        if(temp % timeQuantum == 0 && temp != 0)
            return false;
        else
            return true;
    }
}
