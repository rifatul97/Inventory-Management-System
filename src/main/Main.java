package main;

import controller.AlertBoxHandler;
import controller.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Inventory Application using object-oriented approach.
 *
 * <p>javadocs are located in src\javadocs\index</p>
 *
 * <p><b>RUNTIME ERROR</b> On previous version of this application, there was error on the search functionality
 *  were partial searches were not observed. This error was found because previously it could only return either
 *  Id match or name match together so I have implemented method that searches both together by looking at the search id first
 *  by inspecting if the textfield is number or not and then search by name. Another error were present that
 *  it could not alert the user that if there were no search found which I have fixed by counting the search part/product
 *  and if they equate to zero then I would display alert box using AlertBoxHandler class.</p>
 *
 * <p><b>FUTURE ENHANCEMENT</b> I would like to store the inventory in a computer file.</p>
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class Main extends Application {

    /** The main stage of the application */
    public static Stage mainStage;

    /**
     * The start method that is use to load the MainScreen of the application.
     *
     * @param primaryStage The primaryStage of the application
     */
    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle("RifatulKarim_C482_PA");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
            Parent root = loader.load();

            MainScreenController mainScreenController = loader.getController();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            AlertBoxHandler.displayWarningDialogue("MainScreen.fxml");
            System.exit(1);
        }
    }

    /** @param args the command line arguments */
    public static void main(String[] args) {
        launch(args);
    }

    /** sets a scene to the mainstage
     * @param scene the scene that will be passed from other class whenever load to a new screen
     */
    public static void setScene(Scene scene) {
        mainStage.setScene(scene);
        mainStage.show();
    }

}
