package com.example.payway.activities_and_fragments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payway.Data_Managers.Firebase;
import com.example.payway.Data_Managers.Product;
import com.example.payway.Data_Managers.ProductCardManager;
import com.example.payway.R;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {

    private List<com.example.payway.Data_Managers.Product> productList;
    private ProductCardManager productCardManager;

    public Home() {
        // Required empty public constructor
    }


    public static Home newInstance() {
        Home fragment = new Home();
        Bundle args = new Bundle();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        productList = new ArrayList<>(); // Initialize an empty product list

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter with an empty product list
        productCardManager = new ProductCardManager(getContext(), productList);
        recyclerView.setAdapter(productCardManager);

        // Retrieve products from Firebase and update the product list
        Firebase.getAllProducts().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                String productId = document.getId();
                String productName = document.getString("Product name");
                String productDescription = document.getString("product description");
                String productPrice = document.getString("product price");
                String productPastPrice = document.getString("past price");
                boolean isFavorite = Boolean.TRUE.equals(document.getBoolean("is favorite"));
                String imageUrl = document.getString("image url");

                // Create Product object and add it to the product list
                Product product = new Product(productId, productName, productDescription, productPrice, productPastPrice, isFavorite, imageUrl);
                productList.add(product);
            }
            // Notify the adapter about the data change
            productCardManager.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle any errors in retrieving data from Firebase
        });

        return view;
    }
}