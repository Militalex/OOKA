package org.hbrs.ooka.uebung1.interfaces;

import org.hbrs.ooka.uebung1.component.Product;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Contains the CRUD methods of the Product Management System.
 */
public interface IProductCrud {
    void addProduct(@NotNull Product product);
    boolean contains(Product product);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByPrice(double price);
    List<Product> deleteProductsByName(String name);
    List<Product> deleteProductsByPrice(double price);

    List<Product> deleteAll();
}
