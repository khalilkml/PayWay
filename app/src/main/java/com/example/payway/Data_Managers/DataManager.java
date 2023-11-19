package com.example.payway.Data_Managers;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String COLLECTION_PRODUCTS = "products"; // Name of your Firestore collection

    private FirebaseFirestore db;
    private CollectionReference productsCollection;

    public DataManager() {
        db = FirebaseFirestore.getInstance();
        productsCollection = db.collection(COLLECTION_PRODUCTS);
    }

    // Method to fetch all products from Firestore
    public void getAllProducts(DataCallback<List<com.example.payway.Data_Managers.Product>> callback) {
        productsCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<com.example.payway.Data_Managers.Product> productList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        // Convert Firestore document to a Product object
                        com.example.payway.Data_Managers.Product product = document.toObject(com.example.payway.Data_Managers.Product.class);
                        productList.add(product);
                    }
                    // Pass the list of Product objects to the callback
                    callback.onSuccess(productList);
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    callback.onError(e.getMessage());
                });
    }


    // Define a method to convert Firestore data (queryDocumentSnapshots) to Product objects
    // Implement this method based on your Firestore schema

    // Callback interface to handle data retrieval
    public interface DataCallback<T> {
        void onSuccess(T data);
        void onError(String errorMsg);
    }
}

