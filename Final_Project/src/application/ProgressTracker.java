package application;

import java.util.List;

public interface ProgressTracker {
	void logWeight(double weight);

	List<Double> getWeightHistory();
}
