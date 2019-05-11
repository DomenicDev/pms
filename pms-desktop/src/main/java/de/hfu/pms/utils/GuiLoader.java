package de.hfu.pms.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * This class contains helper methods for the gui context.
 * E.g. for loading fxml files and receiving the application wide used ResourceBundle.
 */
public class GuiLoader {

    private final static ResourceBundle bundle;

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

    /**
     * The returned ResourceBundle contains all the strings used in
     * the graphical user interface.
     * @return the application wide used resource bundle.
     */
    public static ResourceBundle getResourceBundle() {
        return bundle;
    }

}
