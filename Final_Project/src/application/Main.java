package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	private WeightTracker weightTracker = new WeightTracker();

	@Override
	//Adam check
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Weight Track System");

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

		Button logWeightButton = new Button("Log Weight");
		ListView<Double> weightHistoryList = new ListView<>();

		calculateBMIButton.setOnAction(e -> {
			try {
				double height = Double.parseDouble(heightField.getText());
				double weight = Double.parseDouble(weightField.getText());
				User user = new User(nameField.getText(), Integer.parseInt(ageField.getText()), genderBox.getValue(),
						height, weight);
				bmiResultLabel.setText("Your BMI: " + String.format("%.2f", user.calculateBMI()));
			} catch (NumberFormatException ex) {
				bmiResultLabel.setText("Invalid input!");
			}
		});

		logWeightButton.setOnAction(e -> {
			try {
				double weight = Double.parseDouble(weightField.getText());
				weightTracker.logWeight(weight);
				weightHistoryList.getItems().setAll(weightTracker.getWeightHistory());
			} catch (NumberFormatException ex) {
				bmiResultLabel.setText("Invalid weight input!");
			}
		});

		VBox layout = new VBox(10, nameLabel, nameField, ageLabel, ageField, genderLabel, genderBox, heightLabel,
				heightField, weightLabel, weightField, calculateBMIButton, bmiResultLabel, logWeightButton,
				weightHistoryList);
		Scene scene = new Scene(layout, 300, 500);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
