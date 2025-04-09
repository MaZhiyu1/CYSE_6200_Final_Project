package handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

//2) Inheritance/Polymorphism
//The handler package manages button actions and user input events in the application.

/** Handles BMI calculation when the button is clicked. **/
public class CalculateBMIHandler implements EventHandler<ActionEvent> {

	private TextField heightField;
	private TextField weightField;
	private Label resultLabel;

	/** Initializes fields needed for BMI calculation. **/
	public CalculateBMIHandler(TextField heightField, TextField weightField, Label resultLabel) {
		this.heightField = heightField;
		this.weightField = weightField;
		this.resultLabel = resultLabel;
	}

	/** Performs input validation, calculates BMI, and updates the result label. **/
	@Override
	public void handle(ActionEvent event) {
		// Get input values from text fields
		String heightText = heightField.getText();
		String weightText = weightField.getText();

		// Check if input fields are empty
		if (heightText.isEmpty() || weightText.isEmpty()) {
			AlertUtil.showError("Missing Input", "Please enter height and weight.");
			return;
		}

		try {
			// Convert inputs to numbers
			double height = Double.parseDouble(heightText);
			double weight = Double.parseDouble(weightText);

			// Validate that inputs are positive numbers
			if (height <= 0 || weight <= 0) {
				AlertUtil.showError("Invalid Input", "Height and weight must be positive.");
				return;
			}

			// Calculate BMI using the standard formula
			double bmi = weight / Math.pow(height / 100.0, 2);

			// Display result in the label
			resultLabel.setText("Your BMI : " + String.format("%.2f", bmi));

		} catch (NumberFormatException ex) {
			// Handle invalid number format
			AlertUtil.showError("Invalid Format", "Please enter valid numeric values.");
		}
	}
}
