package org.hbrs.ooka.uebung2;

import org.hbrs.ooka.uebung1.component.PortProductManagement;
import org.hbrs.ooka.uebung2_3.runtimeEnvironment.IRuntimeEnvironmentAPI;

public class RuntimeEnvironmentMockup implements IRuntimeEnvironmentAPI {
    @Override
    public Object getPort(Class<?> aClass) {
        return PortProductManagement.class;
    }
}
