package team.os.simulator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class _Simulator {

	public static final int MAX_CORE_SIZE = 4;
	public static int TIME_QUANTUM = 4;

	/**
	 * @param coreList
	 * @return Power consumption of coreList
	 */

	public static int getPowerConsumptionOfCoreList(List<_Core> coreList) {

		int powerConsumption = 0;

		for(_Core core : coreList)

			if(core.isWorking())

				powerConsumption =+ core.getPowerConsumption();

		return powerConsumption;

	}

	/**
	 * @param coreList
	 * @return Standby power of coreList
	 */

	public static double getStandbyPowerOfCoreList(List<_Core> coreList) {

		double standbyPower = 0;

		for(_Core core : coreList)

			if(!core.isWorking())

				standbyPower += 0.1;

		return standbyPower;

	}

	/**
	 * @param coreList
	 * @param schedulingType
	 * @return Index of Recommended Core in CoreList
	 */

	public static int getRecommendCore(List<_Core> coreList, _PriorityType schedulingType) {

		int locationOfFirst = -1;
		int locationOfPCore = -1;
		int locationOfECore = -1;
		int location = -1;

		for(int coreIndex = 0; coreIndex < coreList.size(); coreIndex++) {

			_Core core = coreList.get(coreIndex);

			// 코어가 사용중이 아니라면
			if(!core.isWorking()) {

				// 먼저 발견된 코어의 위치를 저장한다.
				if(locationOfFirst == -1)

					locationOfFirst = coreIndex;

				// 
				if(core instanceof PCore && locationOfPCore == -1)

					locationOfPCore = coreIndex;

				else if(core instanceof ECore && locationOfECore == -1)

					locationOfECore = coreIndex;

			}

		}

		if(schedulingType.equals(_PriorityType.POWER) && locationOfPCore != -1)

			location = locationOfPCore;

		else if(schedulingType.equals(_PriorityType.POWER_CONSUMPTION) && locationOfECore != -1)

			location = locationOfECore;

		else

			location = locationOfFirst;

		return location;

	}

	/**
	 * @param processQueue
	 * @return All of process in process queue were terminated
	 */

	public static boolean isTerminatedAllProcess(Queue<_Process> processQueue) {

		for(_Process process : processQueue)

			if(!process.isTerminated())

				return false;

		return true;

	}

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		// 프로세스 리스트를 생성한다.
		List<_Process> processList = new ArrayList<_Process>();

		for(int i = 0; i < 15; i++)

			processList.add(new _Process(String.format("P%d", processList.size() + 1), new Random().nextInt(15), new Random().nextInt(5) + 1));

		// 프로세스 리스트를 정렬한다.
		processList.sort(Comparator.naturalOrder());

		// 코어 리스트를 생성한다.
		List<_Core> coreList = new LinkedList<_Core>();

		for(int i = 0; i < MAX_CORE_SIZE; i++)

			// case 2는 disable
			switch(new Random().nextInt(3)) { // 3

			case 0: coreList.add(new ECore()); break;
			case 1: coreList.add(new PCore()); break;

			}

		// 예제 프로세스 및 코어
		//		processList.clear();
		//		processList.add(new Process("P1", 0, 3));
		//		processList.add(new Process("P2", 1, 7));
		//		processList.add(new Process("P3", 3, 2));
		//		processList.add(new Process("P4", 5, 5));
		//		processList.add(new Process("P5", 6, 3));
		//		
		//		coreList.clear();
		//		coreList.add(new PCore());

		// 프로세스 및 코어 리스트를 출력한다.
		for(_Process process : processList)

			System.out.println(process);

		for(_Core core : coreList)

			System.out.println(core);

		System.out.println("-------- FCFS --------");
		/*History history = new FCFS().schedule(processList, coreList, PriorityType.POWER);

		// 히스토리 테스트
		System.out.println("-------- History --------");

		System.out.print("       ");

		for(int historyIndex = 0; historyIndex < history.getHistory().size(); historyIndex++)

			System.out.printf("%4d ", historyIndex + 1);

		System.out.println();

		for(int coreIndex = 0; coreIndex < coreList.size(); coreIndex++) {

			System.out.printf("Core%2d", coreIndex + 1);

			for(List<Process> pl : history.getHistory())

				for(Process p : pl)

					if(p.getWorkingCoreIndex() == coreIndex)

						System.out.printf("%5s", p.getPID());
			
			System.out.println();

		}


		System.out.println("History.getTotalBurstTime(): " + history.getTotalBurstTime());*/

	}

}


