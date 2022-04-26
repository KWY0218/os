package model.cpu;

import model.process.Process;

import java.util.ArrayList;
import java.util.List;

class Core {
	
	private final int ABLE_WORK;
    private final int ELECTRICITY;
    
    private Process assignedProcess = null;
    private double usingElectricity = 0;
    private List<Process> history;
    private List<Double> usingElectricityHistory;

    /**
     * ECore(1, 1), PCore(2, 3)
     * @param ableWork
     * @param electricity
     */
    
    public Core(int ableWork, int electricity) {
    	
        ABLE_WORK = ableWork;
        ELECTRICITY = electricity;
        
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
    	
        assignedProcess = process;
        assignedProcess.setWorkStartTime(time); // 초기 설정 확인시 조건문 필요
        assignedProcess.setStartTime(time);
        
    }

    public void setHistory(List<Process> mHistory) {
    	
        history = mHistory;
        
    }

    public List<Double> getUsingElectricityHistory() {
    	
        return usingElectricityHistory;
        
    }

    public void setUsingElectricityHistory(List<Double> mUsingElectricityHistory) {
    	
        usingElectricityHistory = mUsingElectricityHistory;
        
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
