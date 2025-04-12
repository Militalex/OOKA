import org.hbrs.ooka.uebung1.component.Port;
import org.hbrs.ooka.uebung1.component.Product;
import org.hbrs.ooka.uebung1.interfaces.IProductManagement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConnectionTest {

    private IProductManagement productManagement;
    private Product productTarget;

    @BeforeEach
    public void setup() {
        productManagement = Port.getInterface();
        productTarget = new Product(1, "My Motor 1.0", 100.0);
        productManagement.openSession();
    }

    @Test
    public void roundTrip() {
        // Add
        productManagement.addProduct(productTarget);

        // Read
        Product productActual = productManagement.getProductsByName(productTarget.getName()).get(0);

        // Assertion
        assertEquals(productTarget, productActual);
    }

    @AfterEach
    public void deleteSuff() {
        productManagement.closeSession(true);
    }
}