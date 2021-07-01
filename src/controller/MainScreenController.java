package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import main.Main;
import model.Inhouse;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that controls the logic of the main screen of the application.
 *
 * <p><b>FUTURE ENHANCEMENT</b> I would like to add two features and one of them is preventing the Part to be
 * delete that has been added to the product. Another interesting addition would be displaying
 * total number of part of each product contain.</p>
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class MainScreenController implements Initializable {

    /**
     * Table view for the parts of the inventory.
     */
    @FXML
    private TableView<Part> partTable;

    /**
     * Table view for the product of the inventory.
     */
    @FXML
    private TableView<Product> productTable;

    /**
     * Column that displays the Id of a part.
     */
    @FXML
    private TableColumn<Part, Integer> partID_col;

    /**
     * Column that displays the Name for the part table.
     */
    @FXML
    private TableColumn<Part, String> partName_col;

    /**
     * Column that displays the Inventory level for the part table.
     */
    @FXML
    private TableColumn<Part, String> partInvLevel_col;

    /**
     * Column that display the Price for the part table.
     */
    @FXML
    private TableColumn<Part, Double> partPrice_col;

    /**
     * Column that display the Id for the product table.
     */
    @FXML
    private TableColumn<Product, Integer> productId_col;

    /**
     * Column that display the Name for the product table.
     */
    @FXML
    private TableColumn<Product, String> productName_col;

    /**
     * Column that display the Price for the product table.
     */
    @FXML
    private TableColumn<Product, Double> productPrice_col;

    /**
     * Text field for the part search on the table.
     */
    @FXML
    private TextField searchPartTextField;

    /**
     * Text field for the product search on the table.
     */
    @FXML
    private TextField searchProductTextField;

    /**
     * Label to show the error on the screen when deleting product that has associate parts.
     */
    @FXML
    private Label productDeleteErrorMsg;

    /**
     * To distinguish the state of the screen to display
     */
    public enum ScreenState {ADD, MODIFY, MAIN}

    /**
     * The current screen state. When launching the application this will be set to MAIN.
     */
    public static ScreenState currentScreenState;

    /**
     * Initiated upon calling this controller when starting the program or returning to this screen from
     * other screens. This class requires to initializes the table column for the both tables. Afterwards, it
     * populates the table views with current saved inventory data.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentScreenState = ScreenState.MAIN;

        partID_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevel_col.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        productId_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPrice_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getProducts());
    }

    /**
     * Opens the add Part Screen
     * Displays alert box if it cannot locate PartScreen.fxml
     *
     * @param actionEvent add Part click handler
     */
    public void addPart_ClickHandler(ActionEvent actionEvent) {
        try {
            currentScreenState = ScreenState.ADD;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PartScreen.fxml"));
            Parent root = loader.load();

            PartScreenController partScreenController = loader.getController();
            PartScreenController.current_partType_selected = PartScreenController.PART_TYPE.NONE_SELECTED;

            Main.setScene(new Scene(root));

        } catch (IOException e) {
            currentScreenState = ScreenState.MAIN;
            AlertBoxHandler.displayWarningDialogue("PartScreen.fxml");
        }
    }

    /**
     * Loads the selected part data into the modify part screen to edit the data.
     * Displays alert box if it cannot locate PartScreen.fxml
     *
     * @param actionEvent modify Part click handler
     */
    public void modifyPart_ClickHandler(ActionEvent actionEvent) {
        Part selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();

        // if there is no selection on the part select then it won't try to load the screen.
        if (selectedPart == null) {
            return;
        }

        try {
            currentScreenState = ScreenState.MODIFY;

            if (selectedPart instanceof Inhouse) {
                PartScreenController.current_partType_selected = PartScreenController.PART_TYPE.IN_HOUSE;
            } else {
                PartScreenController.current_partType_selected = PartScreenController.PART_TYPE.OUTSOURCED;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PartScreen.fxml"));
            Parent root = loader.load();

            PartScreenController partScreenController = loader.getController();
            partScreenController.initData(selectedPart);

            Main.setScene(new Scene(root));
            Main.mainStage.show();

        } catch (IOException e) {
            AlertBoxHandler.displayWarningDialogue("PartScreen.fxml");
            currentScreenState = ScreenState.MAIN;
        }
    }

    /**
     * Delete the selected part data.
     * <p>
     * It will print message on the screen if it does not selects data from part table.
     * Also it will show confirmation box before deleting the data. </p>
     *
     * @param actionEvent delete Part click handler
     */
    public void deletePart_clickHandler(ActionEvent actionEvent) {
        Part selectedPart = (Part) partTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            return;
        }

        if (AlertBoxHandler.displayConfirmationDialogue(1)) {
            if (Inventory.deletePart(selectedPart) == true) {
                partTable.refresh();
            }
        }
    }


    /**
     * Loads the selected product data into the modify product screen to edit its data.
     * Displays alert box if it cannot locate productscreen.fxml
     *
     * @param actionEvent modify Product click handler
     */
    public void modifyProduct_clickHandler(ActionEvent actionEvent) {
        Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            return;
        }

        try {
            currentScreenState = ScreenState.MODIFY;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProductScreen.fxml"));
            Parent root = loader.load();

            ProductScreenController productScreenController = loader.getController();
            productScreenController.loadSelectedProductData(selectedProduct);

            Main.mainStage.setScene(new Scene(root));
            Main.mainStage.show();

        } catch (IOException e) {
            currentScreenState = ScreenState.MAIN;
            AlertBoxHandler.displayWarningDialogue("ProductScreen.fxml");
        }

    }

    /**
     * Opens the add Product Screen
     * Displays alert box if it cannot locate PartScreen.fxml
     *
     * @param actionEvent add Part click handler
     */
    public void addProduct_clickHandler(ActionEvent actionEvent) {
        try {
            currentScreenState = ScreenState.ADD;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProductScreen.fxml"));
            Parent root = loader.load();

            ProductScreenController productScreenController = loader.getController();

            Main.mainStage.setScene(new Scene(root));
            Main.mainStage.show();

        } catch (IOException e) {
            currentScreenState = ScreenState.MAIN;
            AlertBoxHandler.displayWarningDialogue("PartScreen.fxml");
            return;
        }
    }

    /**
     * Delete the selected product data.
     * <p>
     * It will show error popup if it does not selects data from product table
     * and also if the selected product has any associate parts. </p>
     *
     * @param actionEvent delete Product click handler
     */
    public void deleteProduct_clickHandler(ActionEvent actionEvent) {
        Product selectedProduct = (Product) productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            return;
        }

        if (AlertBoxHandler.displayConfirmationDialogue(1)) {
            if (Inventory.deleteProduct(selectedProduct)) {
                productTable.refresh();
            } else {
                productDeleteErrorMsg.setText("Cannot delete " + selectedProduct.getName() +
                        " product data from inventory\nbecause it has associate parts!");
                ScreenExceptionHandler.transformLabel(productDeleteErrorMsg);
            }
        }

    }

    /**
     * Checks if the textfield of the product search is empty
     * then it will refresh the table to the original state.
     *
     * @param keyEvent search product on key pressed handler
     */
    public void searchProduct_onKeyPressed_Handler(KeyEvent keyEvent) {

        if (searchProductTextField == null) {
            return;
        }

        if (searchProductTextField.getText().trim().length() == 0) {
            productTable.setItems(Inventory.getProducts());
            productTable.refresh();
        }
    }

    /**
     * Checks if the textfield of the part search is empty
     * then it will refresh the table to the original state.
     *
     * @param keyEvent search part on key pressed handler
     */
    public void searchPart_onKeyPressed_Handler(KeyEvent keyEvent) {

        if (searchPartTextField == null) {
            return;
        }

        if (searchPartTextField.getText().trim().length() == 0) {
            partTable.setItems(Inventory.getAllParts());
            partTable.refresh();
        }
    }

    /**
     * Exits the application
     *
     * @param actionEvent exit click handler
     */
    public void exitClickHandler(ActionEvent actionEvent) {
        if (AlertBoxHandler.displayConfirmationDialogue(2)) {
            Platform.exit();
        }
    }

    /**
     * The program looks up for the parts by id or name that partially matches
     * and load them into the table.
     * <p>
     * The table will be refreshed to the original state if the textfield is empty. </p>
     *
     * @param actionEvent Part search button action.
     */
    public void searchPart_ClickHandler(ActionEvent actionEvent) {
        int index = -1;
        String searchText = searchPartTextField.getText().trim();
        ObservableList<Part> partList = FXCollections.observableArrayList();

        try {
            index = Integer.parseInt(searchText);
        } catch (NumberFormatException e) {
            index = -1;
        }

        if (index != -1) {
            Part part = Inventory.lookupByPartID(index);
            if (part != null) {
                partList.add(part);
            }
        }

        ObservableList<Part> partList2 = Inventory.lookupByPartName(searchText);

        for (int i = 0; i < partList2.size(); i++) {
            partList.add(partList2.get(i));
        }

        partTable.setItems(partList);
        partTable.refresh();

        if (partList.size() == 0) {
            AlertBoxHandler.displayNotFoundDialogue(1);
        }
    }

    /**
     * The program looks up for the product by id or name that partially matches
     * and load them into the table.
     * <p>
     * The table will be refreshed to the original state if the textfield is empty.
     *
     * @param actionEvent Product search button action.
     */
    public void searchProduct_ClickHandler(ActionEvent actionEvent) {
        int index = -1;
        String searchText = searchProductTextField.getText().trim();
        ObservableList<Product> productList = FXCollections.observableArrayList();

        try {
            index = Integer.parseInt(searchText);
        } catch (NumberFormatException e) {
        }

        if (index != -1) {
            Product product = Inventory.lookupByProductID(index);
            if (product != null) {
                productList.add(product);
            }
        }

        ObservableList<Product> productList2 = Inventory.lookupByProductName(searchText);

        for (Product product : productList2) {
            productList.add(product);
        }


        productTable.setItems(productList);
        productTable.refresh();

        if (productList.size() == 0) {
            AlertBoxHandler.displayNotFoundDialogue(0);
        }
    }

}
