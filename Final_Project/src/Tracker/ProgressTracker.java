package Tracker;

import java.util.List;

import User.BMIRecord;
import User.GeneralUser;

//3) Abstract Classes/Interfaces
public interface ProgressTracker {
	
	// Log a full record (height and weight)
	public void logRecord(GeneralUser user);

	// Return full record history
	List<BMIRecord> getRecordHistory();
}
