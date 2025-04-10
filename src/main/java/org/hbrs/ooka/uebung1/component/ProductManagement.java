package org.hbrs.ooka.uebung1.component;

import org.hbrs.ooka.uebung1.interfaces.ICaching;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductManagement {

    private final ProductRepository repository;
    private @Nullable ICaching cache;

    public ProductManagement(Connection connection) throws SQLException {
        repository = new ProductRepository(connection);
        fetchCache();
    }

    public void fetchCache(){
        cache = Port.getCache();
    }

    public class ProductController {

        public void addProduct(@NotNull Product product) throws SQLException {
            if (cache != null){
                // Put Product into cache
                if (cache.isKeyOccupied(product.toString())){
                    cache.updateResult(product.toString(), List.of(product));
                }
                else {
                    cache.cacheResult(product.toString(), List.of(product));
                }

                // Refresh
                if (cache.isKeyOccupied("Products=" + product.getName())){
                    cache.readResult("Products=" + product.getName()).add(product);
                }
                if (cache.isKeyOccupied("Products=" + product.getPrice())){
                    cache.readResult("Products=" + product.getPrice()).add(product);
                }
            }
            repository.addProduct(product);
        }

        public boolean contains(Product product) throws SQLException {
            if (cache != null && cache.isKeyOccupied(product.toString())){
                return true;
            }
            return repository.contains(product);
        }

        public Product[] getProductByName(String name) {
            if (cache != null && cache.isKeyOccupied("Products=" + name)){
                return
            }

            return new Product[0];
        }

        public Product[] getProductByPrice(double price) {
            return new Product[0];
        }

        public Product[] deleteProductByName(String name) {
            return new Product[0];
        }

        public Product[] deleteProductByPrice(double price) {
            return new Product[0];
        }
    }
}
