package sample.db;

import javafx.application.Platform;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * Created by Shishkov A.V. on 01.08.2017.
 */
public class Writer extends Thread {

    private Connection connection;
    private Statement statement;
    private ObservableList<String> logs;

    public Writer(Connection connection, ObservableList<String> logs) throws SQLException {
        this.connection = connection;
        this.logs = logs;
        statement = connection.createStatement();
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                UUID id = UUID.randomUUID();
                statement.execute("INSERT INTO car.car (id, name, color, year) " +
                        String.format("VALUES ('%s', 'Porsche', 'Черный', 2017)", id.toString()));
                displayLogs(String.format("Новая запись c id = %s добавлена", id.toString()));
                Thread.sleep(1000);
            } catch (SQLException e) {
                e.printStackTrace();
                interrupt();
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }

    private void displayLogs(String s) {
        Platform.runLater(() -> logs.add(s));
    }
}