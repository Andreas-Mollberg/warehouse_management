package org.example;

import java.util.ArrayList;


public class Warehouse {
    private int warehouseNumber;
    private String warehouseLocation;
    private ArrayList<Product> products;

    public Warehouse(int warehouseNumber, String warehouseLocation) {
        this.warehouseNumber = warehouseNumber;
        this.warehouseLocation = warehouseLocation;
        this.products = new ArrayList<>();
    }

    public int getWarehouseNumber() {
        return warehouseNumber;
    }

    public void setWarehouseNumber(int warehouseNumber) {
        this.warehouseNumber = warehouseNumber;
    }

    public String getWarehouseLocation() {
        return warehouseLocation.toLowerCase();
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public void listAllProducts() {
        for (var product : products) {
            System.out.println(product);

        }
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }

    /**
     * Adds a product to the warhouse stock
     *
     * @param productToAdd The product to add to the warehouse stock
     */
    public void addProductIntoWarehouse(Product productToAdd) {
        products.add(productToAdd);

    }

    /**
     * Removes one product with the specified id
     *
     * @param id The product id to remove
     */

    public void removeProductByIdFromWarehouse(int id) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            if (product.getProductId() == id) {
                products.remove(i);
                return;
            }

        }
    }

    public boolean searchForProduct(String name) {
        var nameToSearchFor = name.toLowerCase().trim();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            if (product.getProductName().toLowerCase().equals(nameToSearchFor)) {
                return true;
            }
        }
        return false;
    }

    public boolean searchForProduct(int number) {

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            if (product.getProductId() == number) {
                return true;
            }
        }
        return false;
    }

    public int howManyInStock(String productName){
        var productNameToCount = productName.toLowerCase().trim();
        int count = 0;

        for (var product : products) {
            if (product.getProductName().toLowerCase().equals(productNameToCount)) {
                count++;

            }
        }
        return count;
    }

    public int howManyInStock(int productId){
        var productNameToCount = getProductNameFromId(productId);
        int count = 0;

        for (var product : products) {
            if (product.getProductName().toLowerCase().equals(productNameToCount)) {
                count++;

            }
        }
        return count;
    }

    public String getProductNameFromId(int id){
        for (var product : products) {
            if (product.getProductId() == id){
                return product.getProductName();
            }else{
                return null;
            }

        }
        return null;
    }

    public Product getProductObject(int id){
        for (var product : products) {
            if (product.getProductId() == id){
                return product;
            }else{
                return null;
            }

        }
        return null;
    }

    public Product getProductObject(String name){
        for (var product : products) {
            if (product.getProductName().equalsIgnoreCase(name)){
                return product;
            }else{
                return null;
            }

        }
        return null;
    }

    // Check if a product is in the warehouse by object
    public boolean containsProduct(Product product) {
        return products.contains(product);
    }

    // Remove a product by object
    public void removeProductByObject(Product product) {
        products.remove(product);
    }

    public void removeProductFromWarehouse(Product product) {
        products.remove(product);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "warehouseNumber=" + warehouseNumber +
                ", warehouseLocation='" + warehouseLocation + '\'' +
                '}';
    }
}
