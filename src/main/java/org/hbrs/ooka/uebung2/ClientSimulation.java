package org.hbrs.ooka.uebung2;

import org.hbrs.ooka.uebung1.component.PortProductManagement;
import org.hbrs.ooka.uebung1.component.Product;
import org.hbrs.ooka.uebung1.interfaces.IProductManagement;
import org.hbrs.ooka.uebung2_3.annotations.Start;
import org.hbrs.ooka.uebung2_3.annotations.Stop;
import org.hbrs.ooka.uebung2_3.runtimeEnvironment.IRuntimeEnvironmentAPI;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSimulation {

    public static IRuntimeEnvironmentAPI runtimeEnvironmentAPI;

    public static final Logger LOGGER = Logger.getLogger(ClientSimulation.class.getName());

    private static Tickable tickable;

    private static PortProductManagement portProductManagement;
    private static IProductManagement productManagement;

    @Start
    public static void start() {
        LOGGER.info("ProduktManagement Client Simulation wird gestartet ...");
        if (portProductManagement != null) {
            LOGGER.log(Level.SEVERE, "ProduktManagement wurde bereits gestartet");
            return;
        }

        try {
            portProductManagement = (PortProductManagement) runtimeEnvironmentAPI.getPort(PortProductManagement.class);
        }
        catch (IllegalStateException e) {
            LOGGER.log(Level.SEVERE, "ProduktManagement wurde noch nicht gestartet");
            return;
        }

        productManagement = portProductManagement.getInterface();
        productManagement.openSession();

        tickable = new Tickable() {
            @Override
            public void tick() {
                productManagement.addProduct(new Product("Simuliertes Produkt", Math.random()));
            }
        };
        tickable.start(0, 1000);

        LOGGER.info("- - - ProduktManagement Client Simulation wurde gestartet - - -");
    }

    @Stop
    public static void stop() {
        LOGGER.info("ProduktManagement Client Simulation wird gestoppt ...");
        if (portProductManagement == null) {
            LOGGER.log(Level.SEVERE, "ProduktManagement wurde noch nicht gestartet");
            return;
        }
        tickable.forceStop();
        productManagement.deleteProductsByName("Simuliertes Produkt");
        productManagement.closeSession(true);

        tickable = null;
        portProductManagement = null;
        productManagement = null;
        LOGGER.info("- - - ProduktManagement Client Simulation wurde gestoppt - - -");
    }
}