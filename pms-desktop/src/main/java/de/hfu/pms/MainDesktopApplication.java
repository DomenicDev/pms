package de.hfu.pms;

import de.hfu.pms.eventbus.EventBusSystem;
import de.hfu.pms.handler.GuiEventHandler;
import de.hfu.pms.service.ApplicationServiceImpl;
import de.hfu.pms.service.ApplicationServices;
import de.hfu.pms.utils.GuiLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

/**
 * This is the starting point of the desktop application.
 */
public class MainDesktopApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // show application services
        ApplicationServices applicationServices = new ApplicationServiceImpl();
        GuiEventHandler eventHandler = new GuiEventHandler(primaryStage, applicationServices, EventBusSystem.getEventBus());
        eventHandler.start();

        // show login screen
        GuiLoader.showLoginScreen(primaryStage);
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(MainDesktopApplication.class.getClassLoader().getResource("conf/log4j.properties"));
        Application.launch(args);
    }
}
