package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import main.Main;
import model.Inhouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that controls the logic of the part screen of the application.
 *
 * <p><b>RUNTIME ERROR</b> On previous version, there was error observed during modifying Part
 * when switching type from outsource to machineid because of format difference as machineid
 * cannot hold non-numeric which I have fixed by implementing another try catch block during checking
 * the textfield on the checkValidity() class and the program will expect to throw NumberExceptionError
 * like other required numeric fields. Another error was that machineid was initialized as integer hence
 * it could not hold larger number and to fix this, it has been changed to long.</p>
 *
 * <p><b>FUTURE ENHANCEMENT</b> I would like to add more variety Part Type to select and
 *  to select from many variety, I would use combo box to display the Part Type information.</p>
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class PartScreenController implements Initializable {

    /**
     * Indicates the label of add/modify part on the screen.
     */
    @FXML private Label partScreenLabel;

    /**
     * Label for the part type. The value will change depends on which radio button selected.
     */
    @FXML private Label partTypeLabel;

    /**
     * RadioButton to select inhouse option
     */
    @FXML private RadioButton inHouseButton;

    /**
     * RadioButton to select outsource option
     */
    @FXML private RadioButton outSourceButton;

    /**
     * Displays the Id of the part.
     */
    @FXML private TextField partIdField;

    /**
     * Displays the Name of the part.
     */
    @FXML private TextField partNameField;

    /**
     * Displays the Inventory level of the part.
     */
    @FXML private TextField partInventoryField;

    /**
     * Displays the Price of the part.
     */
    @FXML private TextField partPriceField;

    /**
     * Displays the Max of the part.
     */
    @FXML private TextField partMaxField;

    /**
     * Displays the Min of the part.
     */
    @FXML private TextField partMinField;

    /**
     * Displays the machine_id or company_name of the part.
     */
    @FXML private TextField partTypeField;

    /**
     * Label that will show error message of the Name input field.
     */
    @FXML private Label nameErrorMsg;

    /**
     * Label that will show error message of the stock input field.
     */
    @FXML private Label invErrorMsg;

    /**
     * Label that will show error message of the Price input field.
     */
    @FXML private Label priceErrorMsg;

    /**
     * Label that will show error message of the part type input field.
     * It will typically print error if the input is in wrong format.
     */
    @FXML private Label ptypeErrorMsg;

    /**
     * Label that will show error message if the user did not select any
     * one of radio button and also print out logic error if max value is
     * less than min value.
     */
    @FXML private Label exceptionErrorMsg;

    /**
     * Label that will show error message of the Max input field.
     */
    @FXML private Label maxFieldErrorMsg;

    /**
     * Label that will show error message of the Min input field.
     */
    @FXML private Label minFieldErrorMsg;

    /**
     * Type of part Type selected on the Add/Modify Part Screen
     */
    public enum PART_TYPE { IN_HOUSE, OUTSOURCED, NONE_SELECTED }

    /**
     * The current part type selected on the Add Part screen.
     */
    public static PART_TYPE current_partType_selected = PART_TYPE.NONE_SELECTED;


    /**
     * Initiated upon calling this controller when selecting to modify/add Part from the main screen.
     * It will disable the ability the edit data of the id field and also disable the part Type
     * field until user pick one of Part Type. Furthermore, it will let the program know if the user
     * is in modify or add screen and save the data accordingly.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdField.setDisable(true);

        // sets the main label depend on the screen's state
        switch (MainScreenController.currentScreenState) {
            case ADD:
                partScreenLabel.setText("Add Part");
                break;
            case MODIFY:
                partScreenLabel.setText("Modify Part");
                break;
            default: break;
        }

        // fires the radio button but it will not fire at start if it is on Add screen
        switch (current_partType_selected) {
            case IN_HOUSE:
                inHouseButton.fire();
                partTypeLabel.setText("Machine ID");
                break;
            case OUTSOURCED:
                outSourceButton.fire();
                partTypeLabel.setText("Company Name");
                break;
            default:
                partTypeField.setDisable(true);
                break;
        }

    }

    /**
     * Functions to save the new or modified Part data to the inventory. Check the data
     * validity and prints out error if present otherwise takes user to the main screen.
     *
     * @param actionEvent add Data click handler
     */
    public void partSave_ClickHandler(ActionEvent actionEvent) {
        if (checkDataValidity() == true) {
            return;
        }

        /* at this point, checkDataValidaity returned false that means we are safe
           to parseInt from textfield without getting NumberException error message. */
        int minVal = Integer.parseInt(partMinField.getText());
        int maxVal = Integer.parseInt(partMaxField.getText());
        int stockVal = Integer.parseInt(partInventoryField.getText());
        double priceVal = Double.parseDouble(partPriceField.getText());
        String partName = partNameField.getText();

        if (MainScreenController.currentScreenState == MainScreenController.ScreenState.MODIFY) {
            int idVal = Integer.parseInt(partIdField.getText());

            if (inHouseButton.isSelected()) {
                Inhouse updatedPart = new Inhouse(idVal, partName, priceVal, stockVal, minVal, maxVal, Long.parseLong(partTypeField.getText()));
                Inventory.updatePart(idVal, updatedPart);
            }
            else {
                Outsourced updatedPart = new Outsourced(idVal, partName, priceVal, stockVal, minVal, maxVal, partTypeField.getText());
                Inventory.updatePart(idVal, updatedPart);
            }
        }
        else {
            int id = -1;
            // Step to generate new ID - finding the largest id number and then add 1 to obtain new id
            if (Inventory.getAllParts().size() == 0) {
                id = 1;
            } else {
                for(Part p : Inventory.getAllParts()) {
                    id = Math.max(p.getId()+1, id);
                }
            }

            System.out.println("new id will be = " + id);

            if (inHouseButton.isSelected()) {
                Inhouse updatedPart = new Inhouse(id, partName, priceVal, stockVal, minVal, maxVal, Long.parseLong(partTypeField.getText()));
                Inventory.addPart(updatedPart);
            }
            else {
                Outsourced updatedPart = new Outsourced(id, partName, priceVal, stockVal, minVal, maxVal, partTypeField.getText());
                Inventory.addPart(updatedPart);
            }
        }

        current_partType_selected = PART_TYPE.NONE_SELECTED;

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            Main.mainStage.setScene(new Scene(root));
            Main.mainStage.show();
        } catch (IOException e) {
            AlertBoxHandler.displayWarningDialogue("MainScreen.fxml");
        }

    }

    /**
     * Takes the user to the main screen.
     * Throws IOException if it cannot locate MainScreen.fxml
     *
     * @param actionEvent cancel Action
     */
    public void cancelAction(ActionEvent actionEvent)  {
        try {
            current_partType_selected = PART_TYPE.NONE_SELECTED;
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));

            Main.mainStage.setScene(new Scene(root));
            Main.mainStage.show();
        } catch (IOException e) { AlertBoxHandler.displayWarningDialogue("MainScreen.fxml"); }
    }

    /**
     * Initiates to transfer the selected part data from the main screen part Table to the Part screen.
     *
     * @param selectedPart the part data that is selected from the main screen Part table.
     */
    public void initData(Part selectedPart) {
        partIdField.setText(String.valueOf(selectedPart.getId()));
        partInventoryField.setText(String.valueOf(selectedPart.getStock()));
        partMaxField.setText(String.valueOf(selectedPart.getMax()));
        partMinField.setText(String.valueOf(selectedPart.getMin()));
        partNameField.setText(selectedPart.getName());
        partPriceField.setText(String.valueOf(selectedPart.getPrice()));

        if (selectedPart instanceof Inhouse) {
            partTypeField.setText(((Inhouse) selectedPart).getMachineId() + "");
        }
        else {

            partTypeField.setText(((Outsourced) selectedPart).getCompanyName() + "");
        }

    }

    /**
     * Checks all the data inputs on the textfield and see if they are in right format and follows the rule.
     * Afterwards it will print out for each errors into the same screen otherwise returns false to indicate
     * there are no error.
     *
     * @return returns true if there is error present.
     */
    private boolean checkDataValidity () {
        boolean errorFound = false;
        emptyErrorLabels();

        int stockVal = -1, minVal = -1, maxVal = -1;
        long machineIdVal = -1;
        double priceVal = -1;

        if (!inHouseButton.isSelected() && !outSourceButton.isSelected()) {
            ScreenExceptionHandler.showErrorMsg(10, exceptionErrorMsg);
            return true;
        }

        if (partNameField.getText().trim().length() == 0) {
            ScreenExceptionHandler.showErrorMsg(1, nameErrorMsg);
            errorFound = true;
        }

        try {
            minVal = Integer.parseInt(partMinField.getText());
        } catch (NumberFormatException e) {
            ScreenExceptionHandler.showErrorMsg(2, minFieldErrorMsg);
            errorFound = true;
        }
        try {
            maxVal = Integer.parseInt(partMaxField.getText());
        } catch (NumberFormatException e) {
            ScreenExceptionHandler.showErrorMsg(2, maxFieldErrorMsg);
            errorFound = true;
        }
        try {
            stockVal = Integer.parseInt(partInventoryField.getText());
        } catch (NumberFormatException e) {
            ScreenExceptionHandler.showErrorMsg(2, invErrorMsg);
            errorFound = true;
        }
        try {
            priceVal = Double.parseDouble(partPriceField.getText());
        } catch (NumberFormatException e) {
            priceErrorMsg.setText("Enter a valid price value. It must include cents.");
            errorFound = true;
        }

        if (inHouseButton.isSelected()) {
            try {
                machineIdVal = Long.parseLong(partTypeField.getText());
            } catch (NumberFormatException e) {
                ScreenExceptionHandler.showErrorMsg(2, ptypeErrorMsg);
                errorFound = true;
            }
        } else if (partTypeField.getText().trim().length() == 0) {
            ScreenExceptionHandler.showErrorMsg(1, ptypeErrorMsg);
            errorFound = true;
        }

        if (errorFound) { return true; }

        if (minVal < 0) {
            ScreenExceptionHandler.showErrorMsg(3, minFieldErrorMsg);
            errorFound = true;
        }
        if (maxVal < 0) {
            ScreenExceptionHandler.showErrorMsg(3, maxFieldErrorMsg);
            errorFound = true;
        }
        if (maxVal <= minVal) {
            ScreenExceptionHandler.showErrorMsg(4, minFieldErrorMsg);
            errorFound = true;
        }
        if (priceVal < 0) {
            ScreenExceptionHandler.showErrorMsg(3, priceErrorMsg);
            errorFound = true;
        }
        if (!(stockVal >= minVal && stockVal <= maxVal)) {
            errorFound = true;
            ScreenExceptionHandler.showErrorMsg(9, invErrorMsg);
        }

        return errorFound;
    }
    /**
     * Radio Button that serves to indicate the part type is in-house and
     * then will change the part type label accordingly.
     *
     * @param actionEvent inHouse Button Handler
     */
    public void inHouseButtonHandler(ActionEvent actionEvent) {
        current_partType_selected = PART_TYPE.IN_HOUSE;
        partTypeLabel.setText("Machine ID");
        partTypeField.setDisable(false);
    }

    /**
     * Radio Button that serves to indicate the part type is outsourced and
     * then will change the part type label accordingly.
     *
     * @param actionEvent outsource button handler
     */
    public void outSourcedButtonHandler(ActionEvent actionEvent) {
        current_partType_selected = PART_TYPE.OUTSOURCED;
        ptypeErrorMsg.setText("");
        partTypeLabel.setText("Company Name");
        partTypeField.setDisable(false);
    }

    /**
     * Removes the error messages display on the screen.
     */
    private void emptyErrorLabels() {
        nameErrorMsg.setText("");
        ptypeErrorMsg.setText("");
        exceptionErrorMsg.setText("");
        maxFieldErrorMsg.setText("");
        minFieldErrorMsg.setText("");
        priceErrorMsg.setText("");
        invErrorMsg.setText("");
    }
}
