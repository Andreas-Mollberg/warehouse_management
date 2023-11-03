package org.example;

import java.util.ArrayList;

public class InsertExamples {
    public static void addExampleWarehouses(WarehouseManager manager){
        Warehouse warehouse1 = new Warehouse(1, "Stockholm");
        Warehouse warehouse2 = new Warehouse(2, "Falun");
        Warehouse warehouse3 = new Warehouse(3, "Sundsvall");

        manager.addWarehouseToList(warehouse1);
        manager.addWarehouseToList(warehouse2);
        manager.addWarehouseToList(warehouse3);
    }

    public static void addExampleProducts(WarehouseManager manager) {
        // Assume that you already have existing warehouses
        // and you don't need to create new ones here.

        Product product1 = new Product("iPhone 15", 1, 12000.00, "Apple iPhone 15");
        Product product2 = new Product("Galaxy S23", 2, 10000, "Samsung Galaxy S23");
        Product product3 = new Product("iPad Air", 3, 8000, "Apple iPad Air 2022");

        // Add products to existing warehouses
        manager.addProductToWarehouse(manager.findWarehouse(1), product1);
        manager.addProductToWarehouse(manager.findWarehouse(2), product2);
        manager.addProductToWarehouse(manager.findWarehouse(3), product3);
    }


}
