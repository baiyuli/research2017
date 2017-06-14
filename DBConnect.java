import java.sql.*;

public class DBConnect {
    public static void main (String[] args) {
      try {
        String host = "little.cs.pomona.edu";
        String username = "gwu";
        String password = "";
        Connection con = DriverManager.getConnection(host, username, password);
      }
      catch (SQLException err) {
        System.out.println(err.getMessage());
      }

    }
}
