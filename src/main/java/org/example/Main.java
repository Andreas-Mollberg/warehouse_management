package org.example;

public class Main {
    public static void main(String[] args) {
        WarehouseManager manager = new WarehouseManager();
        Interface menu = new Interface(manager);
        InsertExamples.addExampleWarehouses(manager);
        InsertExamples.addExampleProducts(manager);

        menu.mainMenu();





    }
}