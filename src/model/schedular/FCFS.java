package model.schedular;
import model.process.Process;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FCFS implements Scheduler{

    @Override
    public Queue<Process> running(Queue<Process> processList, int pCoreCount, int eCoreCount, int time, int timeQuantum) {
//        System.out.println("FCFS Run");
        int counter = pCoreCount + eCoreCount;
        if(counter <= 0)
            return null;
        Queue<Process> resultList = new LinkedList<Process>();
        for(int i=0; (i < counter && !processList.isEmpty()); i++){
            resultList.add(processList.poll());
        }
        return resultList;
    }

    @Override
    public boolean compareProcess(Process runningProcess, Process inProcess, int time) {
        return true;
    }
}
