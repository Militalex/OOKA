package org.hbrs.ooka.uebung1.component;

import org.hbrs.ooka.uebung1.interfaces.Caching;
import org.hbrs.ooka.uebung1.interfaces.ProductManagementInt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Port {
    private @Nullable Caching cache;

    boolean isCacheEmpty(){
        return cache == null;
    }

    public void setCache(@NotNull Caching cache) {
        this.cache = cache;
    }

    public void clearCache(){
        cache = null;
    }

    @Nullable Caching getCache() {
        return cache;
    }

    public ProductManagementInt getInterface(){
        return new ProxyProductManagement();
    }
}
