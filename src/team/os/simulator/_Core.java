package team.os.simulator;

public class _Core {

	private int power = 0;
	private int powerConsumption = 0;
	private int remainPower = 0;
	private boolean working = false;
	
	@Override
	public String toString() {
		
		return String.format("%s:{power=%d, powerConsumption=%d, working=%b}", getClass().getName(), power, powerConsumption, working);
		
	}
	
	public int getPower() { 
		
		return power;
		
	}
	
	public void setPower(int mPower) {
		
		power = mPower;
		
	}
	
	public int getRemainPower() {
		
		return remainPower;
		
	}
	
	public int addRemainPower(int value) {
		
		return remainPower += value;
		
	}
	
	public int subRemainPower(int value) {
		
		return remainPower -= value;
		
	}
	
	public int getPowerConsumption() {
		
		return powerConsumption;
		
	}
	
	public void setPowerConsumption(int mPowerConsumption) {
		
		powerConsumption = mPowerConsumption;
		
	}
	
	public boolean isWorking() {
		
		return working;
		
	}
	
	public _Core work() {
		
		working = true;
		
		return this;
		
	}
	
	public void relax() {
		
		working = false;
		
	}

}

class ECore extends _Core {

	public ECore() {

		setPower(1);
		setPowerConsumption(1);

	}

}

class PCore extends _Core {

	public PCore() {

		setPower(2);
		setPowerConsumption(3);

	}

}