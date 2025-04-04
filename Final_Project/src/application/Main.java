package application;

import java.util.concurrent.atomic.AtomicReference;
import Tracker.BMIChartBuilder;
import Tracker.BMITracker;
import User.BMIRecord;
import User.User;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	private BMITracker bmiTracker = new BMITracker();

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("BMI Weight Tracker");

		// User Profile Inputs
		Label nameLabel = new Label("Name:");
		TextField nameField = new TextField();

		Label ageLabel = new Label("Age:");
		TextField ageField = new TextField();

		Label genderLabel = new Label("Gender:");
		ChoiceBox<String> genderBox = new ChoiceBox<>();
		genderBox.getItems().addAll("Male", "Female", "Other");

		Label heightLabel = new Label("Height (cm):");
		TextField heightField = new TextField();

		Label weightLabel = new Label("Weight (kg):");
		TextField weightField = new TextField();

		Button calculateBMIButton = new Button("Calculate BMI");
		Label bmiResultLabel = new Label();
		bmiResultLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #0066cc; -fx-font-weight: bold;");

		Button logRecordButton = new Button("Add & Print Record");

		// Table to display BMI records
		TableView<BMIRecord> recordTable = new TableView<>();

		// Define table columns
		TableColumn<BMIRecord, String> idCol = new TableColumn<>("Record id");
		idCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

		TableColumn<BMIRecord, String> heightCol = new TableColumn<>("Height (cm)");
		heightCol.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.1f", data.getValue().getHeight())));

		TableColumn<BMIRecord, String> weightCol = new TableColumn<>("Weight (kg)");
		weightCol.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.1f", data.getValue().getWeight())));

		TableColumn<BMIRecord, String> bmiCol = new TableColumn<>("BMI");
		bmiCol.setCellValueFactory(data -> new SimpleStringProperty(String.format("%.2f", data.getValue().getBmi())));

		recordTable.getColumns().addAll(idCol, heightCol, weightCol, bmiCol);	
		recordTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Group Name, Age, Gender into one row
		HBox row1 = new HBox(40, nameLabel, nameField, ageLabel, ageField, genderLabel, genderBox);
		row1.setPadding(new Insets(5, 0, 5, 0));

		// Group Height, Weight into one row
		HBox row2 = new HBox(30, heightLabel, heightField, weightLabel, weightField);
		row2.setPadding(new Insets(5, 0, 5, 0));

		// Group Calculate BMI
		HBox row3 = new HBox(50, calculateBMIButton, bmiResultLabel);
		row3.setPadding(new Insets(5, 0, 5, 0));

		// Group Log Button
		HBox row4 = new HBox(15, logRecordButton);
		row4.setPadding(new Insets(5, 0, 5, 0));

		// Layout + empty placeholder for replacement of weight tracker chart
		VBox layout = new VBox(15, row1, row2, row3, row4, recordTable);
		layout.setPadding(new Insets(20));

		Scene scene = new Scene(layout, 1000, 1000);

		// Placeholder chart reference
		AtomicReference<LineChart<Number, Number>> placeHolder = new AtomicReference<>(
			new LineChart<>(new NumberAxis(), new NumberAxis())
		);
		layout.getChildren().add(placeHolder.get());

		// BMI Calculation Logic
		calculateBMIButton.setOnAction(e -> {
			String heightText = heightField.getText();
			String weightText = weightField.getText();

			if (heightText.isEmpty() || weightText.isEmpty()) {
				showAlert(Alert.AlertType.ERROR, "Missing Input", "Please enter height and weight.");
				bmiResultLabel.setText("Missing input!");
				return;
			}

			try {
				double height = Double.parseDouble(heightText);
				double weight = Double.parseDouble(weightText);

				if (height <= 0 || weight <= 0) {
					showAlert(Alert.AlertType.ERROR, "Invalid Input", "Height and weight must be positive.");
					bmiResultLabel.setText("Invalid input!");
					return;
				}

				double bmi = weight / Math.pow(height / 100.0, 2);
				bmiResultLabel.setText("Your BMI: " + String.format("%.2f", bmi));

			} catch (NumberFormatException ex) {
				showAlert(Alert.AlertType.ERROR, "Invalid Format", "Please enter valid numeric values.");
				bmiResultLabel.setText("Invalid format!");
			}
		});

		// Log BMI record and print Record in table and chart
		logRecordButton.setOnAction(e -> {
			try {
				String name = nameField.getText();
				String ageText = ageField.getText();
				String gender = genderBox.getValue();
				String heightText = heightField.getText();
				String weightText = weightField.getText();

				// Check completeness
				if (name.isEmpty() || ageText.isEmpty() || gender == null || heightText.isEmpty() || weightText.isEmpty()) {
					showAlert(Alert.AlertType.ERROR, "Missing Input", "Please complete all fields.");
					return;
				}

				int age = Integer.parseInt(ageText);
				double height = Double.parseDouble(heightText);
				double weight = Double.parseDouble(weightText);

				if (age <= 0 || height <= 0 || weight <= 0) {
					showAlert(Alert.AlertType.ERROR, "Invalid Input", "All values must be greater than 0.");
					return;
				}

				// Add to record tracker
				User user = new User(name, age, gender, height, weight);
				bmiTracker.logRecord(user);

				// Update table and chart
				recordTable.getItems().setAll(bmiTracker.getRecordHistory());

				LineChart<Number, Number> update = new BMIChartBuilder().bmiChart(bmiTracker.getRecordHistory());
				layout.getChildren().remove(placeHolder.get());
				layout.getChildren().add(update);
				placeHolder.set(update);

			} catch (NumberFormatException ex) {
				showAlert(Alert.AlertType.ERROR, "Format Error", "Please enter valid numbers.");
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/** Utility method to display an alert dialog with a given type, title, and message. **/
	private void showAlert(Alert.AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
