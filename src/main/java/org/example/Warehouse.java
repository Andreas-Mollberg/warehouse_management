package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private int warehouseId;
    private String warehouseName;
    private ArrayList<Product> products;

    public Warehouse(String warehouseName) {
        this.warehouseId = warehouseId;
        this.warehouseName = capitalizeName(warehouseName);
        this.products = new ArrayList<>();
    }

    public Warehouse(int warehouseId, String warehouseName) {
        this.warehouseId = warehouseId;
        this.warehouseName = capitalizeName(warehouseName);
        this.products = new ArrayList<>();
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }

    public Product getProductByIdOrName(String idOrName) {
        for (Product product : products) {
            if (String.valueOf(product.getProductId()).equals(idOrName) || product.getProductName().equalsIgnoreCase(idOrName)) {
                return product;
            }
        }
        return null;
    }

    public void listAllProducts() {
        Map<Product, Integer> productCounts = new HashMap<>();

        for (Product product : products) {
            productCounts.put(product, productCounts.getOrDefault(product, 0) + 1);
        }

        for (Map.Entry<Product, Integer> entry : productCounts.entrySet()) {
            Product product = entry.getKey();
            int count = entry.getValue();

            System.out.println(product);
            System.out.println("Amount in stock: " + count + "\n");
        }
    }

    public void addProductIntoWarehouse(Product productToAdd) {
        products.add(productToAdd);
    }

    public void removeProductFromWarehouse(Product product) {
        products.remove(product);
    }

    public int howManyInStock(Product productToCount) {
        int count = 0;
        for (Product product : products) {
            if (product.equals(productToCount)) {
                count++;
            }
        }
        return count;
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
        return "Warehouse number " + warehouseId + ":\t" + warehouseName;
    }


}
