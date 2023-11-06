package org.example;

import java.math.BigDecimal;

public class Product {
    private String productName;
    private int productId;
    private BigDecimal productPrice;
    private String productDescription;


    public Product(String productName, int productId, double productPrice, String productDescription) {
        if (productId < 0 || productPrice < 0) {
            throw new IllegalArgumentException("Id and Price must be non-negative.");
        }

        this.productName = productName;
        this.productId = productId;
        this.productPrice = BigDecimal.valueOf(productPrice);
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = BigDecimal.valueOf(productPrice);
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public String toString() {
        return "ProductID: " + productId +
                "\nProduct: " + productName +
                "\nPrice: " + productPrice +
                "\nDescription: " + productDescription;
    }
}
