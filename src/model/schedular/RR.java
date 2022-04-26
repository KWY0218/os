package model.schedular;

import model.process.Process;

import java.util.LinkedList;
import java.util.Queue;

public class RR implements Scheduler {
	
    private int timeQuantum;

    @Override
    public Queue<Process> running(Queue<Process> processList, int pCoreCount, int eCoreCount, int time, int mTimeQuantum) {
    	
    	// Set TimeQuantum
    	timeQuantum = mTimeQuantum;
    	
//        System.out.println("RR Run");
        Queue<Process> resultList = new LinkedList<Process>();
        int counter = pCoreCount + eCoreCount;
        for(int i=0; (i < counter && !processList.isEmpty()); i++){
            resultList.add(processList.poll());
        }
//        System.out.println(resultList);
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
