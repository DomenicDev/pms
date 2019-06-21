package de.hfu.pms.controller;

import de.hfu.pms.shared.dto.TravelCostUniversityDTO;
import de.hfu.pms.utils.FormValidator;
import de.hfu.pms.utils.NumberUtils;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TravelCostUniversityController extends AbstractPropertyFormController<TravelCostUniversityDTO> {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField sumSupportTextField;

    @Override
    public void writeProperty() throws IllegalArgumentException {
        // form validation
        FormValidator validator = new FormValidator();
        validator.hasSetValue(datePicker);
        validator.correctSum(sumSupportTextField);

        if (!validator.validationSuccessful()) {
            throw new IllegalArgumentException("validation not successful");
        }

        // extract values
        LocalDate date = datePicker.getValue();
        BigDecimal sum = NumberUtils.parseToBigDecimal(sumSupportTextField.getText());

        // set values
        property = new TravelCostUniversityDTO();
        property.setDate(date);
        property.setSum(sum);
    }

    @Override
    public void readProperty(TravelCostUniversityDTO property) {
        throw new UnsupportedOperationException();
    }

}
