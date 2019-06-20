package de.hfu.pms.controller;

import de.hfu.pms.shared.dto.VisitedQualificationDTO;
import de.hfu.pms.utils.FormValidator;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class QualificationScreenController extends AbstractPropertyFormController<VisitedQualificationDTO> {

    @FXML
    private DatePicker qualificationDatePicker;

    @FXML
    private TextField qualificationNameTextField;

    @Override
    public void writeProperty() throws IllegalArgumentException {
        // form validation
        FormValidator validator = new FormValidator();
        validator.hasSetValue(qualificationDatePicker);
        validator.textFieldNotEmpty(qualificationNameTextField);

        if (!validator.validationSuccessful()) {
            throw new IllegalArgumentException("validation not successful");
        }

        // extract values
        LocalDate qualificationDate = qualificationDatePicker.getValue();
        String qualificationName = qualificationNameTextField.getText();

        // apply values to object
        property = new VisitedQualificationDTO();
        property.setQualificationDate(qualificationDate);
        property.setNameOfQualification(qualificationName);
    }

    @Override
    public void readProperty(VisitedQualificationDTO property) {
        throw new UnsupportedOperationException();
    }


}
