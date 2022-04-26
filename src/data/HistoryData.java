package data;

import model.process.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class HistoryData {
    //Core 1~4 정보
    List<List<Process>> coreHistoryList;
    //시간 순서 레디큐 리스트
    List<Queue<Process>> readyQueueList;
    //시간 순서 전력 사용량
    List<List<Double>> usingCoreElectricityList;

    public HistoryData() {
        coreHistoryList = new ArrayList<List<Process>>();
        readyQueueList = new ArrayList<Queue<Process>>();
        usingCoreElectricityList = new ArrayList<List<Double>>();

    }

    public void addCoreInfo(List<Process> processDataList, List<Double> electricityList){
        coreHistoryList.add(processDataList);
        usingCoreElectricityList.add(electricityList);
    }

    public void addReadyQueueHistory(Queue<Process> processDataQueue){
        readyQueueList.add(processDataQueue);
    }
    public List<List<Process>> getCoreHistoryList() {
        return coreHistoryList;
    }

    public void setCoreHistoryList(List<List<Process>> coreHistoryList) {
        this.coreHistoryList = coreHistoryList;
    }

    public List<Queue<Process>> getReadyQueueList() {
        return readyQueueList;
    }

    public void setReadyQueueList(List<Queue<Process>> readyQueueList) {
        this.readyQueueList = readyQueueList;
    }

    public List<List<Double>> getUsingCoreElectricityList() {
        return usingCoreElectricityList;
    }

    public void setUsingCoreElectricityList(List<List<Double>> usingCoreElectricityList) {
        this.usingCoreElectricityList = usingCoreElectricityList;
    }
}
