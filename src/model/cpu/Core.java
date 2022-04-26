package model.cpu;

import model.process.Process;

import java.util.ArrayList;
import java.util.List;

class Core {
    private Process assignedProcess;
    private double usingElectricity;
    private final int ABLE_WORK; // E(1) or P(2)
    private final int ELECTRICITY;
    private List<Process> history;
    private List<Double> usingElectricityHistory;

    public Core(int ableWork, int electricity) {
        this.ABLE_WORK = ableWork;
        assignedProcess = null;
        usingElectricity = 0;
        this.ELECTRICITY = electricity;
        history = new ArrayList<Process>();
        usingElectricityHistory = new ArrayList<Double>();
    }

    public double getUsingElectricity() {
        return usingElectricity;
    }

    public boolean isRunning() {
        return assignedProcess != null;
    }

    public void setAssignedProcess(Process process, int time) {
        this.assignedProcess = process;
        this.assignedProcess.setWorkStartTime(time); // 초기 설정 확인시 조건문 필요
        this.assignedProcess.setStartTime(time);
    }

    public void setHistory(List<Process> history) {
        this.history = history;
    }

    public List<Double> getUsingElectricityHistory() {
        return usingElectricityHistory;
    }

    public void setUsingElectricityHistory(List<Double> usingElectricityHistory) {
        this.usingElectricityHistory = usingElectricityHistory;
    }

    public void emptyProcess() {
        assignedProcess.setWorkStartTime(-1);
        assignedProcess = null;
    }

    public Process getAssignedProcess() {
        return assignedProcess;
    }

    public List<Process> getHistory() {
        return history;
    }

    public void run() {
        if (assignedProcess != null) {
            usingElectricity += ELECTRICITY;
            history.add(assignedProcess);

            assignedProcess.worked(ABLE_WORK);
        } else {
            usingElectricity += 0.1;
            history.add(null);
        }
        usingElectricityHistory.add(usingElectricity);
    }

    public int getCoreType() {
        return ABLE_WORK;
    }

    @Override
    public String toString() {
        return history.toString();
    }
}
