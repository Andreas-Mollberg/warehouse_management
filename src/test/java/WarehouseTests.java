

import org.example.Product;
import org.example.Warehouse;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;
import org.junit.Test.*;

import static org.junit.Assert.assertEquals;

public class WarehouseTests {

    @Test
    public void testRemoveProductByIdFromWarehouse() {
        var warehouse = new Warehouse(1, "Kista");

        Product product1 = new Product("iPhone 15", 1,
                12000.00, "Apple iPhone 15");

        Product product2 = new Product("Galaxy S23", 2,
                10000, "Samsung Galaxy S23");

        Product product3 = new Product("iPad Air", 3,
                8000, "Apple iPad Air 2022");

        warehouse.addProductIntoWarehouse(product1);
        warehouse.addProductIntoWarehouse(product1);
        warehouse.addProductIntoWarehouse(product2);

        // Remove a product by ID
        warehouse.removeProductByIdFromWarehouse(1);

        // Check if the product was removed
        assertEquals(1, warehouse.howManyInStock(product1));

        // Remove a product that doesn't exist
        warehouse.removeProductByIdFromWarehouse(3);

        // Check if the count remains the same
        assertEquals(1, warehouse.howManyInStock(product1));

        // Add more products
        warehouse.addProductIntoWarehouse(product1);
        warehouse.addProductIntoWarehouse(product2);

        // Check if the counts have increased
        assertEquals(2, warehouse.howManyInStock(product1));
        assertEquals(2, warehouse.howManyInStock(product2));

    }

    @Test
    public void testaddProductIntoWarehouse() {
        var warehouse = new Warehouse(1, "Kista");

        Product product1 = new Product("iPhone 15", 1,
                12000.00, "Apple iPhone 15");

        Product product2 = new Product("Galaxy S23", 2,
                10000, "Samsung Galaxy S23");

        Product product3 = new Product("iPad Air", 3,
                8000, "Apple iPad Air 2022");

        warehouse.addProductIntoWarehouse(product1);
        warehouse.addProductIntoWarehouse(product1);
        warehouse.addProductIntoWarehouse(product2);

        // Check if products were added
        assertEquals(2, warehouse.howManyInStock(product1));
        assertEquals(1, warehouse.howManyInStock(product2));

    }



}
