package org.hbrs.ooka.uebung3;

import org.hbrs.ooka.uebung2_3.annotations.*;
import org.hbrs.ooka.uebung2_3.services.logger.ILogger;

@StartClass
public class TickerSimulation {

    @Inject(InjectType.LOGGER)
    private static ILogger LOGGER;

    @Start
    public static void start() {
        LOGGER.info("Ticker Simulation wird gestartet ...");
        LOGGER.info("- - - Ticker Simulation wurde gestartet - - -");

        for (int i = 0; true; i++) {
            LOGGER.info("Tick " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Stop
    public static void stop(){
        LOGGER.info("Ticker Simulation wird gestoppt ...");
        LOGGER.info("- - - Ticker Simulation wurde gestoppt - - -");
    }
}
