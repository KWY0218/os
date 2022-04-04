package model.schedular;

import model.process.Process;

import java.util.List;
import java.util.Queue;

public interface Scheduler {
    Queue<Process> running(Queue<Process> processList, int counter);

    boolean compareProcess(Process runningProcess, Process inProcess);
}
