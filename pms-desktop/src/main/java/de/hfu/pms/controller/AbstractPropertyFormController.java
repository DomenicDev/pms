package de.hfu.pms.controller;

import com.google.common.eventbus.EventBus;
import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.events.CreateDocStudentPropertyEvent;
import de.hfu.pms.exceptions.SubmitException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class AbstractPropertyFormController<T> implements Initializable {

    private Logger logger = Logger.getLogger(AbstractPropertyFormController.class);

    /**
     * We use the EventBus to notify about the creation.
     */
    private EventBus eventBus = EventBusSystem.getEventBus();

    /**
     * The entity which is being created or edited in this context.
     */
    T property;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void handleOnActionSubmitButton(ActionEvent event) {
        try {
            submit();
            ((Button)event.getSource()).getScene().getWindow().hide();
        } catch (SubmitException e) {
            logger.log(Level.DEBUG, "Could not submit. Error message: " + e);
        }
    }

    @FXML
    public void handleOnActionCancelButton(ActionEvent event) {
        ((Button)event.getSource()).getScene().getWindow().hide();
    }

    /**
     * This method should read the input data from the gui
     * and write it to the property attribute.
     * @throws IllegalArgumentException if input fields were incorrect or could not be written to the entity
     */
    public abstract void writeProperty() throws IllegalArgumentException;

    /**
     * This methods reads the specified property and sets
     * the gui elements accordingly to the input.<br/>
     *
     * This can be used for editing an already existing entity.
     *
     * @param property the property to read
     */
    public abstract void readProperty(T property);

    /**
     * Submit will call the <code>writeProperty()</code> method and
     * post an {@link CreateDocStudentPropertyEvent} with the set property.
     * @throws SubmitException if submit was not possible, e.g. due to missing input fields
     */
    private void submit() throws SubmitException {
        try {
            writeProperty();
            eventBus.post(new CreateDocStudentPropertyEvent<>(property));
        } catch (IllegalArgumentException e) {
            throw new SubmitException(e.getMessage());
        }

    }

}
