package model.schedular;
import model.process.Process;

import java.util.List;
import java.util.Queue;

//현재실행중인 프로세스 추가해야될듯
//why 빼야되면 큐에 추가해야댐
public class RoundRobin implements Scheduler {
    @Override
    public List<Process> running(Queue<Process> processList, int counter) {
        return null;
    }

}
