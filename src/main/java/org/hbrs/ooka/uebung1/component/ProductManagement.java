package org.hbrs.ooka.uebung1.component;

import org.hbrs.ooka.uebung1.interfaces.ICaching;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductManagement {

    private final ProductRepository repository;
    private @Nullable ICaching<List<Product>> cache;

    public ProductManagement(Connection connection) throws SQLException {
        repository = new ProductRepository(connection);
        fetchCache();
    }

    public void fetchCache(){
        cache = PortProductManagement.getCache();
    }

    public class ProductController {

        public void addProduct(@NotNull Product product) throws SQLException {
            fetchCache();
            if (cache != null){
                // Put product into cache by name
                if (cache.isKeyOccupied(product.getName())){
                    cache.readResult(product.getName()).add(product);
                }
                else  {
                    cache.cacheResult(product.getName(), new ArrayList<>(List.of(product)));
                }

                // Put product into cache by price
                if (cache.isKeyOccupied("" + product.getPrice())){
                    cache.readResult("" + product.getPrice()).add(product);
                }
                else {
                    cache.cacheResult("" + product.getPrice(), new ArrayList<>(List.of(product)));
                }
            }
            repository.addProduct(product);
        }

        public boolean contains(Product product) throws SQLException {
            fetchCache();
            if (cache != null && cache.isKeyOccupied(product.getName()) && cache.readResult(product.getName()).contains(product)){
                return true;
            }
            return repository.contains(product);
        }

        public List<Product> getProductsByName(String name) throws SQLException {
            fetchCache();
            if (cache != null && cache.isKeyOccupied(name)){
                return cache.readResult(name);
            }
            List<Product> productList = repository.getProductsByName(name);
            if (cache != null){
                cache.cacheResult(name, productList);
            }
            return productList;
        }

        public List<Product> getProductsByPrice(double price) throws SQLException {
            fetchCache();
            if (cache != null && cache.isKeyOccupied("" + price)){
                return cache.readResult("" + price);
            }
            List<Product> productList = repository.getProductsByPrice(price);
            if (cache != null) {
                cache.cacheResult("" + price, productList);
            }
            return productList;
        }

        public List<Product> getAllProducts() throws SQLException {
            fetchCache();
            return repository.getAllProducts();
        }

        public List<Product> deleteProductsByName(String name) throws SQLException {
            fetchCache();
            if (cache != null && cache.isKeyOccupied(name)){
                List<Product> productList = cache.takeResult(name);
                productList.stream().map(Product::getPrice).forEach(price -> cache.takeResult("" + price));
            }
            return repository.deleteProductsByName(name);
        }

        public List<Product> deleteProductsByPrice(double price) throws SQLException {
            fetchCache();
            if (cache != null && cache.isKeyOccupied("" + price)){
                List<Product> productList = cache.takeResult("" + price);
                productList.stream().map(Product::getName).forEach(name -> cache.takeResult(name));
            }
            return repository.deleteProductsByPrice(price);
        }

        public List<Product> deleteAll() throws SQLException {
            fetchCache();
            if (cache != null){
                cache.clearCache();
            }
            return repository.deleteAll();
        }
    }
}
