package handler;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;

/** Utility class for showing alert dialogs. **/
public class AlertUtil {

  /** Displays an error alert with the given title and message. **/

    public static void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}