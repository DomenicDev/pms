package de.hfu.pms.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * This class contains helper methods for the gui context.
 * E.g. for loading fxml files and receiving the application wide used ResourceBundle.
 */
public class GuiLoader {

    private final static ResourceBundle bundle;

    public static final String EMPLOYMENT = "/screens/employment_screen.fxml";
    public static final String TRAVEL_COST_UNIVERSITY = "/screens/support_travel_cost_uni.fxml";
    public static final String TRAVEL_COST_CONFERENCE = "/screens/support_travel_cost_conference.fxml";

    static {
        // load resource bundle file
        bundle = ResourceBundle.getBundle("lang/strings");
    }

    /**
     * Loads the fxml file from the specified url.
     * The returned parent already includes the globally defined ResourceBundle.
     * @param url the url to load the fxml file from
     * @return the loaded parent
     * @throws IOException if file with specified url does not exist or if file could not be loaded completely
     */
    public static Parent loadFXML(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader(GuiLoader.class.getResource(url));
        loader.setResources(getResourceBundle());
        return loader.load();
    }

    public static Parent loadAbstractFormScreen(String url, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(GuiLoader.class.getResource(url));
        loader.setResources(getResourceBundle());
        loader.setController(controller);
        return loader.load();
    }

    /**
     * The returned ResourceBundle contains all the strings used in
     * the graphical user interface.
     * @return the application wide used resource bundle.
     */
    public static ResourceBundle getResourceBundle() {
        return bundle;
    }

    /**
     * Helper method to create a modal window, especially for small windows, e.g. edit or creation screens.
     * @param url the url of the screen to load
     * @param width the width of the window
     * @param height the height of the window
     * @param resizable true if window shall be resizable, false otherwise
     * @return the newly created stage
     * @throws IOException if screen could not be loaded
     */
    public static Stage createModalWindow(String url, int width, int height, boolean resizable) throws IOException {
        Parent parent = GuiLoader.loadFXML(url);
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(resizable);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        return stage;
    }

}
