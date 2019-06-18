package de.hfu.pms.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
    public static final String CONSULTING_SUPPORT = "/screens/support_consulting_screen.fxml";
    public static final String QUALIFICATION = "/screens/qualification_screen.fxml";
    public static final String DOCTORAL_STUDENT_FORM_MASK = "/screens/doctoral_student_form_mask.fxml";
    public static final String UNIVERSITY_FORM_SCREEN = "/screens/university_form_screen.fxml";
    public static final String FACULTY_FORM_SCREEN = "/screens/faculty_form_screen.fxml";
    public static final String ACCOUNT_INFORMATION = "/screens/account_infoscreen.fxml";
    public static final String EDIT_ACCOUNT_INFORMATION = "/screens/change_accountinformation_screen.fxml";
    public static final String USER_FORM_MASK_ADMIN_AREA = "/screens/AddUserAdminArea.fxml";

    static {
        // load resource bundle file
        bundle = ResourceBundle.getBundle("lang/strings");
    }

    /**
     * Loads the fxml file from the specified url.
     * The returned parent already includes the globally defined ResourceBundle.
     *
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
     * This helper method creates and displays the specified fxml in a separate window.
     * The method will return the defined controller of the file
     *
     * @param url   the file of the fxml file to load
     * @param title the title to be shown in top bar
     * @param modal true if the window shall be a modal window, false otherwise
     * @param <T>   the type of the defined controller
     * @return the controller defined for this screen
     * @throws IOException if screen could not be loaded
     */
    public static <T> T createAndShow(String url, String title, boolean modal) throws IOException {
        FXMLLoader loader = new FXMLLoader(GuiLoader.class.getResource(url));
        loader.setResources(bundle);
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);

        if (modal) {
            stage.initModality(Modality.APPLICATION_MODAL);
        }

        stage.show();
        return loader.getController();
    }


    /**
     * The returned ResourceBundle contains all the strings used in
     * the graphical user interface.
     *
     * @return the application wide used resource bundle.
     */
    public static ResourceBundle getResourceBundle() {
        return bundle;
    }

    /**
     * Helper method to create a modal window, especially for small windows, e.g. edit or creation screens.
     *
     * @param url       the url of the screen to load
     * @param width     the width of the window
     * @param height    the height of the window
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

    /**
     * Creates and shows an alert dialog with a yes-button, a no-button, and a cancel button.
     * If not null, the specific callback method are called when clicked on the appropriate button.
     *
     * @param title    the title of the alert to be shown in the top bar
     * @param content  the content text of the alert dialog
     * @param onYes    callback for yes-button
     * @param onNo     callback for no-button
     * @param onCancel callback for cancel-button
     */
    public static void showYesNoCancelAlert(Alert.AlertType alertType, String title, String content, ButtonServiceRoutine onYes, ButtonServiceRoutine onNo, ButtonServiceRoutine onCancel) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        ButtonType okButton = new ButtonType(bundle.getString("ui.label.yes"), ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType(bundle.getString("ui.label.no"), ButtonBar.ButtonData.NO);
        ButtonType cancelButton = new ButtonType(bundle.getString("ui.label.cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {
                    if (type.getButtonData() == ButtonBar.ButtonData.YES) {
                        // the user want to cancel
                        if (onYes != null) {
                            onYes.callback();
                        }
                    }
                    if (type.getButtonData() == ButtonBar.ButtonData.NO) {
                        if (onNo != null) {
                            onNo.callback();
                        }
                    }
                    if (type.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                        if (onCancel != null) {
                            onCancel.callback();
                        }
                    }
                }
        );
    }

    public interface ButtonServiceRoutine {

        void callback();

    }
}
