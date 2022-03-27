package team.os.model.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import team.os.model.core.Core;
import team.os.model.core.Ecore;
import team.os.model.core.Pcore;
import team.os.model.process.Process;

public class FCFS implements Scheduler{

	@Override
	public void running(
			Queue<Process> readyQueue, 
			List<Core> coreList,
			Integer pc,
			Integer ec
		) {
		
		int time = 0;
		int pCoreCount = pc;
		int eCoreCount = ec;
		List<List<Process>> resultCoreList = new ArrayList<>();
		for(int i =0;i<pc+ec;i++) resultCoreList.add(new ArrayList<Process>());
		
		while(!readyQueue.isEmpty()) {
			Process next = readyQueue.poll();
			/*
			 *	pcore 와 ecore 가 없으면,
			 *	또는 입력한 AT 보다 시간이 작을 때
			 *	time을 증가한다.
			 *
			 *	core.reduceCounter() 를 통해서 시간이 증가할 때 마다 코어의 counter 를 감소 시켜준다.
			 *	reduceCounter()를 통해 해당 코어의 counter 값이 0이 되면, 해당 코어의 값을 증가시켜서 빈 코어가 생겼다는 것을 나타낸다.
			 */
			while(next.getArriveTime() > time || (pCoreCount == 0 && eCoreCount == 0)) {
				time++;
				for(Core core: coreList) {
					if(core.reduceCounter()) {
						if(core instanceof Ecore) eCoreCount++;
						else pCoreCount++;
					}
				}
			}
			/*
			 *	1. pcore 와 ecore 공간이 다 있을 때
			 *	2. pcore 공간만 있을 때
			 *	3. ecore 공간만 있을 때
			 *	
			 *	상황 1일 땐, BT를 체크해서 4이하이면 ecore, 이상이면 pcore 에 넣는다. 이 때 해당 코어의 counter 를 프로세스의 BT로 설정 하고 해당 코어의 계수를 하나 감소한다.
			 *	상황 2,3 일 땐, 1과 동일하게 한다.
			 *
			 *	pcore 에 counter 를 설정할 땐, BT/2 + 1을 해서 설정한다. 
			 */
			if (pCoreCount != 0 && eCoreCount !=0) {
				next.setRunningTime(time);
				if(next.getBurstTime()<5) {
					Core temp = getECore(coreList);
					temp.setCounter(next.getBurstTime());
					resultCoreList.get(temp.getId()).add(next);
					eCoreCount--;
				}
				else {
					Core temp = getPCore(coreList);
					temp.setCounter(next.getBurstTime()/2+1);
					resultCoreList.get(temp.getId()).add(next);
					pCoreCount--;
				}
			}
			else if(pCoreCount>0) {
				next.setRunningTime(time);
				Core temp = getPCore(coreList);
				temp.setCounter(next.getBurstTime()/2+1);
				resultCoreList.get(temp.getId()).add(next);
				pCoreCount--;
			}
			else if(eCoreCount>0){
				next.setRunningTime(time);
				Core temp = getECore(coreList);
				temp.setCounter(next.getBurstTime());
				resultCoreList.get(temp.getId()).add(next);
				eCoreCount--;
			}
		}

		/*
		 *	결과를 코어 리스트의 순서대로 출력한다. 
		 */
		
		for(List<Process> processList: resultCoreList) {
			for(Process process: processList) process.print();
			System.out.println();
		}
	}
	
	Core getPCore(List<Core> coreList) {
		Core temp = new Pcore(-1);
		for(Core core: coreList) {
			if(core instanceof Pcore && core.getPossible()) {
				temp = core;
				break;
			}
		}
		return temp;
	}
	
	Core getECore(List<Core> coreList) {
		Core temp = new Ecore(-1);
		for(Core core: coreList) {
			if(core instanceof Ecore && core.getPossible()) {
				temp = core;
				break;
			}
		}
		return temp;
	}

}
