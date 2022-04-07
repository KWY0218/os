package Test;

import java.util.ArrayList;
import java.util.List;

import model.process.Process;
import model.cpu.CPU;
import model.schedular.FCFS;


public class Main {

    public static void main(String[] args) {


        List<Process> processList = new ArrayList<Process>();
        processList.add(new Process(1, 0,3));
        processList.add(new Process(2, 0,4));
        processList.add(new Process(3, 0,3));


        CPU mainCPU = new CPU(0,1, processList,new FCFS());

        mainCPU.run(false);

        mainCPU.printEndCoreStatus();
        System.out.println("END");
    }
}
