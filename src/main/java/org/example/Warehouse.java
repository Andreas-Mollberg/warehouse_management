package org.example;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class Warehouse {
    private int warehouseId;
    private String warehouseName;
    private ArrayList<Product> products;
    private Map<Product, Integer> productCounts;

    public Warehouse(int warehouseId, String warehouseName) {
        this.warehouseId = warehouseId;
        String firstLetter = warehouseName.substring(0, 1).toUpperCase();
        String theRest = warehouseName.substring(1);
        this.warehouseName = firstLetter + theRest;
        this.products = new ArrayList<>();
        this.productCounts = new HashMap<>();
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName.toLowerCase();
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void listAllProducts() {
        for (Product product : products) {
            int count = productCounts.getOrDefault(product, 0);
            System.out.println(product + "\nAmount in stock: " + count + "\n");
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
        // Update the product count
        productCounts.put(productToAdd, productCounts.getOrDefault(productToAdd, 0) + 1);
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
                // Update the product count
                productCounts.put(product, productCounts.getOrDefault(product, 0) - 1);
                return;
            }
        }
    }

    public boolean searchForProduct(String name) {
        var nameToSearchFor = name.toLowerCase().trim();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);

            if (product.getProductName().equalsIgnoreCase(nameToSearchFor)) {
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


    public int howManyInStock(Product productToCount){
        int count = 0;

        for (var product : products) {
            if (product == productToCount) {
                count++;

            }
        }
        return count;
    }

    public String getProductNameFromId(int id){
        for (var product : products) {
            if (product.getProductId() == id){
                return product.getProductName().toLowerCase();
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


    public boolean containsProduct(Product product) {
        return products.contains(product);
    }


    public void removeProductByObject(Product product) {
        products.remove(product);
    }

    private String capitalizeName(String name) {
        String firstLetter = name.substring(0, 1).toUpperCase();
        String theRest = name.substring(1);
        return firstLetter + theRest;
    }

    public String getWarehouseNameCapitalized() {
        return capitalizeName(warehouseName);
    }

    @Override
    public String toString() {
        return "Warehouse number " + warehouseId + ":" +
                "\t" + warehouseName;
    }
}
