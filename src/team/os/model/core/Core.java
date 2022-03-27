package team.os.model.core;

public abstract class Core {
	int counter = 0;
	boolean isPossible = true;
	int id;
	
	public void setCounter(int t) {
		counter = t;
		isPossible = false;
	}
	
	public boolean reduceCounter() {
		if(!isPossible) {
			--counter;
			isPossible = 0 == counter;
			if(isPossible) return true;
		}
		return false;
	}
	
	public int getCounter() { return counter; }
	
	public boolean getPossible() { return isPossible; }
	
	public int getId() { return id; }
}
