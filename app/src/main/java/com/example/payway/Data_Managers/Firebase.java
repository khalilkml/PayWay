package com.example.payway.Data_Managers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class    Firebase {

    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }
    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }

    public static boolean isLoggedIn() {
        return currentUserId() != null;
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    public static Task<DocumentSnapshot> getProduct(String productId) {
        return FirebaseFirestore.getInstance().collection("products")
                .document(productId)
                .get();
    }

    public static StorageReference getProductImageStorageRef(String imageUrl) {
        return FirebaseStorage.getInstance().getReference().child("image url")
                .child(imageUrl);
    }

    public static Task<QuerySnapshot> getAllProducts() {
        return FirebaseFirestore.getInstance().collection("products").get();
    }
    public static Task<QuerySnapshot> getAllProductsfromfavorit() {
        return FirebaseFirestore.getInstance().collection("users").document(Firebase.currentUserId()).collection("favoritesproductList").get();
    }

    // Method to retrieve product data with its image from Cloud Storage
    public static Task<QuerySnapshot> getProductsWithImages() {
        // Assuming each product document in Firestore has a field 'imageUrl' representing the image reference
        // Change 'imageUrl' to your actual field name representing the image reference
        return FirebaseFirestore.getInstance().collection("products")
                .get()
                .continueWithTask(task -> {
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        String imageUrl = document.getString("image url");
                        StorageReference imageRef = getProductImageStorageRef(imageUrl);
                        // Here you can handle the image reference as needed
                        // You might want to associate images with respective products and return a custom data structure
                        // For simplicity, this example returns the QuerySnapshot with products only
                    }
                    return task;
                });

    }
}
