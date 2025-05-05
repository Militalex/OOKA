package org.hbrs.ooka.uebung2;

import org.hbrs.ooka.uebung1.ProductManagementStarter;
import org.hbrs.ooka.uebung1.component.PortProductManagement;
import org.hbrs.ooka.uebung1.component.Product;
import org.hbrs.ooka.uebung1.interfaces.IProductManagement;
import org.hbrs.ooka.uebung2_3.annotations.*;
import org.hbrs.ooka.uebung2_3.runtimeEnvironment.IRuntimeEnvironmentAPI;
import org.hbrs.ooka.uebung2_3.services.logger.ILogger;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

@StartClass
public class ClientSimulation {

    @Inject(InjectType.LOGGER)
    public static ILogger LOGGER;
    private static IProductManagement productManagement;

    @Start
    public static void start() {
        if (LOGGER == null) {
            LOGGER = new ILogger() {
                private Logger logger = Logger.getLogger("ClientSimulation");
                @Override
                public void sendLog(Level level, String s, @Nullable Throwable throwable) {
                    logger.log(level, s, throwable);
                }
            };
        }

        LOGGER.info("ProduktManagement Client Simulation wird gestartet ...");

        ProductManagementStarter.LOGGER = LOGGER;
        ProductManagementStarter.start();

        productManagement = PortProductManagement.getInterface();
        productManagement.openSession();

        LOGGER.info("- - - ProduktManagement Client Simulation wurde gestartet - - -");

        for (int i = 0; i < 10; i++) {
            productManagement.addProduct(new Product("Simuliertes Produkt", Math.random()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Stop
    public static void stop() {
        LOGGER.info("ProduktManagement Client Simulation wird gestoppt ...");

        productManagement.deleteProductsByName("Simuliertes Produkt");
        productManagement.closeSession(true);

        ProductManagementStarter.stop();
        productManagement = null;
        LOGGER.info("- - - ProduktManagement Client Simulation wurde gestoppt - - -");
    }
}