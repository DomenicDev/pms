package de.hfu.pms.controller;

import de.hfu.pms.shared.dto.VisitedQualificationDTO;
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
        if (property == null) {
            property = new VisitedQualificationDTO();
        }

        LocalDate qualificationDate = qualificationDatePicker.getValue();
        String qualificationName = qualificationNameTextField.getText();

        property.setQualificationDate(qualificationDate);
        property.setNameOfQualification(qualificationName);
    }

    @Override
    public void readProperty(VisitedQualificationDTO property) {
        throw new UnsupportedOperationException();
    }


}
