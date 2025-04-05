package handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import tracker.BMIChartBuilder;
import tracker.BMITracker;
import user.BMIRecord;
import java.util.concurrent.atomic.AtomicReference;

//2) Inheritance/Polymorphism

/** Handles deleting the selected BMI record from tracker and UI. **/
public class DeleteRecordHandler implements EventHandler<ActionEvent> {

	private TableView<BMIRecord> recordTable;
	private BMITracker bmiTracker;
	private VBox layout;
	private AtomicReference<LineChart<Number, Number>> chartPlaceholder;

	/** Constructor to initialize components used in deletion. **/
	public DeleteRecordHandler(TableView<BMIRecord> recordTable,
							   BMITracker bmiTracker,
							   VBox layout,
							   AtomicReference<LineChart<Number, Number>> chartPlaceholder) {
		this.recordTable = recordTable;
		this.bmiTracker = bmiTracker;
		this.layout = layout;
		this.chartPlaceholder = chartPlaceholder;
	}

	/** Removes the selected record and updates table and chart. **/
	@Override
	public void handle(ActionEvent event) {
		BMIRecord selected = recordTable.getSelectionModel().getSelectedItem();

		if (selected == null) {
			new Alert(Alert.AlertType.WARNING, "Please select a record to delete.").showAndWait();
			return;
		}

		// Confirm deletion
		Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected record?");
		confirm.setHeaderText(null);
		confirm.setTitle("Confirm Deletion");

		if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
			bmiTracker.removeRecord(selected); // You must implement this method in BMITracker
			recordTable.getItems().setAll(bmiTracker.getRecordHistory());

			LineChart<Number, Number> chart = new BMIChartBuilder().bmiChart(bmiTracker.getRecordHistory());
			layout.getChildren().remove(chartPlaceholder.get());
			layout.getChildren().add(chart);
			chartPlaceholder.set(chart);
		}
	}
}
