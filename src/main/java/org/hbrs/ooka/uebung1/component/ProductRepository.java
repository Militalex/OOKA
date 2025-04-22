package org.hbrs.ooka.uebung1.component;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    public static final String TABLE_NAME = "products";
    private final Connection connection;

    ProductRepository(Connection connection) throws SQLException {
        this.connection = connection;

        // Create table if not existent
        if (!DatabaseConnection.doesTableExist(connection, TABLE_NAME)) {
            connection.createStatement().execute(
                    "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                            + "id INT PRIMARY KEY AUTO_INCREMENT, "
                            + "name VARCHAR(255) NOT NULL, "
                            + "price DOUBLE NOT NULL)"
            );
        }
    }

    void addProduct(@NotNull Product product) throws SQLException {
        PreparedStatement pstmt = this.connection.prepareStatement(
                "INSERT INTO " + TABLE_NAME + " (name, price) VALUES (?, ?)");
        pstmt.setString(1, product.getName());
        pstmt.setDouble(2, product.getPrice());
        pstmt.executeUpdate();
    }

    boolean contains(Product product) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM " + TABLE_NAME + " WHERE name=? AND price=?");
        pstmt.setString(1, product.getName());
        pstmt.setDouble(2, product.getPrice());
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    List<Product> getProductsByName(String name) throws SQLException {
        PreparedStatement pstmt = this.connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE name=?");
        pstmt.setString(1, name);
        return getProductsBySql(pstmt);
    }

    List<Product> getProductsByPrice(double price) throws SQLException {
        PreparedStatement pstmt = this.connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE price=?");
        pstmt.setDouble(1, price);
        return getProductsBySql(pstmt);
    }

    List<Product> getAllProducts() throws SQLException {
        PreparedStatement pstmt = this.connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
        return getProductsBySql(pstmt);
    }

    List<Product> deleteProductsByName(String name) throws SQLException {
        List<Product> deletedProducts = getProductsByName(name);

        PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM " + TABLE_NAME + " WHERE name = ?;");
        pstmt.setString(1, name);
        pstmt.execute();

        return deletedProducts;
    }

    List<Product> deleteProductsByPrice(double price) throws SQLException {
        List<Product> deletedProducts = getProductsByPrice(price);

        PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM " + TABLE_NAME + " WHERE price = ?;");
        pstmt.setDouble(1, price);
        pstmt.execute();

        return deletedProducts;
    }

    List<Product> deleteAll() throws SQLException {
        List<Product> deletedProducts = getAllProducts();

        connection.createStatement().execute(
                "TRUNCATE TABLE " + TABLE_NAME + ";");

        return deletedProducts;
    }

    private List<Product> getProductsBySql(PreparedStatement sql) throws SQLException {
        List<Product> productList = new ArrayList<>();

        ResultSet rs = sql.executeQuery();
        while (rs.next()) {
            productList.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
            ));
        }
        return productList;
    }
}
