import org.hbrs.ooka.uebung1.component.PortProductManagement;
import org.hbrs.ooka.uebung1.component.Product;
import org.hbrs.ooka.uebung1.interfaces.IProductManagement;

import java.util.List;

public class TestMainClass {
    public static void main(String[] args) {
        IProductManagement productManagement = PortProductManagement.getInterface();
        productManagement.openSession();
        productManagement.addProduct(new Product("Tisch", 1000));
        productManagement.deleteAll();
        productManagement.closeSession(true);
    }
}
