package de.hfu.pms.controller;

import de.hfu.pms.shared.dto.ConsultingSupportDTO;
import de.hfu.pms.utils.FormValidator;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ConsultingSupportController extends AbstractPropertyFormController<ConsultingSupportDTO> {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField kindOfConsultingTextField;

    @FXML
    private TextField durationOfConsultingTextField;

    @Override
    public void writeProperty() throws IllegalArgumentException {
        FormValidator validator = new FormValidator();
        validator.hasSetValue(datePicker);
        validator.textFieldNotEmpty(kindOfConsultingTextField);
        validator.correctInt(durationOfConsultingTextField);

        if (!validator.validationSuccessful()) {
            throw new IllegalArgumentException();
        }

        LocalDate localDate = datePicker.getValue();
        String kindOfConsulting = kindOfConsultingTextField.getText();
        String durationOfConsultingString = durationOfConsultingTextField.getText();
        int duration = Integer.parseInt(durationOfConsultingString);

        property = new ConsultingSupportDTO();
        property.setConsultingDate(localDate);
        property.setConsultingType(kindOfConsulting);
        property.setConsultingDuration(duration);
    }

    @Override
    public void readProperty(ConsultingSupportDTO property) {
        throw new UnsupportedOperationException();
    }
}
