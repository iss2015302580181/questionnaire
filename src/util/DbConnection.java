package util;

import java.sql.*;

/**
 * Created by wzzz on 2019/3/13.
 */
public class DbConnection {
    private static String userName = "user1";
    private static String userPsd = "QAZqaz123";
    private static String url = "jdbc:mysql://localhost:3306/db1";
    private Connection connection;

    public DbConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, userName, userPsd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
            return connection;
    }

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static ResultSet executQuery(String sql, Object[] param) {
//        Connection conn = getConnection();
//        PreparedStatement pstmt = null;
//        ResultSet result = null;
//        try {
//            pstmt = conn.prepareStatement(sql);
//            if (param != null) {
//                for (int i = 0; i < param.length; i++) {
//                    pstmt.setObject(i + 1, param[i]);
//                }
//            }
//            result = pstmt.executeQuery();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
}
