package de.hfu.pms.controller;
import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.shared.dto.UserDTO;
import de.hfu.pms.utils.FormValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ChangeAccountInformationController {
        private EventBus eventBus = EventBusSystem.getEventBus();

        @FXML
        private Button saveChangesButton;

        @FXML
        private Button ButtonExitScreen;

        @FXML
        private TextField textfieldChangeEmail;

        @FXML
        private TextField textfieldFornMeLastname;

        @FXML
        private Label labelChangeUsername;

        @FXML
        private Label lableChangeRole;

        @FXML
        private TextField textfieldLastname;

        @FXML
        void handleChangeUserInformationbutton(ActionEvent event) {
                if (user == null){
                        return;
                }
                boolean validationSuccessful =writeToUserDTO();
                if (!validationSuccessful){
                        return;
                }
                if (validationSuccessful){
                        //eventBus.post(new RequestU)
                        ((Button)event.getSource()).getScene().getWindow().hide();
                }


        }

        private UserDTO user;

        public void edit (UserDTO user){
                this.user =user;
                textfieldChangeEmail.setText((user.getEmail()));
                textfieldFornMeLastname.setText(user.getForename());
                labelChangeUsername.setText(user.getUsername());
                lableChangeRole.setText(user.getRole().name());
                textfieldLastname.setText(user.getLastname());
        }

        @FXML
        void handleExitButton(ActionEvent event) {
            ((Button) event.getSource()).getScene().getWindow().hide();
        }

        private boolean writeToUserDTO(){
                FormValidator userValidator = new FormValidator();



                return userValidator.validationSuccessful();
        }

//
//        if (!isEditMode()) {
//            eventBus.post(new RequestAddUniversityEvent(university));
//        } else {
//            eventBus.post(new RequestUpdateUniversityEvent(university));
//        }
//
//        ((Button)event.getSource()).getScene().getWindow().hide();
//
//    }
//    private boolean writeToUniversityDTO(){
//
//        FormValidator universityValidator = new FormValidator();
//
//        String name = textFieldNameOfUniverity.getText();
//        String location =textFieldLocation.getText();
//        String country = textFieldCountry.getText();
//        String shortForm =textFieldShortForm.getText();
//
//        if(universityValidator.textFieldNotEmpty(textFieldNameOfUniverity)){
//            university.setName(name);
//        }
//        if(universityValidator.textFieldNotEmpty(textFieldCountry)){
//            university.setCountry(country);
//
//        }
//        if(universityValidator.textFieldNotEmpty(textFieldLocation)){
//            university.setLocation(location);
//
//        }
//        university.setAbbreviation(shortForm);
//
//        return universityValidator.validationSuccessful();
//
//
//    }
//
//    @FXML
//    void handleCancelButton(ActionEvent event) {
//        ((Button) event.getSource()).getScene().getWindow().hide();
//
//    }
//}


}
