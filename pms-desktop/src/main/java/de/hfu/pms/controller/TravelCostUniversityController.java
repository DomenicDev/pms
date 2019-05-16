package de.hfu.pms.controller;

import de.hfu.pms.shared.dto.TravelCostUniversityDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

public class TravelCostUniversityController extends AbstractPropertyFormController<TravelCostUniversityDTO> {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField sumSupportTextField;

    @FXML
    public void initialize() {

    }

    @FXML
    public void handleOnActionSubmitButton() {
        submit();
        close();
    }

    @Override
    public void writeProperty() throws IllegalArgumentException {
        if (property == null) {
            property = new TravelCostUniversityDTO();
        }
        LocalDate date = datePicker.getValue();
        String sum = sumSupportTextField.getText();
        if (!sum.matches("^\\d+,\\d{2}$")) {
            throw new IllegalArgumentException();
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        BigDecimal decimal = null;
        try {
            decimal = new BigDecimal(numberFormat.parse(sum).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        property.setDate(date);
        property.setSum(decimal);
    }

    @Override
    public void readProperty(TravelCostUniversityDTO property) {
        this.property = property;

        this.datePicker.setValue(property.getDate());
        this.sumSupportTextField.setText(property.getSum().toPlainString());
    }

    private void close() {
        datePicker.getScene().getWindow().hide();
    }

    @FXML
    public void handleOnActionCancelButton(ActionEvent event) {
        close();
    }
}
