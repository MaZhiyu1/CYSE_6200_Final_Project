package application;

import java.util.List;
//3) Abstract Classes/Interfaces
public interface ProgressTracker {
	void logWeight(double weight);

	List<Double> getWeightHistory();
}
