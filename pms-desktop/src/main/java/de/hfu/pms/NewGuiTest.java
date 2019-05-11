package de.hfu.pms;

import de.hfu.pms.utils.GuiLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

public class NewGuiTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = GuiLoader.loadFXML("/screens/dashboard_final.fxml");
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure(MainDesktopApp.class.getClassLoader().getResource("conf/log4j.properties"));
        Application.launch(args);
    }
}
