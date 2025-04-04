package Tracker;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

public class WeightProgressTracker {

    public LineChart<Number, Number> weightChart(List<Double> weightHistory) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Entry");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Weight (kg)");

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Weight Progress Chart");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Weight Over Time");

        for (int i = 0; i < weightHistory.size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, weightHistory.get(i)));
        }

        lineChart.getData().add(series);
        return lineChart;
    }
}

