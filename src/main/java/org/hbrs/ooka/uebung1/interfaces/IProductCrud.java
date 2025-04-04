package org.hbrs.ooka.uebung1.interfaces;

import org.hbrs.ooka.uebung1.component.Product;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Contains the CRUD methods of the Product Management System.
 */
public interface IProductCrud {
    // Auswahl von CRUD-Methoden (weitere können hinzugefügt werden)
    void addProduct(@NotNull Product product);
    boolean contains(Product product);
    Product[] getProductByName(String name);
    Product[] getProductByPrice(double price);

    /**
     * Deletes matching product with the lowest id.
     * @return Returns the deleted product.
     */
    Product[] deleteProductByName(String name);

    /**
     * Deletes matching product with the lowest id.
     * @return Returns the deleted product.
     */
    Product[] deleteProductByPrice(double price);
}
