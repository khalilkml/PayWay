package com.example.payway.Data_Managers;

public class Product {
    private String productId;
    private String productName;
    private String productDescription;
    private double productPrice;
    private double productPastPrice;
    private boolean isFavorite;
    private String imageUrl;

    public Product(String productId, String productName, String productDescription, double productPrice, double productPastPrice, boolean isFavorite, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productPastPrice = productPastPrice;
        this.isFavorite = isFavorite;
        this.imageUrl = imageUrl;
    }

    // Getters and setters for productId
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    // Getters and setters for productName
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    // Getters and setters for productDescription
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    // Getters and setters for productPrice
    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    // Getters and setters for productPastPrice
    public double getProductPastPrice() {
        return productPastPrice;
    }

    public void setProductPastPrice(double productPastPrice) {
        this.productPastPrice = productPastPrice;
    }

    // Getters and setters for isFavorite
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    // Getters and setters for imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
