package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.Controller;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/sample.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 400));

        primaryStage.setOnCloseRequest(e->{
            Controller controller = loader.getController();
            controller.shutdown();
        });

        primaryStage.show();

        /*Properties properties = new Properties();
        properties.setProperty("url", "jdbc:postgresql://localhost:5432/test");
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "postgres");
        properties.storeToXML(new FileOutputStream("settings.xml"), "Настройки приложения", "Unicode");*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
