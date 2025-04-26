package org.hbrs.ooka.uebung1;

import org.hbrs.ooka.uebung1.component.PortProductManagement;
import org.hbrs.ooka.uebung1.interfaces.IProductManagement;
import org.hbrs.ooka.uebung2.annotations.Start;
import org.hbrs.ooka.uebung2.annotations.Stop;

public class ProductManagementMain {

    private static IProductManagement productManagement;

    @Start
    public static void start(){
        PortProductManagement.setCache(new HashMapCache<>());
        productManagement = PortProductManagement.getInterface();
        productManagement.openSession();
    }

    @Stop
    public static void stop(){
        productManagement.closeSession(true);
        productManagement = null;
    }

    public static IProductManagement getInterface() {
        if (productManagement == null) {
            throw new IllegalStateException("ProductManagement is not started.");
        }
        return productManagement;
    }
}
