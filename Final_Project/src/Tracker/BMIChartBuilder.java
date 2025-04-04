package Tracker;

import javafx.scene.chart.*;
import java.util.List;
import User.BMIRecord;

public class BMIChartBuilder {

    /**
     * Builds a line chart showing BMI trend based on entry ID (record number).
     *
     * @param recordHistory List of BMIRecord objects
     * @return LineChart displaying BMI over time
     */
    public LineChart<Number, Number> bmiChart(List<BMIRecord> recordHistory) {
        // X axis: record ID (1, 2, 3, ...)
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Record ID");

        // Y axis: BMI values
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("BMI");

        // Create the line chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("BMI Progress Chart");

        // Series to store BMI data points
        XYChart.Series<Number, Number> bmiSeries = new XYChart.Series<>();
        bmiSeries.setName("BMI");

        // Add data points to the series
        for (BMIRecord record : recordHistory) {
            int recordId = record.getId();
            double bmi = record.getBmi();
            bmiSeries.getData().add(new XYChart.Data<>(recordId, bmi));
        }

        // Add series to chart
        lineChart.getData().add(bmiSeries);
        return lineChart;
    }
}
