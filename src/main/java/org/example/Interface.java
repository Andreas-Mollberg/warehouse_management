package org.example;

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
                    4. Transfer products between warehouses.
                    5. Adjust stock of products in a warehouse.
                    6. Product management.
                    7. Exit Warehouse Manager.\s""");

            System.out.println("Enter 1-7: ");
            String userInput = scanner.nextLine();


            try {
                int mainMenuSelection = Integer.parseInt(userInput);

                if (mainMenuSelection < 1 || mainMenuSelection > 7) {
                    System.err.println("Invalid input. Please enter a valid number between 1 and 7.");
                    continue;
                }

                switch (mainMenuSelection) {
                    case 1:
                        manager.printAllWarehouses();
                        break;

                    case 2:
                        System.out.println("Which warehouse do you wish to display the inventory of? ");
                        manager.printAllWarehouses();

                        System.out.println("Enter warehouse number or name: ");
                        String warehouseIdOrName = scanner.nextLine();

                        Warehouse selectedWarehouse = manager.getWarehouseFromIdOrName(warehouseIdOrName);
                        selectedWarehouse.listAllProducts();
                        break;

                    case 3:
                        createNewWarehouse();
                        break;

                    case 4:
                        transferProduct();
                        break;

                    case 5:
                        adjustStockLevel();
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

    private void createNewWarehouse() {
        System.out.println("Enter ID for the new warehouse: ");
        String inputNumber = scanner.nextLine();
        int newWarehouseId = Integer.parseInt(inputNumber);
        System.out.println("Enter the name of the new warehouse: ");
        String newWarehouseName = scanner.nextLine().trim();
        String newWarehouseNameFirstLetter = newWarehouseName.substring(0, 1).toUpperCase();
        String restOfName = newWarehouseName.substring(1).toLowerCase();
        newWarehouseName = newWarehouseNameFirstLetter + restOfName;

        Warehouse newWarehouse = new Warehouse(newWarehouseId, newWarehouseName);
        manager.addWarehouseToList(newWarehouse);
    }

    private void transferProduct() {
        System.out.println("Which warehouse do you want to transfer from? ");
        manager.printAllWarehouses();

        System.out.println("Enter name or ID for the warehouse you wish to transfer from: ");
        String fromWarehouseThisIdOrName = scanner.nextLine();

        Warehouse sourceWarehouse = manager.getWarehouseFromIdOrName(fromWarehouseThisIdOrName);
        sourceWarehouse.listAllProducts();


        System.out.println("Enter ID or name of the product you wish to transfer: ");
        String productIdOrName = scanner.nextLine().trim();
        Product productToTransfer = manager.getProductByIdOrName(productIdOrName);

        System.out.println("Enter the name or ID for the warehouse you would like" +
                " to transfer the product into.");
        manager.printAllWarehouses();
        String toWarehouseThisIdOrName = scanner.nextLine();

        Warehouse destinationWarehouse = manager.getWarehouseFromIdOrName(toWarehouseThisIdOrName);
        manager.transferProductBetweenWarehouses(sourceWarehouse, destinationWarehouse, productToTransfer);
        return;
    }

    private void adjustStockLevel() {
        System.out.println("At which warehouse would you like to adjust the stocks? ");
        manager.printAllWarehouses();

        String selectedWarehouseIdOrName = scanner.nextLine();
        Warehouse warehouseToAdjustStocksIn = manager.getWarehouseFromIdOrName(selectedWarehouseIdOrName);

        System.out.println("Current inventory of " + warehouseToAdjustStocksIn.getWarehouseNameCapitalized());
        warehouseToAdjustStocksIn.listAllProducts();

        System.out.println("Enter ID or name of product you wish you adjust the stock level of: ");
        var productToAdjustIdOrName = scanner.nextLine();

        Product productToAdjust = manager.getProductByIdOrName(productToAdjustIdOrName);

        System.out.println("Current stock of " + productToAdjust.getProductName() + " is: "
                + warehouseToAdjustStocksIn.howManyInStock(productToAdjust));

        System.out.println("Enter new total amount in stock: ");
        String inputNewStockAmount = scanner.nextLine();
        int newStockAmount = Integer.parseInt(inputNewStockAmount);

        manager.adjustProductAmountInWarehouse(warehouseToAdjustStocksIn, productToAdjust, newStockAmount);

        var currentStock = warehouseToAdjustStocksIn.howManyInStock(productToAdjust);

        System.out.println("Current stock of " + productToAdjust.getProductName() + " at warehouse "
                + warehouseToAdjustStocksIn.getWarehouseNameCapitalized() + " is: " + currentStock);
    }

    public void productMenu() {
        while (true) {
            System.out.println("***Product Manager***\n");

            System.out.println("""
                    1. Add a new product
                    2. Remove a product
                    3. Search for product in all warehouses.
                    4. Go back to main menu.""");

            System.out.println("Enter 1-6: ");
            String userInput = scanner.nextLine();
            int productMenuSelection = Integer.parseInt(userInput);

            switch (productMenuSelection) {
                case 1:
                    addNewProduct();
                    break;

                case 2:
                    // Insert method to remove product
                    break;

                case 3:
                    searchForProductInAllWarehouses();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Incorrect input. Input must be a number between 1 and 6.");
            }
            break;

        }

    }

    private void addNewProduct() {
        System.out.println("Enter the ID for the new product: ");
        String newPorudctId = scanner.nextLine();
        int newProductId = Integer.parseInt(newPorudctId);
        System.out.println("Enter the name of the new product: ");
        String newProductName = scanner.nextLine().trim();
        System.out.println("Enter the price of the new product: ");
        String inputValue = scanner.nextLine();
        double newProductPrice = Double.parseDouble(inputValue);
        System.out.println("Enter a description of the new product: ");
        String newProductDescription = scanner.nextLine().trim();

        Product newProduct = new Product(newProductName, newProductId, newProductPrice, newProductDescription);


        System.out.println("Enter the warehouse ID or name to add the product to: ");
        manager.printAllWarehouses();

        System.out.println("Enter warehouse ID or name: ");
        String destinationWarehouseIdOrName = scanner.nextLine();

        Warehouse warehouse = manager.getWarehouseFromIdOrName(destinationWarehouseIdOrName);

        System.out.println(newProduct.getProductName() + " has been added into the stock of "
                + warehouse.getWarehouseName());
    }

    private void searchForProductInAllWarehouses() {
        System.out.println("ID or name of product you wish to search for: ");
        String productIdOrName = scanner.nextLine();

        Product productToFind = manager.getProductByIdOrName(productIdOrName);
        manager.searchAndPrintProductInWarehouses(productToFind);
    }
}



