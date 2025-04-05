package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import user.BMIRecord;
import user.GeneralUser;

// 2) Inheritance/Polymorphism
// 5) Lists
// 6) Stacks
public class BMITracker implements ProgressTracker<BMIRecord> {
	private final Stack<BMIRecord> recordHistory = new Stack<>();

	// Log a new record into the stack
	@Override
	public void logRecord(GeneralUser user) {
		BMIRecord record = new BMIRecord(user);
		recordHistory.push(record);
	}
	
	//Remove a record from the stack
	public void removeRecord(BMIRecord record) {
		recordHistory.remove(record);
	}
	
	@Override
	public List<BMIRecord> getRecordHistory() {
		return new ArrayList<>(recordHistory);
	}
}
