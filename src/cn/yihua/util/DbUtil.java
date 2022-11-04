package cn.yihua.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbUtil {
    private static Connection connection;
    private static final String URL = "jdbc:mysql://niyihua.top:3306/httpserverjdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "*****";
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }
}
