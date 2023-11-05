package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarehouseManager {
    private ArrayList<Warehouse> warehouses;

    public WarehouseManager() {
        warehouses = new ArrayList<>();
    }

    public void addWarehouse(Warehouse warehouse) {
        warehouses.add(warehouse);
    }

    public ArrayList<Warehouse> getAllWarehouses() {
        return warehouses;
    }

    public Warehouse getWarehouseByIdOrName(int id) {
        Optional<Warehouse> optionalWarehouse = warehouses.stream()
                .filter(w -> w.getWarehouseId() == id)
                .findFirst();

        return optionalWarehouse.orElse(null);
    }

    public Warehouse getWarehouseByIdOrName(String name) {
        Optional<Warehouse> optionalWarehouse = warehouses.stream()
                .filter(w -> w.getWarehouseName().equalsIgnoreCase(name))
                .findFirst();

        return optionalWarehouse.orElse(null);
    }

    public void printAllWarehouses() {
        for (Warehouse warehouse : warehouses) {
            System.out.println(warehouse);
        }
    }

    public void addProductToWarehouse(Warehouse warehouse, Product product) {
        if (warehouse != null) {
            warehouse.addProductIntoWarehouse(product);
            System.out.println("Product added to " + warehouse.getWarehouseName());
        } else {
            System.out.println("Warehouse not found.");
        }
    }

    public void transferProductBetweenWarehouses(Warehouse sourceWarehouse, Warehouse destinationWarehouse, Product productToTransfer) {
        if (sourceWarehouse != null && destinationWarehouse != null) {
            if (sourceWarehouse.howManyInStock(productToTransfer) > 0) {
                sourceWarehouse.removeProductFromWarehouse(productToTransfer);
                destinationWarehouse.addProductIntoWarehouse(productToTransfer);
                System.out.println("Product transferred successfully.");
            } else {
                System.out.println("The source warehouse does not have the specified product.");
            }
        } else {
            System.out.println("Invalid source or destination warehouse.");
        }
    }

    public void adjustProductAmountInWarehouse(Warehouse warehouse, Product product, int newStockAmount) {
        if (warehouse != null) {
            int currentStock = warehouse.howManyInStock(product);
            if (currentStock >= 0) {
                int stockDifference = newStockAmount - currentStock;
                if (stockDifference > 0) {
                    for (int i = 0; i < stockDifference; i++) {
                        warehouse.addProductIntoWarehouse(product);
                    }
                } else if (stockDifference < 0) {
                    for (int i = 0; i < -stockDifference; i++) {
                        warehouse.removeProductFromWarehouse(product);
                    }
                }
                System.out.println("Stock adjusted successfully.");
            } else {
                System.out.println("Product not found in the warehouse.");
            }
        } else {
            System.out.println("Warehouse not found.");
        }
    }

    public void searchAndPrintProductInWarehouses(Product productToFind) {
        boolean found = false;
        for (Warehouse warehouse : warehouses) {
            if (warehouse.howManyInStock(productToFind) > 0) {
                found = true;
                int amount = warehouse.howManyInStock(productToFind);
                System.out.println("There are " + amount + " " + productToFind.getProductName() +
                        " available in " + warehouse.getWarehouseName());
            }
        }
        if (!found) {
            System.out.println("Product not found in any warehouse.");
        }
    }
}
