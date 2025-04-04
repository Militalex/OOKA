package org.hbrs.ooka.uebung1.interfaces;

import java.util.List;
import java.util.Set;

public interface Caching {
    boolean isKeyOccupied(String key);
    Set<String> getAvailableKeys();
    void cacheResult(String key, List<Object> value);
    List<Object> takeResult(String key);
    void clearCache();
}
