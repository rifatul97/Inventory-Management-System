package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * This class objective is to handle the various common alert box message to
 * the screen that can be reused in various classes.
 *
 * <p><b>FUTURE ENHANCEMENT</b> The alert box can take response from the keyboard.</p>
 *
 * @author Rifatul Karim
 * @version 0.90
 */
public class AlertBoxHandler {

    /**
     * This class objective is to display confirmation message
     *
     * @param type indicate which type of scripted confirmation message to display
     * @return true indicates that the "OK"/"Yes" button was pressed
     */
    public static boolean displayConfirmationDialogue(int type) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        switch (type) {
            case 1: alert.setContentText("Are you sure with deleting this data from inventory?");
                    break;
            case 2: alert.setContentText("Are you sure you want to exit the application?");
                    break;
            case 3: alert.setContentText("Are you sure with deleting this data from the associate part?");
                    break;
            default: break;
        }

        Optional<ButtonType> result = alert.showAndWait();

        return result.orElse(null) == ButtonType.OK;
    }

    /**
     * This class objective is to display warning message that a
     * .fxml file was not found.
     *
     * @param fileNotFound indicate which specific file was not found
     */
    public static void displayWarningDialogue(String fileNotFound) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("IOException Error:");
        alert.setContentText("Cannot change the screen because " + fileNotFound + " is not found!");
        alert.showAndWait();
    }

    /**
     * This class objective is to display information message during when
     * the search term entered does not match any data in the inventory
     *
     * @param type indicate which type of scripted confirmation message to display
     */
    public static void displayNotFoundDialogue(int type) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Not Found!");

        if (type == 1) {
            alert.setContentText("The search term entered does not match any part in the inventory!");
        } else {
            alert.setContentText("The search term entered does not match any product in the inventory");
        }

        alert.showAndWait();
    }

}
