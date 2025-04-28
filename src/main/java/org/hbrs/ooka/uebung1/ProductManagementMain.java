package org.hbrs.ooka.uebung1;

import org.hbrs.ooka.uebung1.component.PortProductManagement;
import org.hbrs.ooka.uebung1.util.LoggerUtil;
import org.hbrs.ooka.uebung2.annotations.Start;
import org.hbrs.ooka.uebung2.annotations.Stop;

import java.util.logging.Logger;

public class ProductManagementMain {

    @Start
    public static void start(){
        PortProductManagement.setCache(new HashMapCache<>());
    }

    @Stop
    public static void stop(){

    }
}
