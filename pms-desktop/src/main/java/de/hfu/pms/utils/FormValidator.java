package de.hfu.pms.utils;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ResourceBundle;

/**
 * The FormValidator provides methods for validating input data from JavaFx controls.
 * It automatically tags controls with special CSS-classes if the validation was not successful.
 */
public class FormValidator {

    private static final String NULL = "null";
    private static final String EMPTY = "empty";

    private static ResourceBundle bundle = GuiLoader.getResourceBundle();

    private boolean valid;

    /**
     * Creates a new FormValidator.
     */
    public FormValidator() {
        this.valid = true;
    }

    /**
     * Use this method at the end of all validations
     * to check if there were any validations that failed.
     * @return true if all validation calls were successful, false otherwise
     */
    public boolean validationSuccessful() {
        return this.valid;
    }

    /**
     * Checks whether the specified ComboBox actually has an
     * item selected.
     * @param comboBox the ComboBox to check
     * @return true if an item of the ComboBox has been selected by the user
     */
    public boolean comboBoxHasSelectedItem(ComboBox comboBox) {
        removeClass(comboBox, NULL);
        if (comboBox.getValue() == null) {
            addClass(comboBox, NULL);
            comboBox.setPromptText(bundle.getString("ui.validation.combo_box_selection_required"));
            failValidation();
            return false;
        }
        return true;
    }

    private void failValidation() {
        this.valid = false;
    }

    /**
     * Validates whether the specified TextField neither null nor empty.
     * @param textField the TextField to validate
     * @return true if the TextField is not empty, false otherwise.
     */
    public boolean textFieldNotEmpty(TextField textField) {
        if (textField.getText() != null && textField.getText().length() > 0) {
            removeClass(textField, EMPTY);
            return true;
        } else {
            addClass(textField, EMPTY);
            failValidation();
            textField.setPromptText(bundle.getString("ui.validation.empty_textfield_not_allowed"));
            return false;
        }
    }

    /**
     * Validates whether the specified input is a valid grade notation.
     * @param textField the TextField to check
     * @return true if the text matches the grade notation syntax
     */
    public boolean isValidGrade(TextField textField) {
        String grade = textField.getText();
        if (grade.matches("^\\d[,.]\\d{1,2}$")) {
            return true;
        } else {
            failValidation();
            return false;
        }
    }

    public boolean passwordFieldsAreSimilar (PasswordField passwordField,PasswordField passwordFieldRewrite){
        if (passwordField.getText().equals(passwordFieldRewrite.getText())) {
            return true;
        }
        else{
            failValidation();
            return false;
        }
    }
    private static void addClass(Node control, String className) {
        control.getStyleClass().add(className);
    }

    private static void removeClass(Node control, String className) {
        control.getStyleClass().remove(className);
    }


}
