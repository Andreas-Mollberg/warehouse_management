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
    public void addWarehouseToList(Warehouse warehouseToAdd) {
        try {
            boolean idExists = warehouses.stream()
                    .anyMatch((w -> w.getWarehouseId() == warehouseToAdd.getWarehouseId()));

            if (idExists) {
                throw new IllegalArgumentException(("A warehouse with that id already exists"));
            }
            boolean nameExists = warehouses.stream()
                    .anyMatch(w -> w.getWarehouseName().equalsIgnoreCase(warehouseToAdd.getWarehouseName()));
            if (nameExists) {
                throw new IllegalArgumentException("A warehouse with that name already exists.");
            }
            warehouses.add(warehouseToAdd);
            System.out.println(warehouseToAdd + " has been added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
     * @param productToAdd         The product to add to the warehouse
     */
    public void addProductToWarehouse(Warehouse destinationWarehouse, Product productToAdd) {
        destinationWarehouse.addProductIntoWarehouse(productToAdd);
    }

    public void removeProductFromWarehouse(Warehouse warehouse, Product productToRemove) {
        warehouse.removeProductByObject(productToRemove);
    }

    public void transferProductBetweenWarehouses(Warehouse sourceWarehouse, Warehouse destinationWarehouse, Product productToTransfer) {
        if (sourceWarehouse == null || destinationWarehouse == null) {
            System.out.println("Invalid source or destination warehouse.");
            return;
        }

        int sourceAmount = sourceWarehouse.howManyInStock(productToTransfer);
        if (sourceAmount <= 0) {
            System.out.println("The source warehouse does not have the specified product.");
            return;
        }

        sourceWarehouse.removeProductByObject(productToTransfer);
        destinationWarehouse.addProductIntoWarehouse(productToTransfer);

        System.out.println("Product transferred successfully.");
    }


    public void searchAndPrintProductInWarehouses(Product productToFind) {
        boolean found = false;

        for (Warehouse warehouse : warehouses) {
            int amount = warehouse.howManyInStock(productToFind);
            if (amount > 0) {
                found = true;
                System.out.println("There are " + amount + " " + productToFind.getProductName() +
                        " available in " + warehouse.getWarehouseNameCapitalized());
            }
        }

        if (!found) {
            System.out.println("Product not found in any warehouse.");
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
                .filter(w -> w.getWarehouseId() == number)
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
                .filter(w -> w.getWarehouseName().equalsIgnoreCase(locationLowerCase))
                .findFirst();

        // If there is a warehouse with the ID...
        if (optionalWarehouse.isPresent()) {

            // ...return it
            return optionalWarehouse.get();
        }

        // Otherwise throw an error
        throw new FindException("Warehouse with ID not found");
    }

    public Warehouse getWarehouseFromIdOrName(String IdOrName) {

        if (IdOrName.matches("\\d+")) {
            int warehouseNumber = Integer.parseInt(IdOrName);
            Warehouse warehouse = getWarehouse(warehouseNumber);

            if (warehouse != null) {
                return warehouse;
            } else {
                System.out.println("That warehouse cannot be found.");
            }
        } else {
            Warehouse warehouse = getWarehouse(IdOrName.toLowerCase());
            if (warehouse != null) {
                return warehouse;
            } else {
                System.out.println("That warehouse cannot be found.");
            }

        }
        return null;
    }


    public void printAllWarehouses() {
        getAllWarehouses();
        System.out.println("List of all warehouses:");
        for (Warehouse warehouse : warehouses) {
            System.out.println(warehouse);
        }

    }


    public Warehouse findWarehouse(int id) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getWarehouseId() == id) {
                return warehouse;
            }
        }
        return null; // Warehouse not found
    }

    public Warehouse findWarehouse(String name) {
        for (Warehouse warehouse : warehouses) {
            if (warehouse.getWarehouseName().equalsIgnoreCase(name)) {
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

    public Product getProductByIdOrName(String productIdOrName) {
        if (productIdOrName.matches("\\d+")) {
            int productId = Integer.parseInt(productIdOrName);
            var productToFind = findProduct(productId);
            if (productToFind != null) {
                return productToFind;
            }

        } else {

            // Input is a name
            return findProduct(productIdOrName);

        }
        return null;

    }

    public void adjustProductAmountInWarehouse(Warehouse warehouseToAdjust, Product productToAdjust, int newStockAmount) {
        int currentStock = warehouseToAdjust.howManyInStock(productToAdjust);

        if (newStockAmount > currentStock) {
            // Add products to reach the new stock amount
            for (int i = currentStock; i < newStockAmount; i++) {
                warehouseToAdjust.addProductIntoWarehouse(productToAdjust);
            }
        } else if (newStockAmount < currentStock) {
            // Remove products to reach the new stock amount
            for (int i = currentStock; i > newStockAmount; i--) {
                warehouseToAdjust.removeProductByObject(productToAdjust);
            }
        }
    }


}



