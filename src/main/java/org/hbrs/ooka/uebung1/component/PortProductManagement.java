package org.hbrs.ooka.uebung1.component;

import org.hbrs.ooka.uebung1.interfaces.ICaching;
import org.hbrs.ooka.uebung1.interfaces.IProductManagement;
import org.hbrs.ooka.uebung2_3.annotations.Port;
import org.hbrs.ooka.uebung2_3.services.logger.ILogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Port
public class PortProductManagement {
    private static ILogger logger;
    private static @Nullable ICaching<List<Product>> cache;
    public static void setLogger(ILogger logger) {
        PortProductManagement.logger = logger;
    }
    public static ILogger getLogger() {
        return logger;
    }
    public static boolean isCacheEmpty(){
        return cache == null;
    }

    public static void setCache(@NotNull ICaching<List<Product>> cache) {
        PortProductManagement.cache = cache;
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
