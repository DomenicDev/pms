package de.hfu.pms.controller;

import de.hfu.pms.shared.dto.TravelCostConferenceDTO;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
        property = new TravelCostConferenceDTO();
        LocalDate date = datePicker.getValue();
        String title = titleTextField.getText();
        String location = locationTextField.getText();
        // todo BigDecimal sumSupport = ...

        property.setDate(date);
        property.setConferenceTitle(title);
        property.setLocation(location);
        // todo property.setSum()...
    }

    @Override
    public void readProperty(TravelCostConferenceDTO property) {
        throw new UnsupportedOperationException();
    }
}
