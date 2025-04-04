package org.hbrs.ooka.uebung1.component;

/**
 * Klasse zur Repräsentation eines Produkts. Dieses kann um weitere Attribute gerne erweitert werden.
 */
public class Product {
    private static int nextId = 1;
    private final int id;
    private String name;
    private double price;

    /**
     * Creates a new product with an automatic assigned Identifier.
     * Used to create brand-new products.
     */
    public Product(String name, double price) {
        this(nextId++, name, price);
    }

    /**
     * Creates a new product.
     * Used to read already existing products.
     */
    // TODO: public auf package-private setzen
    public Product(int id, String name, double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        // Wir lassen mal die ID eines Produkts weg, da diese nicht immer gesetzt ist bzw. ohnehin
        // über Auto-Increment in der Datenbank gesetzt wird.
        return "Product{" +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    /**
     * Vergleich von zwei Produkten anhand des Namens und des Preises.
     * Wichtig für den Junit-Test!!
     */
    public boolean equals(Object o) {
        if (!(o instanceof Product))
            return false;

        Product p = (Product) o;
        return p.getName().equals(this.getName()) && p.getPrice() == this.getPrice();
    }
}
