package org.hbrs.ooka.uebung2;

import org.hbrs.ooka.uebung1.ProductManagementStarter;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        ProductManagementStarter.start();

        ClientSimulation.runtimeEnvironmentAPI = new RuntimeEnvironmentMockup();

        ClientSimulation.start();
        Thread.sleep(10000);
        ClientSimulation.stop();

        ProductManagementStarter.stop();
    }
}
