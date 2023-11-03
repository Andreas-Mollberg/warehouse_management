package org.example;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WarehouseManager {
    private List<Warehouse> warehouses = new ArrayList<>();
    ;

    /**
     * Adds a new warehouse to the list of all warehouses
     *
     * @param warehouseToAdd The warehouse to add
     * @throws java.lang.module.FindException If no warehouse exists with the ID
     */
    public void addWarehouseToList(Warehouse warehouseToAdd) throws IllegalArgumentException {
        boolean idExists = warehouses.stream()
                .anyMatch((w -> w.getWarehouseNumber() == warehouseToAdd.getWarehouseNumber()));

        if (idExists) {
            throw new IllegalArgumentException(("A warehouse with that id already exists"));
        }

        warehouses.add(warehouseToAdd);
        System.out.println(warehouseToAdd + " has been added.");
    }

    /**
     * Gets all warehouses currently available
     *
     * @return All warehouses
     */
    public ArrayList<Warehouse> getAllWarehouses() {
        return (ArrayList<Warehouse>) warehouses;
    }

    /**
     * Adds a product to the warehouse with the specified ID
     *
     * @param destinationWarehouse The ID of the warehouse to add a product to
     * @param productToAdd    The product to add to the warehouse
     */
    public void addProductToWarehouse(Warehouse destinationWarehouse, Product productToAdd) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse == destinationWarehouse) {
                warehouse.addProductIntoWarehouse(productToAdd);
                return;
            }
        }
    }

    public void removeProductFromWarehouse(Warehouse warehouse, Product productToRemove) {
        for (var wh : warehouses) {
            if (wh == (warehouse)) {
                wh.removeProductByObject(productToRemove);
            }
        }
    }

    public void transferProductBetweenWarehouses(Warehouse sourceWarehouse, Warehouse destinationWarehouse, Product productToTransfer) {
        if (sourceWarehouse == null || destinationWarehouse == null) {
            System.out.println("Invalid source or destination warehouse.");
            return;
        }

        if (!sourceWarehouse.containsProduct(productToTransfer)) {
            System.out.println("The source warehouse does not have the specified product.");
            return;
        }

        removeProductFromWarehouse(sourceWarehouse, productToTransfer); // Remove the product from the source warehouse
        addProductToWarehouse(destinationWarehouse, productToTransfer); // Add the product to the destination warehouse
        System.out.println("Product transferred successfully.");
    }

    public void printProductsInWarehouse(Warehouse warehouse) {
        if (warehouse == null) {
            System.out.println("Warehouse not found.");
            return;
        }

        System.out.println("Products in " + warehouse.getWarehouseLocation() + ":");
        warehouse.listAllProducts();
    }


    public void printWarehouseStock(int id) {
        for (var warehouse : warehouses) {
            if (warehouse.getWarehouseNumber() == id) {
                warehouse.listAllProducts();
            }
        }

    }

    public void printWarehouseStock(String location) {
        for (var warehouse : warehouses) {
            if (warehouse.getWarehouseLocation().equals(location)) {
                warehouse.listAllProducts();
            }
        }

    }

    public void searchAllWarehousesForProduct(String name) {
        for (var warehouse : warehouses) {
            if (warehouse.searchForProduct(name.toLowerCase())) {
                int amount = warehouse.howManyInStock(name);
                System.out.println("There are " + amount + " " + name +
                        " available in " + warehouse.getWarehouseLocation());
            }
        }
    }

    public void searchAllWarehousesForProduct(int number) {
        for (var warehouse : warehouses) {
            if (warehouse.searchForProduct(number)) {
                int amount = warehouse.howManyInStock(number);
                System.out.println("There are " + amount + " " + number +
                        " available in " + warehouse.getWarehouseLocation());
            }
        }
    }


    /**
     * Gets the warehouse with the specified ID
     *
     * @param number The ID of the warehouse
     * @return The warehouse
     * @throws FindException If no warehouse exists with the ID
     */
    public Warehouse getWarehouse(int number) throws FindException {
        // Filter all warehouses on ID
        Optional<Warehouse> optionalWarehouse = warehouses.stream()
                .filter(w -> w.getWarehouseNumber() == number)
                .findFirst();

        // If there is a warehouse with the ID...
        if (optionalWarehouse.isPresent()) {

            // ...return it
            return optionalWarehouse.get();
        }

        // Otherwise throw an error
        throw new FindException("Warehouse with ID not found");
    }

    public Warehouse getWarehouse(String location) throws FindException {
        String locationLowerCase = location.toLowerCase();
        // Filter all warehouses on ID
        Optional<Warehouse> optionalWarehouse = warehouses.stream()
                .filter(w -> w.getWarehouseLocation().equalsIgnoreCase(locationLowerCase))
                .findFirst();

        // If there is a warehouse with the ID...
        if (optionalWarehouse.isPresent()) {

            // ...return it
            return optionalWarehouse.get();
        }

        // Otherwise throw an error
        throw new FindException("Warehouse with ID not found");
    }


    public void printAllWarehouses() {
        getAllWarehouses();
        System.out.println("List of all warehouses:");
        for (Warehouse warehouse : warehouses) {
            System.out.println(warehouse);
        }

    }


    public void getProductsInWarehouse(Warehouse warehouse) {
        for (Warehouse wh : warehouses) {
            if (wh == warehouse) {
                return;
            }
        }
    }

    public Warehouse findWarehouse(int id) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getWarehouseNumber() == id) {
                return warehouse;
            }
        }
        return null; // Warehouse not found
    }

    public Warehouse findWarehouse(String name) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getWarehouseLocation().equalsIgnoreCase(name)) {
                return warehouse;
            }
        }
        return null; // Warehouse not found
    }


    public Product findProduct(int productId) {
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getAllProducts()) {
                if (product.getProductId() == productId) {
                    return product;
                }
            }
        }
        return null; // Product not found
    }

    public Product findProduct(String productName) {
        for (Warehouse warehouse : warehouses) {
            for (Product product : warehouse.getAllProducts()) {
                if (product.getProductName().equalsIgnoreCase(productName)) {
                    return product;
                }
            }
        }
        return null; // Product not found
    }

}



