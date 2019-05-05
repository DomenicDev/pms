package de.hfu.pms;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.hfu.pms.events.LoginRequestEvent;
import de.hfu.pms.exceptions.LoginFailedException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;

public class GuiEventHandler {

    private EventBus eventBus;

    private ApplicationServices applicationServices;

    private Stage primaryStage;

    private HashMap<String, Node> scenes = new HashMap<>();

    public GuiEventHandler(Stage primaryStage, ApplicationServices applicationServices, EventBus eventBus) {
        this.primaryStage = primaryStage;

        this.eventBus = eventBus;
        this.eventBus.register(this);

        this.applicationServices = applicationServices;
    }

    @Subscribe
    public void handleLoginEvent(LoginRequestEvent loginRequestEvent) {
        // extract credentials from request
        String username = loginRequestEvent.getUsername();
        String password = loginRequestEvent.getPwHash();

        try {
            // try to login with specified username and password
            //applicationServices.login(username, password);

            // login was successful, so we can close the login screen and open the dashboard
            primaryStage.close();

            // todo: replace the following event with event
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newDashboardDesign.fxml"));
            try {
                Stage newStage = new Stage(StageStyle.DECORATED);

                Parent dashboard = loader.load();
                Scene scene = new Scene(dashboard);
                newStage.setScene(scene);
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (LoginFailedException e) {
            // todo: show prompt which shows error message failed login
            e.printStackTrace();
        }


    }

}
