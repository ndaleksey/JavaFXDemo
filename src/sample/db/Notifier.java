package sample.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Shishkov A.V. on 24.07.2017.
 */
public class Notifier extends Thread {
    private Connection conn;

    public Notifier(Connection conn) {
        this.conn = conn;
    }

    public void run() {
        while (true) {
            try {
                Statement stmt = conn.createStatement();
                stmt.execute("NOTIFY test_channel");
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