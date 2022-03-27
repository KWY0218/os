package model.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.process.Process;
import model.cpu.CPU;
import model.schedular.FCFS;


public class Main {

    public static void main(String[] args) {
        List<Process> processList = new ArrayList<Process>();
        processList.add(new Process(0,3, 1));
        processList.add(new Process(1,2, 2));
        processList.add(new Process(3,5, 3));
        processList.add(new Process(2,2, 4));
        processList.add(new Process(0,1, 5));
        processList.add(new Process(0,1, 6));
        processList.add(new Process(0,1, 7));
        processList.add(new Process(0,1, 8));

        CPU mainCPU = new CPU(2,2,4, processList,new FCFS());
        mainCPU.printCoreStatuses();
        mainCPU.printProcessList();

        mainCPU.run();

        mainCPU.printCoreHitory();
        System.out.println("END");
    }
}
