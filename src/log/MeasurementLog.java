package log;

public final class MeasurementLog {
	private final int x1OW;
	private final int x2OW;
	private final int x1OG;
	private final int x2OG;
	private final int posOW;
	private final int posOG;
	private final boolean isPosOWSelected;
	private final boolean isPosOGSelected;
	
	
	
	public MeasurementLog(int x1OW, int x2OW, int x1OG, int x2OG, int posOW, int posOG, boolean isPosOW, boolean isPosOG) {
		this.x1OW = x1OW;
		this.x2OW = x2OW;
		this.x1OG = x1OG;
		this.x2OG = x2OG;
		this.posOW = posOW;
		this.posOG = posOG;
		this.isPosOWSelected = isPosOW;
		this.isPosOGSelected = isPosOG;
		
	}
	
	
	public int getX1OW() {
		return x1OW;
	}
	
	
	public int getX2OW() {
		return x2OW;
	}
	
	
	public int getX1OG() {
		return x1OG;
	}
	
	
	public int getX2OG() {
		return x2OG;
	}
	
	
	public int getPosOW() {
		return posOW;
	}
	
	
	public int getPosOG() {
		return posOG;
	}
	
	
	public boolean isPosOWSelected() {
		return isPosOWSelected;
	}
	
	
	public boolean isPosOGSelected() {
		return isPosOGSelected;
	}
}
