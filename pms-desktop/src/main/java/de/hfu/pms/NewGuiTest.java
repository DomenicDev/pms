package de.hfu.pms;

import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.handler.GuiEventHandler;
import de.hfu.pms.service.ApplicationServiceImpl;
import de.hfu.pms.service.ApplicationServices;
import de.hfu.pms.utils.GuiLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.PropertyConfigurator;

public class NewGuiTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        ApplicationServices applicationServices = new ApplicationServiceImpl();

        GuiEventHandler eventHandler = new GuiEventHandler(primaryStage, applicationServices, EventBusSystem.getEventBus());
   //     applicationServices.initEntityPool();
   //     EntityPool.getInstance().setApplicationServices(applicationServices);

        Parent login = GuiLoader.loadFXML("/screens/login.fxml");
        Scene loginScene = new Scene(login);
        loginScene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        primaryStage.setScene(loginScene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

       // applicationServices.login("admin", "1234");


        /*
        Parent root = GuiLoader.loadFXML("/screens/dashboard_final.fxml");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        primaryStage.setScene(scene);
        //primaryStage.setMaximized(true);
        primaryStage.show();

         */
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(MainDesktopApp.class.getClassLoader().getResource("conf/log4j.properties"));
        Application.launch(args);


    }
}
