package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interface {
    Scanner scanner = new Scanner(System.in);

    private WarehouseManager manager;

    public Interface(WarehouseManager manager) {
        this.manager = manager;
    }

    public void mainMenu() {
        while (true) {
            System.out.println("*** Warhouse Manager ***\n");

            System.out.println("""
                    1. List all warehouses.
                    2. Show all products in a warehouse.
                    3. Create a new warehouse location.
                    4. Product management.
                    5. Exit Warehouse Manager.\s""");

            System.out.println("Enter 1-5: ");
            String userInput = scanner.nextLine();
            int mainMenuSelection = Integer.parseInt(userInput);

            switch (mainMenuSelection) {
                case 1:
                    manager.printAllWarehouses();
                    break;

                case 2:
                    System.out.println("Which warehouse do you wish to display the inventory of? ");
                    manager.printAllWarehouses();

                    System.out.println("Enter warehouse number or location: ");
                    String numberOrLocation = scanner.nextLine();

                    if (numberOrLocation.matches("\\d+")) {
                        int warehouseNumber = Integer.parseInt(numberOrLocation);
                        Warehouse warehouse = manager.getWarehouse(warehouseNumber);

                        if (warehouse != null) {
                            System.out.println("Products in " + warehouse.getWarehouseLocation() + ":");
                            warehouse.listAllProducts();
                        } else {
                            System.out.println("That warehouse cannot be found.");
                        }
                    } else {
                        Warehouse warehouseLocation = manager.getWarehouse(numberOrLocation.toLowerCase());
                        if (warehouseLocation != null) {
                            System.out.println("Products in " + warehouseLocation.getWarehouseLocation() + ":");
                            warehouseLocation.listAllProducts();
                        } else {
                            System.out.println("That warehouse cannot be found.");
                        }

                    }
                    break;

                case 3:
                    System.out.println("Enter number for the new warehouse: ");
                    String inputNumber = scanner.nextLine();
                    int newWarehouseNumber = Integer.parseInt(inputNumber);
                    System.out.println("Enter the location of the new warehouse: ");
                    String newWarehouseLocation = scanner.nextLine().trim();

                    Warehouse newWarehouse = new Warehouse(newWarehouseNumber, newWarehouseLocation);
                    manager.addWarehouseToList(newWarehouse);
                    break;

                case 4:
                    productMenu();
                    break;

                case 5:
                    return;
            }

        }
    }

    public void productMenu() {
        while (true) {
            System.out.println("***Product Manager***\n");

            System.out.println("""
                    1. Add a new product
                    2. Remove a product
                    3. Search for product in all warehouses.
                    4. Transfer products between warehouses.
                    5. Adjust stock of products
                    6. Go back to main menu.""");

            System.out.println("Enter 1-6: ");
            String userInput = scanner.nextLine();
            int productMenuSelection = Integer.parseInt(userInput);

            switch (productMenuSelection) {
                case 1:
                    System.out.println("Enter the ID for the new product: ");
                    String inputId = scanner.nextLine();
                    int newProductId = Integer.parseInt(inputId);
                    System.out.println("Enter the name of the new product: ");
                    String newProductName = scanner.nextLine().trim();
                    System.out.println("Enter the price of the new product: ");
                    String inputValue = scanner.nextLine();
                    double newProductPrice = Double.parseDouble(inputValue);
                    System.out.println("Enter a description of the new product: ");
                    String newProductDescription = scanner.nextLine().trim();

                    Product newProduct = new Product(newProductName, newProductId, newProductPrice, newProductDescription);


                    // Prompt the user to select a warehouse for the new product
                    System.out.println("Enter the warehouse number or location to add the product to: ");
                    String warehouseNumberOrLocation = scanner.nextLine();

                    // Find the warehouse by number or location
                    Warehouse targetWarehouse = manager.findWarehouse(warehouseNumberOrLocation);

                    if (targetWarehouse != null) {
                        // Add the new product to the specified warehouse
                        manager.addProductToWarehouse(targetWarehouse, newProduct);
                    } else {
                        System.out.println("The specified warehouse was not found.");
                    }

                    break;

                case 2:
                    // Insert method to remove product from sqlite db here
                    break;

                case 3:
                    System.out.println("ID or name of product you wish to search for: ");

                    String idOrName = scanner.nextLine();
                    if (idOrName.matches("\\d+")) {
                        int productNumber = Integer.parseInt(idOrName);
                        manager.searchAllWarehousesForProduct(idOrName);
                    } else {
                        manager.searchAllWarehousesForProduct(idOrName);
                    }
                    break;

                case 4:
                    System.out.println("Which warehouse do you want to transfer from? ");
                    manager.printAllWarehouses();

                    System.out.println("Enter location or ID for the warehouse you wish to transfer from: ");
                    String fromThisIdOrName = scanner.nextLine();
                    Warehouse sourceWarehouse = null;

                    if (fromThisIdOrName.matches("\\d+")) {
                        // Input is a number (ID)
                        int sourceWarehouseId = Integer.parseInt(fromThisIdOrName);
                        sourceWarehouse = manager.findWarehouse(sourceWarehouseId);
                    } else {
                        // Input is a name
                        sourceWarehouse = manager.findWarehouse(fromThisIdOrName.trim());
                    }

                    if (sourceWarehouse != null) {
                        manager.printProductsInWarehouse(sourceWarehouse); // Display products in the selected warehouse
                    } else {
                        System.out.println("The specified warehouse was not found.");
                    }


                    System.out.println("Enter ID or name of the product you wish to transfer: ");
                    String productIdOrName = scanner.nextLine().trim();
                    Product productToTransfer = null;

                    if (productIdOrName.matches("\\d+")) {
                        int productId = Integer.parseInt(productIdOrName);
                        productToTransfer = manager.findProduct(productId);
                    }else{
                        // Input is a name
                        productToTransfer = manager.findProduct(productIdOrName);
                    }

                    manager.printAllWarehouses();
                    String toThisIdOrName = scanner.nextLine();

                    Warehouse destinationWarehouse = null;

                    if (toThisIdOrName.matches("\\d+")) {
                        // Input is a number (ID)
                        int destinationWarehouseId = Integer.parseInt(toThisIdOrName);
                        destinationWarehouse = manager.findWarehouse(destinationWarehouseId);
                    } else {
                        // Input is a name
                        destinationWarehouse = manager.findWarehouse(toThisIdOrName.trim());
                    }

                    manager.transferProductBetweenWarehouses(sourceWarehouse, destinationWarehouse, productToTransfer);


            }
            break;
        }

    }
}



