package org.hbrs.ooka.uebung1.component;

import org.hbrs.ooka.uebung1.interfaces.Caching;
import org.hbrs.ooka.uebung1.interfaces.IProductCrud;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ProductController implements Caching, IProductCrud {

    private final HashMap<String, List<Object>> cacheMap = new HashMap<>();

    @Override
    public boolean isKeyOccupied(String key) {
        return cacheMap.containsKey(key);
    }

    @Override
    public Set<String> getAvailableKeys() {
        return cacheMap.keySet();
    }

    @Override
    public void cacheResult(String key, List<Object> value) {
        if (isKeyOccupied(key))
            throw new IllegalStateException(key + " is already occupied.");
        cacheMap.put(key, value);
    }

    @Override
    public List<Object> takeResult(String key) {
        if (!isKeyOccupied(key))
            throw new IllegalArgumentException(key + " is not occupied.");
        List<Object> result = cacheMap.get(key);
        cacheMap.remove(key);
        return result;
    }

    @Override
    public void clearCache() {
        cacheMap.clear();
    }

    @Override
    public void addProduct(@NotNull Product product) {

    }

    @Override
    public boolean contains(Product product) {
        return false;
    }

    @Override
    public Product[] getProductByName(String name) {
        return new Product[0];
    }

    @Override
    public Product[] getProductByPrice(double price) {
        return new Product[0];
    }

    @Override
    public Product[] deleteProductByName(String name) {
        return new Product[0];
    }

    @Override
    public Product[] deleteProductByPrice(double price) {
        return new Product[0];
    }
}
