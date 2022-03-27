package team.os.model.scheduler;

import java.util.List;
import java.util.Queue;
import team.os.model.process.Process;
import team.os.model.core.Core;

public interface Scheduler {
	void running(Queue<Process> readyQueue,List<Core> coreList,Integer pc,Integer ec);
}
