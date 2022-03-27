package model.schedular;

import model.process.Process;
import java.util.Queue;

public interface Scheduler {
    Process running(Queue<Process> processList, Process assignedProcess);

    boolean produceSig();
}
