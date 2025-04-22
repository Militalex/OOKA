package org.hbrs.ooka.uebung1;

import org.hbrs.ooka.uebung1.component.PortProductManagement;
import org.hbrs.ooka.uebung1.component.Product;
import org.hbrs.ooka.uebung1.interfaces.IProductManagement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionTest {

    private IProductManagement productManagement;

    private List<Product> p = new ArrayList<>(10);

    @BeforeEach
    public void setup() {
        p.add(new Product("My Motor 1.0", 100));
        p.add(new Product("My Motor 2.0", 200));
        p.add(new Product("Tisch", 100));
        p.add(new Product("Tisch", 250));
        p.add(new Product("Kaffeemaschine", 1200));
        p.add(new Product("Kaffeemaschine", 2400));
        p.add(new Product("IPhone", 2400));
        p.add(new Product("My Motor 1.0", 125));
        p.add(new Product("My Motor 2.0", 225));
        p.add(new Product("Tisch", 400));

        productManagement = PortProductManagement.getInterface();
        productManagement.openSession();
    }

    @Test
    public void roundTripWithoutCache() {
        roundTrip();
    }

    @Test
    public void roundTripWithCache() {
        PortProductManagement.setCache(new HashMapCache<>());
        roundTrip();
    }

    @Test
    public void persistenceTest() {
        productManagement.addProduct(p.get(0));
        productManagement.closeSession(false);

        productManagement.openSession();
        List<Product> productsActual = productManagement.getAllProducts();
        assertEquals(1, productsActual.size());
        assertEquals(p.get(0), productsActual.get(0));
        productManagement.closeSession(true);

        productManagement.openSession();
        productsActual = productManagement.getAllProducts();
        assertTrue(productsActual.isEmpty());
    }

    private void roundTrip(){
        // Einfaches Hinzufügen
        productManagement.addProduct(p.get(0));
        Product productActual = productManagement.getProductsByName(p.get(0).getName()).get(0);
        assertEquals(p.get(0), productActual);

        // Einfaches Entfernen
        productActual = productManagement.deleteProductsByName(p.get(0).getName()).get(0);
        assertEquals(p.get(0), productActual);
        assertTrue(productManagement.getProductsByName(p.get(0).getName()).isEmpty());

        // Kompliziertes Hinzufügen
        p.forEach(productManagement::addProduct);
        List<Product> productsActual = productManagement.getAllProducts();
        assertEquals(10, productsActual.size());
        p.forEach(product -> assertTrue(productsActual.contains(product)));

        // Einfaches Entfernen V02
        List<Product> productsActualV02 = productManagement.deleteAll();
        assertEquals(10, productsActualV02.size());
        assertTrue(productManagement.getAllProducts().isEmpty());
        p.forEach(product -> assertTrue(productsActualV02.contains(product)));

        // Kompliziertes Hinzufügen zweites mal
        p.forEach(productManagement::addProduct);
        List<Product> productsActualV03 = productManagement.getAllProducts();
        assertEquals(10, productsActualV03.size());
        p.forEach(product -> assertTrue(productsActualV03.contains(product)));

        // Komplizierte Entfernen
            // Tischabfrage
        List<Product> tische = productManagement.getProductsByName("Tisch");
        assertEquals(3, tische.size());
        p.stream().filter(p -> p.getName().equals("Tisch")).forEach(product -> assertTrue(tische.contains(product)));

            // Kaffeemaschinenabfrage
        List<Product> kaffeemaschinen = productManagement.getProductsByName("Kaffeemaschine");
        assertEquals(2, kaffeemaschinen.size());
        p.stream().filter(p -> p.getName().equals("Kaffeemaschine")).forEach(product -> assertTrue(kaffeemaschinen.contains(product)));
            // Preis 100 Abfrage
        List<Product> preis_100 = productManagement.getProductsByPrice(100);
        assertEquals(2, preis_100.size());
        p.stream().filter(p -> p.getPrice() == 100).forEach(product -> assertTrue(preis_100.contains(product)));

        // Kompliziertes Entfernen
            // Motoren 1.0 entfernen
        List<Product> motoren_1_0 = productManagement.deleteProductsByName("My Motor 1.0");
        assertEquals(2, motoren_1_0.size());
        p.stream().filter(p -> p.getName().equals("My Motor 1.0")).forEach(product -> assertTrue(motoren_1_0.contains(product)));

                // Preis 100 Abfrage erneut
        List<Product> preis_100_now = productManagement.getProductsByPrice(100);
        assertEquals(1, preis_100_now.size());
        assertEquals(p.get(2), preis_100_now.get(0));

        p = p.stream().filter(p -> !p.getName().equals("My Motor 1.0")).toList();
        List<Product> productsActual_now = productManagement.getAllProducts();
        assertEquals(8, productsActual_now.size());
        p.forEach(product -> assertTrue(productsActual_now.contains(product)));

                // Motoren 1.0 nicht mehr enthalten?
        List<Product> motoren_1_0_now = productManagement.getProductsByName("My Motor 1.0");
        assertTrue(motoren_1_0_now.isEmpty());
        motoren_1_0_now = productManagement.deleteProductsByName("My Motor 1.0");
        assertTrue(motoren_1_0_now.isEmpty());

            // Preis 2400 entfernen
        List<Product> preis_2400 = productManagement.deleteProductsByPrice(2400);
        assertEquals(2, preis_2400.size());
        p.stream().filter(p -> p.getPrice() == 2400).forEach(product -> assertTrue(preis_2400.contains(product)));

        p = p.stream().filter(p -> p.getPrice() != 2400).toList();
        List<Product> productsActual_now1 = productManagement.getAllProducts();
        assertEquals(6, productsActual_now1.size());
        p.forEach(product -> assertTrue(productsActual_now1.contains(product)));

        List<Product> kaffeemaschinen_now = productManagement.getProductsByName("Kaffeemaschine");
        assertEquals(1, kaffeemaschinen_now.size());
        p.stream().filter(p -> p.getName().equals("Kaffeemaschine")).forEach(product -> assertTrue(kaffeemaschinen_now.contains(product)));

        List<Product> tische_now = productManagement.getProductsByName("Tisch");
        assertEquals(3, tische_now.size());
        p.stream().filter(p -> p.getName().equals("Tisch")).forEach(product -> assertTrue(tische_now.contains(product)));

            // Motoren 2.0 entfernen
        List<Product> motoren_2_0 = productManagement.deleteProductsByName("My Motor 2.0");
        assertEquals(2, motoren_2_0.size());
        p.stream().filter(p -> p.getName().equals("My Motor 2.0")).forEach(product -> assertTrue(motoren_2_0.contains(product)));

        p = p.stream().filter(p -> !p.getName().equals("My Motor 2.0")).toList();
        List<Product> productsActual_now2 = productManagement.getAllProducts();
        assertEquals(4, productsActual_now2.size());
        p.forEach(product -> assertTrue(productsActual_now2.contains(product)));

        List<Product> tische_now2 = productManagement.getProductsByName("Tisch");
        assertEquals(3, tische_now2.size());
        p.stream().filter(p -> p.getName().equals("Tisch")).forEach(product -> assertTrue(tische_now2.contains(product)));

            // Tische entfernen
        List<Product> tische_del = productManagement.deleteProductsByName("Tisch");
        assertEquals(3, tische_del.size());
        p.stream().filter(p -> p.getName().equals("Tisch")).forEach(product -> assertTrue(tische_del.contains(product)));

        p = p.stream().filter(p -> !p.getName().equals("Tisch")).toList();
        List<Product> productsActual_now3 = productManagement.getAllProducts();
        assertEquals(1, productsActual_now3.size());
        p.forEach(product -> assertTrue(productsActual_now3.contains(product)));

        List<Product> lastProduct = productManagement.deleteProductsByName("Kaffeemaschine");
        assertEquals(1, lastProduct.size());
        assertEquals(p.get(0), lastProduct.get(0));
        assertTrue(productManagement.getAllProducts().isEmpty());
    }

    @AfterEach
    public void deleteSuff() {
        productManagement.closeSession(true);
    }
}