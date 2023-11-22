package com.example.payway.activities_and_fragments.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payway.Data_Managers.Client;
import com.example.payway.Data_Managers.Firebase;
import com.example.payway.Data_Managers.Product;
import com.example.payway.Data_Managers.ProductCardManager;
import com.example.payway.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {

    private List<com.example.payway.Data_Managers.Product> productList;
    private ProductCardManager productCardManager;
    TextView client_name;
    Client client;
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

    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //initializing the user name textview
        client_name=view.findViewById(R.id.hellos);

        //getting user details
        Firebase.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    client =    task.getResult().toObject(Client.class);
                    if(client!=null){
                        //puting user name in the hello
                        client_name.setText("Hello "+client.getUsername());
                    }
                }
            }
        });



        productList = new ArrayList<>(); // Initialize an empty product list

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));



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