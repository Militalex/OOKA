package org.hbrs.ooka.uebung1;

import org.hbrs.ooka.uebung1.interfaces.ICaching;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class HashMapCache implements ICaching {
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
    public List<Object> readResult(String key) {
        if (!isKeyOccupied(key))
            throw new IllegalArgumentException(key + " is not occupied.");
        return cacheMap.get(key);
    }

    @Override
    public List<Object> takeResult(String key) {
        if (!isKeyOccupied(key))
            throw new IllegalArgumentException(key + " is not occupied.");
        List<Object> result = readResult(key);
        cacheMap.remove(key);
        return result;
    }

    @Override
    public void clearCache() {
        cacheMap.clear();
    }
}
