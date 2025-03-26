package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//Implementation of ProgressTracker using Stack
class WeightTracker implements ProgressTracker {
	private final Stack<Double> weightHistory = new Stack<>();

	@Override
	public void logWeight(double weight) {
		weightHistory.push(weight);
	}

	@Override
	public List<Double> getWeightHistory() {
		return new ArrayList<>(weightHistory);
	}
}