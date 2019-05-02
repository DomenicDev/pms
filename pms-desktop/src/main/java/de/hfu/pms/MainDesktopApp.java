package de.hfu.pms;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainDesktopApp extends Application {

    Stage window;
    Scene login, dashboard;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        window =primaryStage;
        

        primaryStage.setScene(scene);
        primaryStage.show();

        LoginScreenController loginScreenController = loader.getController();
//        loginScreenController.dump();

        GuiEventHandler guiEventHandler = new GuiEventHandler(primaryStage,null, EventBusSystem.getEventBus());


    }


    public static void main(String[] args) {
        Application.launch(args);
    }

    public class ExitButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            Platform.exit();
        }

    }
}
