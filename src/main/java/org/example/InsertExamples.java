package org.example;

import java.util.ArrayList;

public class InsertExamples {
    public static void addExampleWarehouses(WarehouseManager manager) {
        Warehouse warehouse1 = new Warehouse(1, "Stockholm"); // Specify a unique ID
        Warehouse warehouse2 = new Warehouse(2, "Falun"); // Specify a unique ID
        Warehouse warehouse3 = new Warehouse(3, "Sundsvall"); // Specify a unique ID

        manager.addWarehouse(warehouse1);
        manager.addWarehouse(warehouse2);
        manager.addWarehouse(warehouse3);
    }


    public static void addExampleProducts(WarehouseManager manager) {

        Product product1 = new Product("iPhone 15", 1, 12000.00, "Apple iPhone 15");
        Product product2 = new Product("Galaxy S23", 2, 10000, "Samsung Galaxy S23");
        Product product3 = new Product("iPad Air", 3, 8000, "Apple iPad Air 2022");


        manager.addProductToWarehouse(manager.getWarehouseByIdOrName(1), product1);
        manager.addProductToWarehouse(manager.getWarehouseByIdOrName(1), product2);
        manager.addProductToWarehouse(manager.getWarehouseByIdOrName(1), product3);

        manager.addProductToWarehouse(manager.getWarehouseByIdOrName(2), product1);
        manager.addProductToWarehouse(manager.getWarehouseByIdOrName(2), product2);
        manager.addProductToWarehouse(manager.getWarehouseByIdOrName(2), product3);

        manager.addProductToWarehouse(manager.getWarehouseByIdOrName(3), product1);
        manager.addProductToWarehouse(manager.getWarehouseByIdOrName(3), product2);
        manager.addProductToWarehouse(manager.getWarehouseByIdOrName(3), product3);
    }


}
