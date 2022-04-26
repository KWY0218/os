package Test;

import java.util.ArrayList;
import java.util.List;

import model.process.Process;
import model.cpu.CPU;
import model.schedular.FCFS;
import model.schedular.RR;


public class Main {

    public static void main(String[] args) {


        List<Process> processList1 = new ArrayList<Process>();
        processList1.add(new Process("P1", 0,3));
        processList1.add(new Process("P2", 1,7));
        processList1.add(new Process("P3", 3,2));
        processList1.add(new Process("P4", 5,5));
        processList1.add(new Process("P5", 6,3));



        CPU mainCPU = new CPU(1,0, processList1,new FCFS(),-1);
        mainCPU.run(false);
        mainCPU.printEndCoreStatus();

        for(Process process:processList1) {
            System.out.println(process);
        }


        List<Process> processList2 = new ArrayList<Process>();
        processList2.add(new Process("1", 0,3));
        processList2.add(new Process("2", 0,4));
        processList2.add(new Process("3", 0,3));
        processList2.add(new Process("4", 0,3));
        processList2.add(new Process("5", 0,4));
        processList2.add(new Process("6", 0,3));
        processList2.add(new Process("7", 0,3));

        CPU rrCPU = new CPU(2, 2, processList2, new RR(),2);
        rrCPU.run(true);
        rrCPU.printEndCoreStatus();

        System.out.println("END");
        for(Process process:processList2) {
            System.out.println(process);
        }
    }
}
