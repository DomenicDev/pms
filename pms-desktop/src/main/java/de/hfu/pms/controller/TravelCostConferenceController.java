package de.hfu.pms.controller;

import de.hfu.pms.shared.dto.TravelCostConferenceDTO;
import de.hfu.pms.utils.FormValidator;
import de.hfu.pms.utils.NumberUtils;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TravelCostConferenceController extends AbstractPropertyFormController<TravelCostConferenceDTO> {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField sumSupportTextField;


    @Override
    public void writeProperty() throws IllegalArgumentException {
        FormValidator validator = new FormValidator();
        validator.textFieldNotEmpty(titleTextField);
        validator.textFieldNotEmpty(locationTextField);
        validator.hasSetValue(datePicker);
        validator.correctSum(sumSupportTextField);

        if (!validator.validationSuccessful()) {
            throw new IllegalArgumentException("validation not successful");
        }

        // extract values
        LocalDate date = datePicker.getValue();
        String title = titleTextField.getText();
        String location = locationTextField.getText();
        BigDecimal sum = NumberUtils.parseToBigDecimal(sumSupportTextField.getText());

        // set values
        property = new TravelCostConferenceDTO();
        property.setDate(date);
        property.setConferenceTitle(title);
        property.setLocation(location);
        property.setSum(sum);
    }

    @Override
    public void readProperty(TravelCostConferenceDTO property) {
        throw new UnsupportedOperationException();
    }
}
