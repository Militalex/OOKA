package org.hbrs.ooka.uebung1.component;

import org.hbrs.ooka.uebung1.interfaces.ICaching;
import org.hbrs.ooka.uebung1.interfaces.IProductManagement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Port {
    private static @Nullable ICaching<List<Product>> cache;

    static boolean isCacheEmpty(){
        return cache == null;
    }

    public static void setCache(@NotNull ICaching<List<Product>> cache) {
        cache = cache;
    }

    public static void clearCache(){
        cache = null;
    }

    static @Nullable ICaching<List<Product>> getCache() {
        return cache;
    }

    public static IProductManagement getInterface(){
        return new ProxyProductManagement();
    }
}
