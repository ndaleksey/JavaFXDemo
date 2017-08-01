package sample.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Shishkov A.V. on 24.07.2017.
 */
public class Notifier extends Thread {
    private Connection conn;
    private String channelName;

    public Notifier(Connection conn, String channelName) {
        this.conn = conn;
        this.channelName = channelName;
    }

    public void run() {
        while (true) {
            try {
                Statement stmt = conn.createStatement();
                stmt.execute(String.format("NOTIFY %s", channelName));
                stmt.close();
                Thread.sleep(2000);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}