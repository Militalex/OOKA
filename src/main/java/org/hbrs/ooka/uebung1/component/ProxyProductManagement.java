package org.hbrs.ooka.uebung1.component;

import org.hbrs.ooka.uebung1.interfaces.IProductManagement;
import org.hbrs.ooka.uebung2_3.services.logger.ILogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.*;

public class ProxyProductManagement implements IProductManagement {

    @NotNull
    private final ILogger logger;
    private Connection connection;
    private ProductManagement.ProductController controller;

    ProxyProductManagement(){
        logger = Objects.requireNonNullElse(PortProductManagement.getLogger(), new ILogger() {
            private Logger logger = Logger.getLogger("ProductManagement");
            @Override
            public void sendLog(Level level, String s, @Nullable Throwable throwable) {
                logger.log(level, s, throwable);
            }
        });
    }

    @Override
    public void openSession() {
        // Logging
        logger.sendLog(Level.INFO, "Zugriff auf ProductManagement über Methode openSession.");

        // Session already opened
        if (isSessionOpen()){
            logger.warning("Eine Session ist bereits geöffnet. Keine neue Session konnte geöffnet werden.");
            return;
        }

        if (PortProductManagement.isCacheEmpty()){
            logger.warning("Cache ist nicht gesetzt worden. Es wird empfohlen einen Cache zu setzen, damit Datenbankzugriffe" +
                    "mittels des Caches minimiert werden können.");
        }

        // Create a new Session
        try {
            ProductManagement productManagement = new ProductManagement(connection = DatabaseConnection.getConnection());
            controller = productManagement.new ProductController();
            logger.info("Eine neue Session wurde erfolgreich geöffnet.");
        } catch (SQLException e) {
            logger.severe("Die Session konnte nicht geöffnet werden wegen eines Errors. \nSiehe folgende Fehlermeldung: ", e);
        }
    }

    private boolean isSessionOpen(){
        return connection != null;
    }

    @Override
    public void closeSession(boolean dropTable) {
        // Logging
        logger.info("Zugriff auf ProductManagement über Methode closeSession.");

        // Session isn't opened
        if (!isSessionOpen()){
            logger.warning("Keine zu schließende Session ist geöffnet.");
            return;
        }

        // Drop table
        if (dropTable){
            try {
                DatabaseConnection.deleteTable(connection, ProductRepository.TABLE_NAME);
            } catch (Exception e) {
                logger.warning("Die Produkt Tabelle konnte nicht entfernt werden. Versuche die Produkte manuell " +
                        "zu entfernen. \nSiehe folgende Fehlermeldung: ", e);
                deleteAll();
            }
        }

        // Session schließen
        try {
            connection.close();
            connection = null;
            controller = null;
            logger.info("Die Session konnte erfolgreich geschlossen werden.");
        } catch (Exception e) {
            logger.severe("Die Session konnte nicht geschlossen werden. \nSiehe folgende Fehlermeldung: ", e);
        }
    }

    @Override
    public void addProduct(@NotNull Product product) {
        // Logging
        logger.info("Zugriff auf ProductManagement über Methode addProduct. " +
                "\nProdukt: " + product);

        // Session isn't opened
        if (!isSessionOpen()){
            logger.severe("Aktion kann nicht durchführt werden, da keine Session geöffnet ist.");
            return;
        }

        // Error Handling
        try {
            controller.addProduct(product);
        } catch (Exception e) {
            logger.severe("Das Produkt " + product.getName() + " mit dem Preis " + product.getPrice() +
                    " konnte nicht hinzugefügt werden. \nSiehe die folgende Fehlermeldung: ", e);
        }
    }

    @Override
    public boolean contains(Product product) {
        // Logging
        logger.info("Zugriff auf ProductManagement über Methode contains. " +
                "\nProdukt: " + product);

        // Session isn't opened
        if (!isSessionOpen()){
            logger.severe("Aktion kann nicht durchführt werden, da keine Session geöffnet ist. Gebe false zurück... .");
            return false;
        }

        // Error Handling
        try {
            return controller.contains(product);
        } catch (Exception e) {
            logger.severe("Das Produkt " + product.getName() + " mit dem Preis " + product.getPrice() +
                    " konnte nicht auf Verfügbarkeit geprüft werden. Es wird false zurückgegeben... . \nSiehe die folgende Fehlermeldung: ", e);
            return false;
        }
    }

