import org.hbrs.ooka.uebung1.interfaces.ICaching;

import java.util.HashMap;
import java.util.Set;

public class HashMapCache<T> implements ICaching<T> {
    private final HashMap<String, T> cacheMap = new HashMap<>();

    @Override
    public boolean isKeyOccupied(String key) {
        return cacheMap.containsKey(key);
    }

    @Override
    public Set<String> getAvailableKeys() {
        return cacheMap.keySet();
    }

    @Override
    public void cacheResult(String key, T value) {
        if (isKeyOccupied(key))
            throw new IllegalStateException(key + " is already occupied.");
        cacheMap.put(key, value);
    }

    @Override
    public T readResult(String key) {
        if (!isKeyOccupied(key))
            throw new IllegalArgumentException(key + " is not occupied.");
        return cacheMap.get(key);
    }

    @Override
    public T takeResult(String key) {
        if (!isKeyOccupied(key))
            throw new IllegalArgumentException(key + " is not occupied.");
        T result = readResult(key);
        cacheMap.remove(key);
        return result;
    }

    @Override
    public void clearCache() {
        cacheMap.clear();
    }
}
