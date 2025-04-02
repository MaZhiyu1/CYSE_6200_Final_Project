package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//4) Generics/Collections/Iterators
class WeightTracker implements ProgressTracker {
	private final Stack<Double> weightHistory = new Stack<>(); //logs weight history

	@Override
	public void logWeight(double weight) {
		weightHistory.push(weight); //pushes weight to stack
	}

	@Override
	public List<Double> getWeightHistory() {
		return new ArrayList<>(weightHistory);
	}
}