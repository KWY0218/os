package team.os.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import team.os.model.process.Process;
import team.os.model.scheduler.FCFS;
import team.os.model.core.Core;
import team.os.model.core.Ecore;
import team.os.model.core.Pcore;

class FCFSTest {

	@Test
	void testRunning() {
		FCFS fcfs = new FCFS();
		Queue<Process> readyQueue = new LinkedList<Process>();
		List<Core> coreList = new ArrayList<Core>();
		
		readyQueue.add(new Process("p1",0,3));
		readyQueue.add(new Process("p2",1,7));
		readyQueue.add(new Process("p3",3,2));
		readyQueue.add(new Process("p4",5,5));
		readyQueue.add(new Process("p5",6,3));
		
		coreList.add(new Ecore(0));
		coreList.add(new Ecore(1));
		coreList.add(new Ecore(2));
		coreList.add(new Ecore(3));
		/*
		 *	readyQuue, coreList, Pcore 개수, Ecore 개수 
		 */
		fcfs.running(readyQueue, coreList, 0, 4);
	}

}
