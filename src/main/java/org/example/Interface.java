package org.example;

import java.util.Scanner;

public class Interface {
    ProductDatabase productDatabase = new ProductDatabase("jdbc:sqlite:products.db");
    WarehouseDatabase warehouseDatabase = new WarehouseDatabase("jdbc:sqlite:warehouses.db");

    Scanner scanner = new Scanner(System.in);
    private WarehouseManager manager;

    public Interface(WarehouseManager manager) {
        this.manager = manager;
    }

    public void mainMenu() {
        while (true) {
            System.out.println("*** Warehouse Manager ***\n");
            System.out.println("""
                1. List all warehouses.
                2. Show all products in a warehouse.
                3. Create a new warehouse location.
                4. Transfer products between warehouses.
                5. Adjust stock of products in a warehouse.
                6. Product management.
                7. Exit Warehouse Manager.
                """);

            System.out.println("Enter 1-7: ");
            String userInput = scanner.nextLine();

            try {
                int mainMenuSelection = Integer.parseInt(userInput);

                switch (mainMenuSelection) {
                    case 1:
                        manager.printAllWarehouses();
                        break;

                    case 2:
                        System.out.println("Which warehouse do you wish to display the inventory of? ");
                        manager.printAllWarehouses();

                        System.out.println("Enter warehouse number or location: ");
                        String warehouseIdOrName = scanner.nextLine();

                        Warehouse selectedWarehouse = manager.getWarehouseByIdOrName(warehouseIdOrName);
                        if (selectedWarehouse != null) {
                            selectedWarehouse.listAllProducts();
                        } else {
                            System.out.println("Warehouse not found.");
                        }
                        break;

                    case 3:
                        System.out.println("Enter the name of the new warehouse: ");
                        String newWarehouseLocation = scanner.nextLine().trim();

                        Warehouse newWarehouse = new Warehouse(newWarehouseLocation);
                        manager.addWarehouse(newWarehouse);
                        System.out.println("New warehouse added.");
                        break;

                    case 4:
                        System.out.println("Which warehouse do you want to transfer from? ");
                        manager.printAllWarehouses();

                        System.out.println("Enter name or ID for the warehouse you wish to transfer from: ");
                        String fromThisIdOrName = scanner.nextLine();

                        Warehouse sourceWarehouse = manager.getWarehouseByIdOrName(fromThisIdOrName);

                        System.out.println("Enter ID or name of the product you wish to transfer: ");
                        String productIdOrName = scanner.nextLine().trim();
                        Product productToTransfer = sourceWarehouse.getProductByIdOrName(productIdOrName);

                        System.out.println("Enter the name or ID for the warehouse you would like to move the product to.");
                        manager.printAllWarehouses();
                        String toThisIdOrName = scanner.nextLine();

                        Warehouse destinationWarehouse = manager.getWarehouseByIdOrName(toThisIdOrName);

                        if (sourceWarehouse != null && destinationWarehouse != null && productToTransfer != null) {
                            manager.transferProductBetweenWarehouses(sourceWarehouse, destinationWarehouse, productToTransfer);
                            System.out.println("Product transferred successfully.");
                        } else {
                            System.out.println("Invalid source, destination, or product.");
                        }
                        break;

                    case 5:
                        System.out.println("At which warehouse would you like to adjust the stocks? ");
                        manager.printAllWarehouses();

                        String inputWarehouseIdOrName = scanner.nextLine();
                        Warehouse warehouseToAdjustStocksIn = manager.getWarehouseByIdOrName(inputWarehouseIdOrName);

                        if (warehouseToAdjustStocksIn != null) {
                            System.out.println("Current inventory of " + warehouseToAdjustStocksIn.getWarehouseNameCapitalized());
                            warehouseToAdjustStocksIn.listAllProducts();

                            System.out.println("Enter ID or name of product you wish to adjust the stock level of: ");
                            String productToAdjustIdOrName = scanner.nextLine();

                            Product productToAdjust = warehouseToAdjustStocksIn.getProductByIdOrName(productToAdjustIdOrName);

                            if (productToAdjust != null) {
                                System.out.println("Current stock of " + productToAdjust.getProductName() + " is: "
                                        + warehouseToAdjustStocksIn.howManyInStock(productToAdjust));

                                System.out.println("Enter new total amount in stock: ");
                                String inputNewTotal = scanner.nextLine();
                                int newStockAmount = Integer.parseInt(inputNewTotal);

                                manager.adjustProductAmountInWarehouse(warehouseToAdjustStocksIn, productToAdjust, newStockAmount);

                                var currentStock = warehouseToAdjustStocksIn.howManyInStock(productToAdjust);

                                System.out.println("Current stock of " + productToAdjust.getProductName() + " at warehouse "
                                        + warehouseToAdjustStocksIn.getWarehouseNameCapitalized() + " is: " + currentStock);
                            } else {
                                System.out.println("Product not found.");
                            }
                        } else {
                            System.out.println("Warehouse not found.");
                        }
                        break;

                    case 6:
                        productMenu();
                        break;

                    case 7:
                        return;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public void productMenu() {
        while (true) {
            System.out.println("*** Product Manager ***\n");
            System.out.println("""
                1. Add a new product
                2. Remove a product
                3. Search for product in all warehouses.
                4. Go back to the main menu.
                """);

            System.out.println("Enter 1-4: ");
            String userInput = scanner.nextLine();
            int productMenuSelection;

            try {
                productMenuSelection = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Please enter a valid number.");
                continue;
            }

            switch (productMenuSelection) {
                case 1:
                    addNewProduct();
                    break;

                case 2:
                    removeProduct();
                    break;

                case 3:
                    searchForProductInAllWarehouses();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Incorrect input. Input must be a number between 1 and 4.");
                    break;
            }
        }
    }

    private void addNewProduct() {
        System.out.println("Enter the ID for the new product: ");
        String inputId = scanner.nextLine();

        try {
            int newProductId = Integer.parseInt(inputId);
            System.out.println("Enter the name of the new product: ");
            String newProductName = scanner.nextLine().trim();
            System.out.println("Enter the price of the new product: ");
            String inputValue = scanner.nextLine();

            double newProductPrice;

            try {
                newProductPrice = Double.parseDouble(inputValue);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Price must be a valid number.");
                return;
            }

            System.out.println("Enter a description of the new product: ");
            String newProductDescription = scanner.nextLine().trim();

            Product newProduct = new Product(newProductName, newProductId, newProductPrice, newProductDescription);

            System.out.println("Enter the warehouse number or location to add the product to: ");
            manager.printAllWarehouses();

            System.out.println("Enter warehouse number or location: ");
            String numberOrName = scanner.nextLine();

            Warehouse warehouse = manager.getWarehouseByIdOrName(numberOrName);

            if (warehouse != null) {
                manager.addProductToWarehouse(warehouse, newProduct);
                System.out.println("Product added successfully to warehouse " + warehouse.getWarehouseName());
            } else {
                System.out.println("Warehouse not found.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. ID must be a valid number.");
        }
    }

    private void removeProduct() {
        System.out.println("Enter the name or ID of the product to remove: ");
        String input = scanner.nextLine().trim();

        productDatabase.removeProduct(input);

        System.out.println("Product removed successfully from the database.");
    }




    private void searchForProductInAllWarehouses() {
        System.out.println("Enter the name or ID of the product you are searching for: ");
        String input = scanner.nextLine().trim();

        boolean found = false;

        for (Warehouse warehouse : manager.getAllWarehouses()) {
            Product productToFind = warehouse.getProductByIdOrName(input);

            if (productToFind != null) {
                found = true;
                int amountFound = warehouse.howManyInStock(productToFind);
                System.out.println("The product was found in warehouse: " + warehouse.getWarehouseName());
                System.out.println("Amount found in stock: " + amountFound);

            }
        }

        if (!found) {
            System.out.println("The product was not found in any warehouse.");
        }
    }

}
