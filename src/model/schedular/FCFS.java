package model.schedular;
import model.process.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FCFS implements Scheduler{

    @Override
    public List<Process> running(Queue<Process> processList, int counter) {
        System.out.println("FCFS Run");
        List<Process> resultList = new ArrayList<>();
        for(int i=0; (i < counter && !processList.isEmpty()); i++){
            resultList.add(processList.poll());
        }
        return resultList;
    }
}
