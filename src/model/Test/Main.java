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
        processList.add(new Process(1, 0,3));
        processList.add(new Process(2, 1,2));
        processList.add(new Process(3, 3,5));
        processList.add(new Process(4, 2,5));
        processList.add(new Process(5, 0,1));
        processList.add(new Process(6, 0,3));
        processList.add(new Process(7, 0,3));
        processList.add(new Process(8, 0,3));
        processList.add(new Process(9, 0,3));
        processList.add(new Process(10, 0,3));
        CPU mainCPU = new CPU(2,2, processList,new FCFS());

        mainCPU.run(false);

        mainCPU.printEndCoreStatus();
        System.out.println("END");
    }
}
