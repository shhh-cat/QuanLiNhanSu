package DAL.Eloquent;

import java.sql.*;
import java.util.Properties;

public class Connector {
    private Connection conn;
    private String status = "";
    public Connector() {
        conn = getConnection();
    }

    public Statement getStatement() throws SQLException {
        return conn.createStatement();
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public PreparedStatement getPreparedStatement(String sql, int options) throws SQLException {
        return conn.prepareStatement(sql, options);
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getStatus() {
        return status;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/jdbc_humanresource";
            Properties props = new Properties();
            props.setProperty("user","root");
            props.setProperty("password","");
            props.setProperty("ssl","false");
            conn = DriverManager.getConnection(url, props);
            status= "Connect successfully";
        } catch (Exception e) {
            status= "Connect failure";
            e.printStackTrace();
        }
        return conn;
    }
}
