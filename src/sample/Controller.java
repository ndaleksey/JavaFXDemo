package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import sample.db.Listener;
import sample.db.Notifier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Controller {
    @FXML
    private ObservableList<String> logs = FXCollections.observableArrayList();

    @FXML
    protected void testButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Content");
        alert.setHeaderText(null);
        alert.show();
    }

    @FXML
    public void startListenerButtonClick(ActionEvent actionEvent) {
        try {
//            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/test";

            // Create two distinct connections, one for the notifier
            // and another for the listener to show the communication
            // works across connections although this example would
            // work fine with just one connection.

            Connection lConn = DriverManager.getConnection(url, "postgres", "postgres");
            Connection nConn = DriverManager.getConnection(url, "postgres", "postgres");

            // Create two threads, one to issue notifications and
            // the other to receive them.

            Listener listener = new Listener(lConn);
            Notifier notifier = new Notifier(nConn);
            listener.start();
            notifier.start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}