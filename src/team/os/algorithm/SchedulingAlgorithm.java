package team.os.algorithm;

import java.util.List;

import team.os.simulator.Core;
import team.os.simulator.History;
import team.os.simulator.PriorityType;
import team.os.simulator.Process;

public interface SchedulingAlgorithm {
	
	public History schedule(List<Process> mProcessList, List<Core> mCoreList, PriorityType mPriorityType);
	
}
