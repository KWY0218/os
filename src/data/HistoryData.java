package data;

import java.util.List;
import java.util.Queue;

public class HistoryData {
    //Core 1~4 정보
    List<List<ProcessData>> coreHistoryList;

    //시간 순서 레디큐 리스트
    List<Queue<ProcessData>> readyQueueList;
    //시간 순서 전력 사용량
    List<Double> usingElectricityList;
}
