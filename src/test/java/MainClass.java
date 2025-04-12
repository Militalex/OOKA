import org.hbrs.ooka.uebung1.component.Port;
import org.hbrs.ooka.uebung1.component.Product;
import org.hbrs.ooka.uebung1.interfaces.IProductManagement;

public class MainClass {
    public static void main(String[] args) {
        IProductManagement productManagement = Port.getInterface();
        productManagement.addProduct(new Product("Tisch", 1000));
    }
}
