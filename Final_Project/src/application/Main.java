package application;

import java.util.concurrent.atomic.AtomicReference;

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

import tracker.BMIChartBuilder;
import tracker.BMITracker;
import user.BMIRecord;
import user.User;

import handler.CalculateBMIHandler;
import handler.LogRecordHandler;
import handler.EditRecordHandler;
import handler.DeleteRecordHandler;

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
		Button editRecordButton = new Button("Edit Record");
		Button deleteRecordButton = new Button("Delete Record");

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
		HBox row4 = new HBox(15, logRecordButton, editRecordButton, deleteRecordButton);
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
		calculateBMIButton.setOnAction(new CalculateBMIHandler(heightField, weightField, bmiResultLabel));

		// Log BMI record and print Record in table and chart
		logRecordButton.setOnAction(new LogRecordHandler(
			nameField, ageField, genderBox, heightField, weightField,
			recordTable, layout, placeHolder, bmiTracker
		));
		
		// Updates selected BMI record
		editRecordButton.setOnAction(new EditRecordHandler(recordTable, bmiTracker, layout, placeHolder));

		// Removes selected BMI record
		deleteRecordButton.setOnAction(new DeleteRecordHandler(recordTable, bmiTracker, layout, placeHolder));
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
