package controller;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
* This class objective is to handle the various common error message to print
* on the specific label from a screen that are passed in a parameter.
*
* <p><b>FUTURE ENHANCEMENT</b> I will like to add small enchantment for new transform method
*                      that will style the error textfield into red.</p>
*
* @author Rifatul Karim
* @version 0.95
*/
public final class ScreenExceptionHandler {

    /**
    * This method is used to show common error of the application
    *
    * @param type check for the type of error
    * @param errorLabel Label to print the message.
    */
    public static void showErrorMsg(int type, Label errorLabel) {

        switch (type) {
            case 1: errorLabel.setText("This field cannot be empty.");
                    break;
            case 2: errorLabel.setText("Enter a valid number value.");
                    break;
            case 3: errorLabel.setText("This value cannot be negative.");
                    break;
            case 4: errorLabel.setText("Max value cannot be less than Min!");
                    break;
            case 9: errorLabel.setText("Inventory Level must be between Min and Max.");
                    break;
            case 10: errorLabel.setText("Must pick one of Part type!");
                     break;
            default: break;
        }
    }

    /** Generates a fadescreen transition effect to a label. */
    public static void transformLabel (Label label) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(4.0), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }

}
