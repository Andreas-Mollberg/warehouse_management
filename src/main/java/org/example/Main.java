package org.example;

public class Main {
    public static void main(String[] args) {

        WarehouseManager manager = new WarehouseManager();
        Interface menu = new Interface(manager);

        InsertExamples.addExampleWarehouses(manager);
        InsertExamples.addExampleProducts(manager);


        manager.printWarehouseStock(1);
        System.out.println();
        manager.printWarehouseStock(2);
        System.out.println();
        manager.printWarehouseStock(3);
        System.out.println("---");



        manager.searchAllWarehousesForProduct("iphone 15");


        menu.mainMenu();


    }
}