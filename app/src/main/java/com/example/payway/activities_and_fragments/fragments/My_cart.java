package com.example.payway.activities_and_fragments.fragments;

import static com.example.payway.Data_Managers.Firebase.currentUserId;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payway.Data_Managers.Product;
import com.example.payway.Data_Managers.ProductCardManager;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.Activities.MainActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class My_cart extends Fragment  {

    private ProductViewModel productViewModel;
    private ProductCardManager productCardManager;

    private List<Product> productList;


    public My_cart() {
        // Required empty public constructor
    }

    public static My_cart newInstance(String param1, String param2) {
        My_cart fragment = new My_cart();
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
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);


        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        ImageView back = view.findViewById(R.id.back);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view2);
        //2 cards in row
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        // Get reference to Firestore collection containing the user's product list
        CollectionReference productListRef = FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUserId())
                .collection("productList");

        // Retrieve data from Firestore
        productListRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Product> retrievedProductList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Convert Firestore document to Product object
                    Product product = document.toObject(Product.class);
                    retrievedProductList.add(product);
                }

                // Update the RecyclerView adapter with the retrieved product list
                productCardManager = new ProductCardManager(getContext(), retrievedProductList);
                recyclerView.setAdapter(productCardManager);
            } else {
                // Handle unsuccessful Firestore query
                Log.e("Firestore", "Error getting documents: ", task.getException());
            }
        });



        back.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Access the MainActivity
        MainActivity mainActivity = (MainActivity) requireActivity();
        // Hide the bottom app bar
        mainActivity.hideBottomAppBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Access the MainActivity
        MainActivity mainActivity = (MainActivity) requireActivity();
        // Hide the bottom app bar
        mainActivity.showBottomAppBar();
    }

}