package log;

import java.util.Stack;

public class Logger {
	Stack<MeasurementLog> historyLog = new Stack<MeasurementLog>();
	Stack<MeasurementLog> futureLog = new Stack<MeasurementLog>();
	
	
	public void logMeasurment(MeasurementLog logItem) {
		historyLog.push(logItem); 
	}
	
	
	
	public void logMeasurment(int x1OW, int x2OW, int x1OG, int x2OG, int posOW, int posOG, boolean isPosOW, boolean isPosOG) {
		
		historyLog.push(new MeasurementLog(x1OW, x2OW, x1OG, x2OG, posOW, posOG, isPosOW, isPosOG)); 
	}
	
	
	
	public boolean isEmpty() {
		return historyLog.empty();
	}
	
	
	public MeasurementLog getMeasurement() {
		if(!historyLog.empty()) {
			return historyLog.pop();
		}
		else {
			return null;
		}
	}
}
