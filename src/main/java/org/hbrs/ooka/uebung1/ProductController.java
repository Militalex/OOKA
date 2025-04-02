package org.hbrs.ooka.uebung1;

import org.hbrs.ooka.uebung1.entities.Product;

import java.util.List;

public class ProductController implements ProductManagementInt, CachingInt {
    @Override
    public void cacheResult(String key, List<Object> value) {

    }

    @Override
    public List<Product> getProductByName(String name) {
        return null;
    }

    @Override
    public void openSession() {

    }

    @Override
    public void closeSession() {

    }
}
