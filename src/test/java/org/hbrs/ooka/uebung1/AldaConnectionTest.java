package org.hbrs.ooka.uebung1;

import org.hbrs.ooka.uebung1.component.DatabaseConnection;
import org.hbrs.ooka.uebung1.component.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AldaConnectionTest {

    private Connection connection;

    @BeforeEach
    public void setup()  {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            connection = databaseConnection.getConnection();

            String sql = "CREATE TABLE IF NOT EXISTS products ("
                + "id INT PRIMARY KEY AUTO_INCREMENT, "
                + "name VARCHAR(255) NOT NULL, "
                + "price DOUBLE NOT NULL)";

            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            System.out.println("Table created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void roundTrip() {
        // Erzeugung eines neuen Produkts (CREATE)
        Product productTarget = insertProduct();

        // Lesen des Produkts aus der Datenbank (READ)
        List<Product> products = readProducts();
        Product productActual = products.get(0);

        // Vergleich des Produkts mit dem erwarteten Produkt (Assertion)
        assertEquals(productTarget, productActual);
    }

    @AfterEach
    public void deleteSuff() {
        // SQL für die Löschung der Tabelle (Vermeidung von Datenmüll)
        // String sql = "DROP TABLE products"; --> ToDo bei Ihnen ;-)
        try {
            // Diese Verbindung könnte im Rahmen der Übung Nr. 1 auch über die Methode closeConnection() geschlossen werden
            // (vgl. Interface ProductManagementInt)
            DatabaseConnection.deleteTable(connection, "products");
            this.connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
        }
    }

    private List<Product> readProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try {
            PreparedStatement pstmt = this.connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    private Product insertProduct() {
        String sql1 = "INSERT INTO products (name, price) VALUES (?, ?)";
        Product productTarget = new Product(1, "My Motor 1.0", 100.0);

        try {
            PreparedStatement pstmt = this.connection.prepareStatement(sql1);
            pstmt.setString(1, productTarget.getName());
            pstmt.setDouble(2, productTarget.getPrice());
            pstmt.executeUpdate();
            System.out.println("Product inserted successfully.");
        } catch (SQLException e2) {
            e2.printStackTrace();
        }

        return productTarget;
    }
}