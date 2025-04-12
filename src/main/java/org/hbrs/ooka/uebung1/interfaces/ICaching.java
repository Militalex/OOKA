package org.hbrs.ooka.uebung1.interfaces;

import java.util.Set;

public interface ICaching<T> {
    boolean isKeyOccupied(String key);
    Set<String> getAvailableKeys();
    void cacheResult(String key, T value);

    /**
     * Updates Result if it is present, else method throws {@link IllegalStateException}.
     */
    default T updateResult(String key, T value){
        if (!isKeyOccupied(key))
            throw new IllegalStateException(key + " is not occupied and thus value cannot be updated.");
        T result = takeResult(key);
        cacheResult(key, value);
        return result;
    }
    T readResult(String key);
    T takeResult(String key);
    void clearCache();
}
