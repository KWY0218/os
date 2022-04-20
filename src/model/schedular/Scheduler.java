package model.schedular;

import model.process.Process;

import java.util.List;
import java.util.Queue;

public interface Scheduler {
    /**
     * 실행시 현재 코어 개수만큼 processList에서 실행해야할 프로세스를 큐형태로 반환한다.
     * @param processList 레디큐
     * @return 현재 실행해야할 코어 큐
     */
    Queue<Process> running(Queue<Process> processList, int pCoreCount, int eCoreCount, int time);

    /**
     * 기존에 실행중인 프로세스와 새로 할당할 프로세스를 비교하여, 기존 프로세스를 계속 처리할 경우 true를 할당된 프로세스를 변경시 false를 반환한다.
     * @param runningProcess 기존에 실행중인 프로세스
     * @param inProcess 새로 할당된 프로세스
     * @return true : 기존 프로세스 처리, false : 프로세스 교체
     */
    boolean compareProcess(Process runningProcess, Process inProcess, int time);
}
