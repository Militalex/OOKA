package org.hbrs.ooka.uebung1.component;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    public static final String TABLE_NAME = "products";
    private final Connection connection;

    public ProductRepository(Connection connection) throws SQLException {
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

    public void addProduct(@NotNull Product product) throws SQLException {
        PreparedStatement pstmt = this.connection.prepareStatement(
                "INSERT INTO " + TABLE_NAME + " (name, price) VALUES (?, ?)");
        pstmt.setString(1, product.getName());
        pstmt.setDouble(2, product.getPrice());
        pstmt.executeUpdate();
    }

    public boolean contains(Product product) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM " + TABLE_NAME + " WHERE name=" + product.getName());
        ResultSet rs = pstmt.executeQuery();
        return rs.next();
    }

    public Product[] getProductByName(String name) throws SQLException {
        return getProductBySql("SELECT * FROM " + TABLE_NAME + " WHERE name=" + name);
    }

    public Product[] getProductByPrice(double price) throws SQLException {
        return getProductBySql("SELECT * FROM " + TABLE_NAME + " WHERE price = ?");
    }

    public Product[] getAllProducts() throws SQLException {
        return getProductBySql("SELECT * FROM " + TABLE_NAME);
    }

    public Product[] deleteProductByName(String name) throws SQLException {
        Product[] deletedProducts = getProductByName(name);

        PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM " + TABLE_NAME + " WHERE name = ?;");
        pstmt.setString(1, name);
        pstmt.executeQuery();

        return deletedProducts;
    }

    public Product[] deleteProductByPrice(double price) throws SQLException {
        Product[] deletedProducts = getProductByPrice(price);

        PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM " + TABLE_NAME + " WHERE price = ?;");
        pstmt.setDouble(1, price);
        pstmt.executeQuery();

        return deletedProducts;
    }

    public Product[] deleteAll() throws SQLException {
        Product[] deletedProducts = getAllProducts();

        connection.createStatement().execute(
                "TRUNCATE FROM " + TABLE_NAME + ";");

        return deletedProducts;
    }

    private Product[] getProductBySql(String sql) throws SQLException {
        List<Product> productList = new ArrayList<>();

        PreparedStatement pstmt = this.connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            productList.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
            ));
        }
        Product[] productArray = new Product[productList.size()];
        productList.toArray(productArray);
        return productArray;
    }
}
