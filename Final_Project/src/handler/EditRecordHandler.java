package handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tracker.BMIChartBuilder;
import tracker.BMITracker;
import user.BMIRecord;
import java.util.concurrent.atomic.AtomicReference;

//2) Inheritance/Polymorphism

/** Handles editing height and weight of a selected BMI record via a popup dialog. **/
public class EditRecordHandler implements EventHandler<ActionEvent> {

	private TableView<BMIRecord> recordTable;
	private BMITracker bmiTracker;
	private VBox layout;
	private AtomicReference<LineChart<Number, Number>> chartPlaceholder;

	/** Initializes handler with data and UI components. **/
	public EditRecordHandler(TableView<BMIRecord> recordTable,
							 BMITracker bmiTracker,
							 VBox layout,
							 AtomicReference<LineChart<Number, Number>> chartPlaceholder) {
		this.recordTable = recordTable;
		this.bmiTracker = bmiTracker;
		this.layout = layout;
		this.chartPlaceholder = chartPlaceholder;
	}

	/** Displays a dialog to edit height and weight. **/
	@Override
	public void handle(ActionEvent event) {
		BMIRecord selected = recordTable.getSelectionModel().getSelectedItem();

		if (selected == null) {
			AlertUtil.showError("Selection Required", "Please select a record to edit.");
			return;
		}

		// Input fields pre-filled with current values
		TextField heightField = new TextField(String.valueOf(selected.getHeight()));
		TextField weightField = new TextField(String.valueOf(selected.getWeight()));

		// Dialog layout
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(15));
		grid.add(new Label("Height (cm):"), 0, 0);
		grid.add(heightField, 1, 0);
		grid.add(new Label("Weight (kg):"), 0, 1);
		grid.add(weightField, 1, 1);

		Button saveBtn = new Button("Save");
		Button cancelBtn = new Button("Cancel");

		HBox buttonBox = new HBox(10, saveBtn, cancelBtn);
		grid.add(buttonBox, 1, 2);

		Stage dialog = new Stage();
		dialog.setTitle("Edit BMI Record");
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setScene(new Scene(grid));
		dialog.show();

		// Save logic
		saveBtn.setOnAction(e -> {
			try {
				double newHeight = Double.parseDouble(heightField.getText());
				double newWeight = Double.parseDouble(weightField.getText());

				if (newHeight <= 0 || newWeight <= 0) {
					throw new NumberFormatException();
				}

				selected.setHeight(newHeight);
				selected.setWeight(newWeight);
				selected.recalculateBMI();

				recordTable.refresh();

				LineChart<Number, Number> newChart = new BMIChartBuilder().bmiChart(bmiTracker.getRecordHistory());
				layout.getChildren().remove(chartPlaceholder.get());
				layout.getChildren().add(newChart);
				chartPlaceholder.set(newChart);

				dialog.close();
			} catch (NumberFormatException ex) {
				AlertUtil.showError("Invalid Input", "Please enter valid positive numbers.");
			}
		});

		cancelBtn.setOnAction(e -> dialog.close());
	}
}
