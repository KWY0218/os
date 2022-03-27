package model.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.process.Process;
import model.cpu.CPU;
import model.schedular.FCFS;


public class Main {
    public static void queueTest(Queue<Integer> queue){
        queue.remove(2);
    }
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queueTest(queue);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        List<Process> processList = new ArrayList<Process>();
        processList.add(new Process(0,3, 1));
        processList.add(new Process(1,2, 2));
        processList.add(new Process(3,5, 3));
        processList.add(new Process(2,2, 4));

        CPU mainCPU = new CPU(2,2,4, processList,new FCFS());
        mainCPU.printCoreStatuses();
        mainCPU.printProcessList();
    }
}
