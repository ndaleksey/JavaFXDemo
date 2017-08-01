package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import sample.db.Listener;
import sample.db.Writer;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Controller {
    @FXML
    private ToggleButton button;

    @FXML
    private ListView listView;

    private Listener listener;
    private Writer writer;
    private ObservableList<String> logs = FXCollections.observableArrayList();

    @FXML
    protected void initialize() {
        listView.setItems(logs);
        button.selectedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue)
//                    startListening();
                    startWriting();
                else
                    stopWriting();
//                    stopListening();
            } catch (Exception e) {
                e.printStackTrace();
                button.setText("Старт");
            }
        });
    }


    public void shutdown() {
        if (listener != null && !listener.isInterrupted())
            listener.interrupt();

        if (writer != null && !writer.isInterrupted())
            writer.interrupt();
    }

    private void startListening() throws ClassNotFoundException, IOException, SQLException {
        Class.forName("org.postgresql.Driver");

        Properties properties = new Properties();
        properties.loadFromXML(new FileInputStream("settings.xml"));
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        Connection lConn = DriverManager.getConnection(url, user, password);
        String channelName = "notification";

        listener = new Listener(lConn, channelName, logs);
        listener.start();

        button.setText("Стоп");
    }

    private void stopListening() {
        listener.interrupt();

        button.setText("Старт");
    }

    private void startWriting() throws IOException, SQLException {
        Properties properties = new Properties();
        properties.loadFromXML(new FileInputStream("settings.xml"));
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        Connection connection = DriverManager.getConnection(url, user, password);

        writer = new Writer(connection, logs);
        writer.start();

        button.setText("Стоп");
    }

    private void stopWriting() {
        writer.interrupt();

        button.setText("Старт");
    }
}