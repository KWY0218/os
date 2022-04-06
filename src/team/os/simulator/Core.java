package team.os.simulator;

public class Core {

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
	
	public void setRemainPower(int mRemainPower) {
		
		remainPower = mRemainPower;
		
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
	
	public void work() {
		
		working = true;
		
	}
	
	public void relax() {
		
		working = false;
		
	}

}

class ECore extends Core {

	public ECore() {

		setPower(1);
		setPowerConsumption(1);

	}

}

class PCore extends Core {

	public PCore() {

		setPower(2);
		setPowerConsumption(3);

	}

}