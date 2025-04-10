package org.hbrs.ooka.uebung1.interfaces;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public interface ICaching {
    boolean isKeyOccupied(String key);
    Set<String> getAvailableKeys();
    void cacheResult(String key, List<Object> value);

    /**
     * Updates Result if it is present, else method throws {@link IllegalStateException}.
     */
    default List<Object> updateResult(String key, List<Object> value){
        if (!isKeyOccupied(key))
            throw new IllegalStateException(key + " is not occupied and thus value cannot be updated.");
        List<Object> result = takeResult(key);
        cacheResult(key, value);
        return result;
    }
    List<Object> readResult(String key);
    List<Object> takeResult(String key);
    void clearCache();
}
