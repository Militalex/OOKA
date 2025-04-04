package org.hbrs.ooka.uebung1.component;

import java.sql.*;

public class DatabaseConnection {
        private static final String URL = "jdbc:h2:~/test";
        private static final String USER = "sa";
        private static final String PASSWORD = "";

        public static Connection getConnection() throws SQLException {
                DriverManager.registerDriver(new org.h2.Driver());
                return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        public static boolean doesTableExist(Connection connection, String tableName) throws SQLException {
                DatabaseMetaData meta = connection.getMetaData();
                try (ResultSet rs = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
                        return rs.next();
                }
        }

        public static void deleteTable(Connection connection, String tableName) throws SQLException {
                connection.createStatement().execute("DROP TABLE products");
        }
}


