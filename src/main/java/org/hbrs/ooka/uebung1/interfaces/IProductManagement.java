package org.hbrs.ooka.uebung1.interfaces;

import org.hbrs.ooka.uebung1.component.Product;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Spezifikation des Interfaces ProductManagementInt:
 * 1. Zunächst MUSS ein externer Client (außerhalb der Komponente!) mit der Methode
 * openConnection() eine Session explizit öffnen!
 * 2. Methoden zur Suche, Einfügen usw. können beliebig ausgeführt werden.
 * 3. Dann MUSS ein externer Client mit der Methode closeConnection() die Session explizit schließen!
 */

public interface IProductManagement{
    // Lifecycle-Methoden (dürfen nicht verändert werden, siehe Spezifikation im Kommentar)

    /**
     * Open a session (here the connection to the database should be established)
     */
    void openSession();

    /**
     * Close a session (here the connection to the database should be closed)
     * @param dropProducts Determines whether the products should be deleted when closing the session or if they should be persistent.
     */
    void closeSession(boolean dropProducts);
    void addProduct(@NotNull Product product);
    boolean contains(Product product);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByPrice(double price);
    List<Product> getAllProducts();
    List<Product> deleteProductsByName(String name);
    List<Product> deleteProductsByPrice(double price);
    List<Product> deleteAll();
}