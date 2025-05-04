package org.hbrs.ooka.uebung2;

import org.hbrs.ooka.uebung1.component.PortProductManagement;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        PortProductManagement.start();

        ClientSimulation simulation = new ClientSimulation();
        simulation.start();
        Thread.sleep(10000);
        simulation.stop();

        PortProductManagement.stop();
    }
}