    @Override
    public List<Product> getProductsByName(String name) {
        // Logging
        logger.info("Zugriff auf ProductManagement über Methode getProduct. " +
                "\nSuchwort: " + name);

        // Session isn't opened
        if (!isSessionOpen()){
            logger.severe("Aktion kann nicht durchführt werden, da keine Session geöffnet ist. Gebe leere Liste zurück... .");
            return Collections.emptyList();
        }

        // Error Handling
        try{
            return controller.getProductsByName(name);
        } catch (Exception e){
            logger.severe("Das Produkt " + name + " konnte aufgrund eines Fehlers nicht in der Datenbank " +
                    "gefunden werden. Es wird eine leere Liste zurückgegeben... . \nSiehe die folgende Fehlermeldung: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Product> getProductsByPrice(double price) {
        // Logging
        logger.info("Zugriff auf ProductManagement über Methode getProduct. " +
                "\nPreis: " + price);

        // Session isn't opened
        if (!isSessionOpen()){
            logger.severe("Aktion kann nicht durchführt werden, da keine Session geöffnet ist. Gebe leere Liste zurück... .");
            return Collections.emptyList();
        }

        // Error Handling
        try{
            return controller.getProductsByPrice(price);
        } catch (Exception e){
            logger.severe("Das Produkt mit dem Preis " + price + " konnte aufgrund eines Fehlers nicht in der Datenbank " +
                    "gefunden werden. Es wird eine leere Liste zurückgegeben... . \nSiehe die folgende Fehlermeldung: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Product> getAllProducts() {
        // Logging
        logger.info("Zugriff auf ProductManagement über Methode getAllProducts. ");

        // Session isn't opened
        if (!isSessionOpen()){
            logger.severe("Aktion kann nicht durchführt werden, da keine Session geöffnet ist. Gebe leere Liste zurück... .");
            return Collections.emptyList();
        }

        // Error Handling
        try{
            return controller.getAllProducts();
        } catch (Exception e){
            logger.severe("Die Produkte konnten aufgrund eines Fehlers nicht aus der Datenbank " +
                    "abgefrufen werden. Es wird eine leere Liste zurückgegeben... . \nSiehe die folgende Fehlermeldung: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Product> deleteProductsByName(String name) {
        // Logging
        logger.info("Zugriff auf ProductManagement über Methode deleteProduct. " +
                "\nSuchwort: " + name);

        // Session isn't opened
        if (!isSessionOpen()){
            logger.severe("Aktion kann nicht durchführt werden, da keine Session geöffnet ist. Gebe leere Liste zurück... .");
            return Collections.emptyList();
        }

        // Error Handling
        try{
            return controller.deleteProductsByName(name);
        } catch (Exception e){
            logger.severe("Das Produkt " + name + " konnte aufgrund eines Fehlers nicht in der Datenbank " +
                    "gefunden werden. Das Produkt konnte nicht gelöscht werden und es wird eine leere Liste zurückgegeben... . " +
                    "\nSiehe die folgende Fehlermeldung: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Product> deleteProductsByPrice(double price) {
        // Logging
        logger.info("Zugriff auf ProductManagement über Methode deleteProduct. " +
                "\nPreis: " + price);

        // Session isn't opened
        if (!isSessionOpen()){
            logger.severe("Aktion kann nicht durchführt werden, da keine Session geöffnet ist. Gebe leere Liste zurück... .");
            return Collections.emptyList();
        }

        // Error Handling
        try{
            return controller.deleteProductsByPrice(price);
        } catch (Exception e){
            logger.severe("Das Produkt mit dem Preis " + price + " konnte aufgrund eines Fehlers nicht in der Datenbank " +
                    "gefunden werden. Das Produkt konnte nicht gelöscht werden und es wird eine leere Liste zurückgegeben... . " +
                    "\nSiehe die folgende Fehlermeldung: ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Product> deleteAll() {
        // Logging
        logger.info("Zugriff auf ProductManagement über Methode deleteAll. ");

        // Session isn't opened
        if (!isSessionOpen()){
            logger.severe("Aktion kann nicht durchführt werden, da keine Session geöffnet ist. Gebe leere Liste zurück... .");
            return Collections.emptyList();
        }

        // Error handling
        try {
            return controller.deleteAll();
        } catch (Exception e) {
            logger.severe("Es konnten nicht alle Produkte entfernt werden. Gebe leere Liste zurück... ." +
                    " \nSiehe die folgende Fehlermeldung: ", e);
            return Collections.emptyList();
        }
    }
}
