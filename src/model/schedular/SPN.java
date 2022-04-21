package model.schedular;

import model.process.Process;

import java.util.Queue;

public class SPN implements Scheduler{
    @Override
    public Queue<Process> running(Queue<Process> processList, int pCoreCount, int eCoreCount, int time) {
        return null;
    }

    @Override
    public boolean compareProcess(Process runningProcess, Process inProcess, int time) {
        return false;
    }
}
