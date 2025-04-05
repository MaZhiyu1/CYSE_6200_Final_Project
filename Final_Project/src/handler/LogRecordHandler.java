package handler;

import tracker.BMIChartBuilder;
import tracker.BMITracker;
import user.BMIRecord;
import user.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.concurrent.atomic.AtomicReference;

//2) Inheritance/Polymorphism

/** Handles the "Add & Print Record" button click to log BMI and update UI. **/
public class LogRecordHandler implements EventHandler<ActionEvent> {

	private TextField nameField;
	private TextField ageField;
	private ChoiceBox<String> genderBox;
	private TextField heightField;
	private TextField weightField;
	private TableView<BMIRecord> recordTable;
	private VBox layout;
	private AtomicReference<LineChart<Number, Number>> chartPlaceholder;
	private BMITracker bmiTracker;

	/** Initializes handler with input fields and components to update. **/
	public LogRecordHandler(
			TextField nameField,
			TextField ageField,
			ChoiceBox<String> genderBox,
			TextField heightField,
			TextField weightField,
			TableView<BMIRecord> recordTable,
			VBox layout,
			AtomicReference<LineChart<Number, Number>> chartPlaceholder,
			BMITracker bmiTracker
	) {
		this.nameField = nameField;
		this.ageField = ageField;
		this.genderBox = genderBox;
		this.heightField = heightField;
		this.weightField = weightField;
		this.recordTable = recordTable;
		this.layout = layout;
		this.chartPlaceholder = chartPlaceholder;
		this.bmiTracker = bmiTracker;
	}

	/** Handles the action: validates input, logs a new record, updates table and chart. **/
	@Override
	public void handle(ActionEvent event) {
		// Read input values
		String name = nameField.getText();
		String ageText = ageField.getText();
		String gender = genderBox.getValue();
		String heightText = heightField.getText();
		String weightText = weightField.getText();

		// Validate completeness
		if (name.isEmpty() || ageText.isEmpty() || gender == null ||
			heightText.isEmpty() || weightText.isEmpty()) {
			AlertUtil.showError("Missing Input", "Please complete all fields.");
			return;
		}

		try {
			// Parse numeric values
			int age = Integer.parseInt(ageText);
			double height = Double.parseDouble(heightText);
			double weight = Double.parseDouble(weightText);

			// Validate positive values
			if (age <= 0 || height <= 0 || weight <= 0) {
				AlertUtil.showError("Invalid Input", "All values must be greater than 0.");
				return;
			}

			// Create a new user and log to tracker
			User user = new User(name, age, gender, height, weight);
			bmiTracker.logRecord(user);

			// Update record table with latest records
			recordTable.getItems().setAll(bmiTracker.getRecordHistory());

			// Generate and replace updated chart
			LineChart<Number, Number> newChart = new BMIChartBuilder().bmiChart(bmiTracker.getRecordHistory());
			layout.getChildren().remove(chartPlaceholder.get());
			layout.getChildren().add(newChart);
			chartPlaceholder.set(newChart);

		} catch (NumberFormatException ex) {
			// Handle invalid number format
			AlertUtil.showError("Invalid Format", "Please enter valid numeric values.");
		}
	}
}
