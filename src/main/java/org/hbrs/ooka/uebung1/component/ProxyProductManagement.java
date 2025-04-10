package org.hbrs.ooka.uebung1.component;

import org.hbrs.ooka.uebung1.interfaces.IProductManagement;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxyProductManagement implements IProductManagement {
    private Logger logger = Logger.getLogger("ProductManagement");
    private Connection connection;
    private ProductManagement.ProductController controller;
    @Override
    public void openSession() {
        // Logging
        logger.log(Level.INFO, "Zugriff auf ProductManagement über Methode openSession.");

        // Session already opened
        if (connection != null){
            logger.log(Level.WARNING, "Eine Session ist bereits geöffnet. Keine neue Session konnte geöffnet werden.");
            return;
        }

        // Create new Session
        try {
            ProductManagement productManagement = new ProductManagement(connection = DatabaseConnection.getConnection());
            controller = productManagement.new ProductController();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Die Session konnte nicht geöffnet werden wegen eines Errors. Siehe folgende Fehlermeldung:", e);
        }
    }

    @Override
    public void closeSession() {
        // Logging
        logger.log(Level.INFO, "Zugriff auf ProductManagement über Methode closeSession.");

        // Session not opened
        if (connection == null){
            logger.log(Level.WARNING, "Keine zu schließende Session ist geöffnet.");
            return;
        }

        // Session schließen
        try {
            connection.close();
            connection = null;
            controller = null;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Die Session konnte nicht geschlossen werden. Siehe folgende Fehlermeldung: " + e.getMessage(), e);
        }
    }

    @Override
    public void addProduct(@NotNull Product product) {
        // Logging
        logger.log(Level.INFO, "Zugriff auf ProductManagement über Methode addProduct. " +
                "\nProdukt: " + product);
    }

    @Override
    public boolean contains(Product product) {
        // Logging
        logger.log(Level.INFO, "Zugriff auf ProductManagement über Methode contains. " +
                "\nProdukt: " + product);

        return false;
    }

    @Override
    public Product[] getProductByName(String name) {
        // Logging
        logger.log(Level.INFO, "Zugriff auf ProductManagement über Methode getProduct. " +
                "\nSuchwort: " + name);
        return null;
    }

    @Override
    public Product[] getProductByPrice(double price) {
        // Logging
        logger.log(Level.INFO, "Zugriff auf ProductManagement über Methode getProduct. " +
                "\nPreis: " + price);
        return null;
    }

    @Override
    public Product[] deleteProductByName(String name) {
        // Logging
        logger.log(Level.INFO, "Zugriff auf ProductManagement über Methode deleteProduct. " +
                "\nSuchwort: " + name);
        return null;
    }

    @Override
    public Product[] deleteProductByPrice(double price) {
        // Logging
        logger.log(Level.INFO, "Zugriff auf ProductManagement über Methode deleteProduct. " +
                "\nPreis: " + price);
        return null;
    }
}
