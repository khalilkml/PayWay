package com.example.payway.activities_and_fragments.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payway.Data_Managers.Firebase;
import com.example.payway.Data_Managers.MyAdapterListener;
import com.example.payway.Data_Managers.Product;
import com.example.payway.Data_Managers.ProductCardManager;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.Activities.MainActivity;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class Favorit extends Fragment implements MyAdapterListener {

    private ProductCardManager productCardManager;
    private List<com.example.payway.Data_Managers.Product> favproductList;
    private List<com.example.payway.Data_Managers.Product> AllproductList;

    public Favorit() {
        // Required empty public constructor
    }

    public static Favorit newInstance() {
        Favorit fragment = new Favorit();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorit, container, false);

        AllproductList = new ArrayList<>(); // Initialize an empty product list
        favproductList = new ArrayList<>(); // Initialize an empty product list

        RecyclerView FavrecyclerView = view.findViewById(R.id.favorite_recycle);
        FavrecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        productCardManager = new ProductCardManager(getContext(), favproductList);
        productCardManager.setListener(this);
        FavrecyclerView.setAdapter(productCardManager);

        //getting all favorite products
        Firebase.getAllProductsfromfavorit().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                String productId = document.getId();
                String productName = document.getString("Product name");
                String productDescription = document.getString("product description");
                String productPrice = document.getString("product price");
                String productPastPrice = document.getString("past price");
                boolean isFavorite = Boolean.TRUE.equals(document.getBoolean("is favorite"));
                String imageUrl = document.getString("image url");
                String Type = document.getString("Type");

                // Create Product object and add it to the product list
                Product product = new Product(productId, productName, productDescription, productPrice, productPastPrice, isFavorite, imageUrl , Type);
                AllproductList.add(product);
                //testing for favorite products
                if (product.isFavorite()){
                    favproductList.add(product);
                }

            }
            // Notify the adapter about the data change
            productCardManager.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle any errors in retrieving data from Firebase
            Toast.makeText(requireContext(), "An Error occurred! check your Network", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
    public void onPlaceClick(Product product) {
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.changeFromHomeToProductWithProductdata(product);

        }
    }
}