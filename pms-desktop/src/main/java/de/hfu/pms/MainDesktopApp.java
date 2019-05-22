package de.hfu.pms;

import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.handler.GuiEventHandler;
import de.hfu.pms.service.ApplicationServiceImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.ResourceBundle;


public class MainDesktopApp extends Application {

    Stage window;
    Scene login, dashboard;

    private Logger logger = Logger.getLogger(MainDesktopApp.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.log(Level.INFO, "Starting GUI...");

        ResourceBundle bundle = ResourceBundle.getBundle("lang/strings");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/screens/dashboard_final.fxml"));
        loader.setResources(bundle);
        Parent root = loader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root);
        window =primaryStage;

        primaryStage.setScene(scene);
        primaryStage.show();

        GuiEventHandler guiEventHandler = new GuiEventHandler(primaryStage,new ApplicationServiceImpl(), EventBusSystem.getEventBus());
    }


    public static void main(String[] args) {
        // load log4j properties file
        PropertyConfigurator.configure(MainDesktopApp.class.getClassLoader().getResource("conf/log4j.properties"));

        Application.launch(args);
    }

    public class ExitButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent arg0) {
            Platform.exit();
        }

    }
}
