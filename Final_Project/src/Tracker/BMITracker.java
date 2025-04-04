package Tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import User.BMIRecord;
import User.GeneralUser;

// 2) Inheritance/Polymorphism
// 5) Lists
// 6) Stacks
public class BMITracker implements ProgressTracker{
	private final Stack<BMIRecord> recordHistory = new Stack<>();

	// Log a new record into the stack
	@Override
	public void logRecord(GeneralUser user) {
		BMIRecord record = new BMIRecord(user);
		recordHistory.push(record);
	}
	
	@Override
	public List<BMIRecord> getRecordHistory() {
		return new ArrayList<>(recordHistory);
	}
}
