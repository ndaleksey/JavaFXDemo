package sample.db;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * Created by Shishkov A.V. on 24.07.2017.
 */
public class Listener extends Thread {
    private PGConnection pgConn;
    private ObservableList<String> logs;

    public Listener(Connection conn, String channelName, ObservableList<String> logs) throws SQLException {
        this.logs = logs;

        this.pgConn = conn.unwrap(org.postgresql.PGConnection.class);
        Statement stmt = conn.createStatement();
        stmt.execute(String.format("LISTEN %s", channelName));
        stmt.close();
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println(isInterrupted());
                PGNotification notifications[] = pgConn.getNotifications();

                if (notifications != null) {
                    for (int i = 0; i < notifications.length; i++) {
                        String parameter = notifications[i].getParameter();
                        displayLogs(parseNotificationParam(parameter));
                        System.out.println(parameter);
                    }
                }

//                System.out.println("Сервер слушает");
                Thread.sleep(500);
            } catch (IllegalArgumentException iae) {
                new Alert(Alert.AlertType.ERROR, "Ошибка чтения id из уведомления. Приложение будет остановлено");
                interrupt();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
                interrupt();
            } catch (InterruptedException ie) {
                interrupt();
            }
        }
    }

    private void displayLogs(String s) {
        Platform.runLater(() -> logs.add(s));
    }

    @NotNull
    private String parseNotificationParam(String parameter) {
        if (parameter.isEmpty()) return "";

        String[] parts = parameter.split(" ");

        if (parts.length != 3) return "";

        UUID id = UUID.fromString(parts[0]);

        return id.toString();
    }
}