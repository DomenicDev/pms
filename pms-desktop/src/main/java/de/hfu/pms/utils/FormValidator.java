package de.hfu.pms.utils;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

/**
 * The FormValidator provides methods for validating input data from JavaFx controls.
 * It automatically tags controls with special CSS-classes if the validation was not successful.
 */
public class FormValidator {

    private static final String NULL = "null";
    private static final String ERROR_PROMPT_TEXT = "error_prompt_text";
    private static final String MISSING_DATE = "missing_date";
    private static final String ERROR_BORDER = "error_border";

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
     *
     * @return true if all validation calls were successful, false otherwise
     */
    public boolean validationSuccessful() {
        return this.valid;
    }

    /**
     * Checks whether the specified ComboBox actually has an
     * item selected.
     *
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
     *
     * @param textField the TextField to validate
     * @return true if the TextField is not empty, false otherwise.
     */
    public boolean textFieldNotEmpty(TextField textField) {
        if (textField.getText() != null && textField.getText().trim().length() > 0) {
            removeClass(textField, ERROR_PROMPT_TEXT);
            textField.setPromptText("");
            return true;
        } else {
            addClass(textField, ERROR_PROMPT_TEXT);
            failValidation();
            textField.setPromptText(bundle.getString("ui.validation.empty_textfield_not_allowed"));
            return false;
        }
    }

    /**
     * Validates whether the specified input is a valid grade notation.
     *
     * @param textField the TextField to check
     * @return true if the text matches the grade notation syntax
     */
    public boolean isValidGrade(TextField textField) {
        if (textFieldNotEmpty(textField)) {
            removeClass(textField, ERROR_BORDER);
            String grade = textField.getText();
            if (grade.matches("^\\d[,.]\\d{1,2}$")) {
                return true;
            } else {
                addClass(textField, ERROR_BORDER);
                failValidation();
                return false;
            }
        }
        return false;
    }

    public boolean validateUsername(TextField textField) {
        String input = textField.getText();
        removeClass(textField, ERROR_BORDER);
        if (input.matches("^(\\w)+$")) {
            return true;
        } else {
            addClass(textField, ERROR_BORDER);
            failValidation();
            return false;
        }
    }

    public boolean passwordFieldsAreSimilar(PasswordField passwordField, PasswordField passwordFieldRewrite) {
        if (passwordField.getText().equals(passwordFieldRewrite.getText())) {
            return true;
        } else {
            failValidation();
            return false;
        }
    }

    public boolean hasSetValue(DatePicker datePicker) {
        removeClass(datePicker, ERROR_BORDER);
        removeClass(datePicker.getEditor(), ERROR_PROMPT_TEXT);

        if (datePicker.getEditor().getText().trim().isEmpty()) {
            addClass(datePicker, ERROR_BORDER);
            addClass(datePicker.getEditor(), ERROR_PROMPT_TEXT);
            datePicker.setPromptText(bundle.getString("ui.validation.missing_date"));
            failValidation();
            return false;
        }

        try {
            datePicker.setValue(datePicker.getConverter().fromString(datePicker.getEditor().getText()));
            return true;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            addClass(datePicker, ERROR_BORDER);
            failValidation();
            return false;
        }

    }

    public boolean correctSum(TextField textField) {
        if (textFieldNotEmpty(textField)) {
            String text = textField.getText();
            removeClass(textField, ERROR_BORDER);
            if (text.matches("^((0|[1-9]\\d*)|[1-9]{1,3}(\\.\\d{3})*)(,\\d{2})?$")) {
                return true;
            } else {
                addClass(textField, ERROR_BORDER);
                failValidation();
                return false;
            }
        }
        return false;
    }

    public boolean correctInt(TextField textField) {
        if (textFieldNotEmpty(textField)) {
            removeClass(textField, ERROR_BORDER);
            try {
                Integer.parseInt(textField.getText());
                return true;
            } catch (NumberFormatException e) {
                failValidation();
                addClass(textField, ERROR_BORDER);
                return false;
            }
        }
        return false;
    }

    private static void addClass(Node control, String className) {
        control.getStyleClass().add(className);
    }

    private static void removeClass(Node control, String className) {
        control.getStyleClass().remove(className);
    }


}
