package sample.db;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Shishkov A.V. on 24.07.2017.
 */
public class Listener  extends Thread {
    private Connection conn;
    private PGConnection pgconn;

    public Listener(Connection conn) throws SQLException {
        this.conn = conn;
        this.pgconn = conn.unwrap(org.postgresql.PGConnection.class);
        Statement stmt = conn.createStatement();
        stmt.execute("LISTEN test_channel");
        stmt.close();
    }

    public void run() {
        while (true) {
            try {
                // issue a dummy query to contact the backend
                // and receive any pending notifications.

                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT 1");
                rs.close();
                stmt.close();

                PGNotification notifications[] = pgconn.getNotifications();

                if (notifications != null) {
                    for (int i = 0; i < notifications.length; i++)
                        System.out.println("Got notification: " + notifications[i].getName());
                }

                // wait a while before checking again for new
                // notifications

                Thread.sleep(500);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}