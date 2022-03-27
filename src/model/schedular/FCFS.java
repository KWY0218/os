package model.schedular;
import model.process.Process;
import java.util.Queue;

public class FCFS implements Scheduler{
    @Override
    public Process running(Queue<Process> processList, Process assignedProcess) {
        if(assignedProcess.getRemainWork() != 0)
            return assignedProcess;
        else
            return processList.poll();
    }

    @Override
    public boolean produceSig() {
        return false;
    }
}
