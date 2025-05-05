package org.hbrs.ooka.uebung1;

import org.hbrs.ooka.uebung1.component.PortProductManagement;
import org.hbrs.ooka.uebung2_3.annotations.*;
import org.hbrs.ooka.uebung2_3.services.logger.ILogger;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductManagementStarter {
    @Inject(InjectType.LOGGER)
    public static ILogger LOGGER;
    @Start
    public static void start(){
        if (LOGGER == null) {
            LOGGER = new ILogger() {
                private Logger logger = Logger.getLogger("ClientSimulation");
                @Override
                public void sendLog(Level level, String s, @Nullable Throwable throwable) {
                    logger.log(level, s, throwable);
                }
            };
        }
        LOGGER.info("ProduktManagement wird gestartet ...");

        PortProductManagement.setCache(new HashMapCache<>());
        PortProductManagement.setLogger(LOGGER);
        LOGGER.info("- - - ProduktManagement wurde gestartet - - -");
    }
    @Stop
    public static void stop(){
        LOGGER.info("ProduktManagement wird gestoppt ...");

        LOGGER.info("- - - ProduktManagement wurde gestoppt - - -");
    }
}
