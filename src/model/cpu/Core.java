package model.cpu;

import model.process.Process;

import java.util.ArrayList;
import java.util.List;

class Core {
    private Process assignedProcess;
    private double usingElectricity;
    private final int ABLE_WORK; // E(1) or P(2)
    private final int ELECTRICITY;
    private List<Integer> history;

    public Core(int ableWork, int electricity) {
        this.ABLE_WORK = ableWork;
        assignedProcess = null;
        usingElectricity = 0;
        this.ELECTRICITY = electricity;
        history = new ArrayList<Integer>();
    }

    public double getUsingElectricity() {
        return usingElectricity;
    }

    public boolean isRunning() {
        return assignedProcess != null;
    }

    public void setAssignedProcess(Process process, int time) {
        this.assignedProcess = process;
        this.assignedProcess.setWhatTime(time); // 초기 설정 확인시 조건문 필요
    }

    public void emptyProcess() {
        assignedProcess.setWhatTime(-1);
        assignedProcess = null;
    }

    public Process getAssignedProcess() {
        return assignedProcess;
    }

    public List<Integer> getHistory() {
        return history;
    }

    public void run() {
        if (assignedProcess != null) {
            usingElectricity += ELECTRICITY;
            history.add(assignedProcess.getPid());

            assignedProcess.worked(ABLE_WORK);
        } else {
            usingElectricity += 0.1;
            history.add(-1);
        }

    }

    public int getCoreType() {
        return ABLE_WORK;
    }

    @Override
    public String toString() {
        return history.toString();
    }
}
