package com.example.payway.activities_and_fragments.fragments;

import static com.example.payway.Data_Managers.Firebase.currentUserId;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payway.Data_Managers.Client;
import com.example.payway.Data_Managers.Firebase;
import com.example.payway.Data_Managers.MyAdapterListener;
import com.example.payway.Data_Managers.Product;
import com.example.payway.Data_Managers.ProductCardManager;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.Activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment implements MyAdapterListener {

    private List<com.example.payway.Data_Managers.Product> productList;
    private List<com.example.payway.Data_Managers.Product> AllproductList;
    private ProductCardManager productCardManager;
    TextView client_name;
    Client client;

    FrameLayout watch_frame;
    ImageView wacthes;
    FrameLayout T_shorts_frame;
    ImageView T_shorts;
    FrameLayout caps_frame;
    ImageView caps;
    FrameLayout jacket_frame;
    ImageView jacket;
    FrameLayout backpack_frame;
    ImageView backpack;
    FrameLayout shoas_frame;
    ImageView shoas;
    FrameLayout shorts_frame;
    ImageView shorts;
    FirebaseFirestore db;
    CollectionReference productListRef;
    String userId = currentUserId();



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

        watch_frame = view.findViewById(R.id.watch_frame);
        wacthes= view.findViewById(R.id.wacthes);
        T_shorts_frame= view.findViewById(R.id.T_shorts_frame);
        T_shorts= view.findViewById(R.id.T_shorts);
        caps_frame= view.findViewById(R.id.caps_frame);
        caps= view.findViewById(R.id.caps);
        jacket_frame= view.findViewById(R.id.jacket_frame);
        jacket= view.findViewById(R.id.jacket);
        backpack_frame= view.findViewById(R.id.backpack_frame);
        backpack= view.findViewById(R.id.backpack);
        shoas_frame= view.findViewById(R.id.shoas_frame);
        shoas= view.findViewById(R.id.shoas);
        shorts_frame= view.findViewById(R.id.shorts_frame);
        shorts= view.findViewById(R.id.shorts);


        watch_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onwacthesClick();
            }
        });
        T_shorts_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onT_shortsClick();
            }
        });
        caps_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oncapsClick();
            }
        });
        jacket_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onjacketClick();
            }
        });
        backpack_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onbackpackClick();
            }
        });
        shoas_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onshoasClick();
            }
        });
        shorts_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onshortsClick();
            }
        });



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



        AllproductList = new ArrayList<>(); // Initialize an empty product list
        productList = new ArrayList<>(); // Initialize an empty product list

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));



        // Initialize the adapter with an empty product list
        productCardManager = new ProductCardManager(getContext(), productList);
        productCardManager.setListener(this);
        recyclerView.setAdapter(productCardManager);

        // Retrieve wathes products from Firebase and update the product list
        Firebase.getAllProducts().addOnSuccessListener(queryDocumentSnapshots -> {
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
                if (product.getType().equals("watches")){
                    productList.add(product);
                }

            }
            // Notify the adapter about the data change
            productCardManager.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            // Handle any errors in retrieving data from Firebase
             Toast.makeText(requireContext(), "An Error occurred! check your Network", Toast.LENGTH_SHORT).show();
        });


        //set the selected category by default
        watch_frame.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));
        watch_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_orange_background));
        wacthes.setImageResource(R.drawable.watch_svgrepo_com_field);
        wacthes.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));




        return view;
    }

    public void onPlaceClick(Product product) {
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.changeFromHomeToProductWithProductdata(product);

        }
    }


    public void onwacthesClick(){
        //set the non selected colors
        T_shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        T_shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        T_shorts.setImageResource(R.drawable.short_sleeve_svgrepo_com__1_);
        T_shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        caps_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        caps_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        caps.setImageResource(R.drawable.cap_svgrepo_com);
        caps.setBackgroundColor(Color.parseColor("#ECEBEB"));

        jacket_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        jacket_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        jacket.setImageResource(R.drawable.jacket_illustration_2_svgrepo_com);
        jacket.setBackgroundColor(Color.parseColor("#ECEBEB"));

        backpack_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        backpack_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        backpack.setImageResource(R.drawable.backpack_bag_holidays_svgrepo_com);
        backpack.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shoas_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shoas_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shoas.setImageResource(R.drawable.shoe_store_svgrepo_com);
        shoas.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shorts.setImageResource(R.drawable.short_jeans_clothing_wear_cloth_fashion_svgrepo_com);
        shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        //set the selected one colors
        watch_frame.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));
        watch_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_orange_background));
        wacthes.setImageResource(R.drawable.watch_svgrepo_com_field);
        wacthes.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));

        productList.clear();
        //loop on the all products list
        for (Product product1 : AllproductList){
            if (product1.getType().equals("watches")){
                productList.add(product1);
            }
            // Notify the adapter about the data change
            productCardManager.notifyDataSetChanged();
        }
    }

    public void onT_shortsClick(){
        //set the non selected colors
        watch_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        watch_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        wacthes.setImageResource(R.drawable.watch_svgrepo_com);
        wacthes.setBackgroundColor(Color.parseColor("#ECEBEB"));

        caps_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        caps_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        caps.setImageResource(R.drawable.cap_svgrepo_com);
        caps.setBackgroundColor(Color.parseColor("#ECEBEB"));

        jacket_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        jacket_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        jacket.setImageResource(R.drawable.jacket_illustration_2_svgrepo_com);
        jacket.setBackgroundColor(Color.parseColor("#ECEBEB"));

        backpack_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        backpack_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        backpack.setImageResource(R.drawable.backpack_bag_holidays_svgrepo_com);
        backpack.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shoas_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shoas_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shoas.setImageResource(R.drawable.shoe_store_svgrepo_com);
        shoas.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shorts.setImageResource(R.drawable.short_jeans_clothing_wear_cloth_fashion_svgrepo_com);
        shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        //set the selected one colors
        T_shorts_frame.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));
        T_shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_orange_background));
        T_shorts.setImageResource(R.drawable.short_sleeve_svg_com__1_filled);
        T_shorts.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));

        productList.clear();
        //loop on the all products list
        for (Product product1 : AllproductList){
            if (product1.getType().equals("T_shorts")){
                productList.add(product1);
            }
            // Notify the adapter about the data change
            productCardManager.notifyDataSetChanged();
        }
    }

    public void oncapsClick(){
        //set the non selected colors
        watch_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        watch_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        wacthes.setImageResource(R.drawable.watch_svgrepo_com);
        wacthes.setBackgroundColor(Color.parseColor("#ECEBEB"));

        T_shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        T_shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        T_shorts.setImageResource(R.drawable.short_sleeve_svgrepo_com__1_);
        T_shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        jacket_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        jacket_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        jacket.setImageResource(R.drawable.jacket_illustration_2_svgrepo_com);
        jacket.setBackgroundColor(Color.parseColor("#ECEBEB"));

        backpack_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        backpack_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        backpack.setImageResource(R.drawable.backpack_bag_holidays_svgrepo_com);
        backpack.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shoas_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shoas_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shoas.setImageResource(R.drawable.shoe_store_svgrepo_com);
        shoas.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shorts.setImageResource(R.drawable.short_jeans_clothing_wear_cloth_fashion_svgrepo_com);
        shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        //set the selected one colors
        caps_frame.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));
        caps_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_orange_background));
        caps.setImageResource(R.drawable.cap_svgrepo_com_filled);
        caps.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));

        productList.clear();
        //loop on the all products list
        for (Product product1 : AllproductList){
            if (product1.getType().equals("Caps")){
                productList.add(product1);
            }
            // Notify the adapter about the data change
            productCardManager.notifyDataSetChanged();
        }
    }

    public void onjacketClick(){
        //set the non selected colors
        watch_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        watch_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        wacthes.setImageResource(R.drawable.watch_svgrepo_com);
        wacthes.setBackgroundColor(Color.parseColor("#ECEBEB"));

        T_shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        T_shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        T_shorts.setImageResource(R.drawable.short_sleeve_svgrepo_com__1_);
        T_shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        caps_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        caps_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        caps.setImageResource(R.drawable.cap_svgrepo_com);
        caps.setBackgroundColor(Color.parseColor("#ECEBEB"));

        backpack_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        backpack_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        backpack.setImageResource(R.drawable.backpack_bag_holidays_svgrepo_com);
        backpack.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shoas_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shoas_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shoas.setImageResource(R.drawable.shoe_store_svgrepo_com);
        shoas.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shorts.setImageResource(R.drawable.short_jeans_clothing_wear_cloth_fashion_svgrepo_com);
        shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        //set the selected one colors
        jacket_frame.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));
        jacket_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_orange_background));
        jacket.setImageResource(R.drawable.jacket_svgrepo_com_filled);
        jacket.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));

        productList.clear();
        //loop on the all products list
        for (Product product1 : AllproductList){
            if (product1.getType().equals("jacket")){
                productList.add(product1);
            }
            // Notify the adapter about the data change
            productCardManager.notifyDataSetChanged();
        }
    }
    public void onbackpackClick(){
        //set the non selected colors
        watch_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        watch_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        wacthes.setImageResource(R.drawable.watch_svgrepo_com);
        wacthes.setBackgroundColor(Color.parseColor("#ECEBEB"));

        T_shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        T_shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        T_shorts.setImageResource(R.drawable.short_sleeve_svgrepo_com__1_);
        T_shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        caps_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        caps_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        caps.setImageResource(R.drawable.cap_svgrepo_com);
        caps.setBackgroundColor(Color.parseColor("#ECEBEB"));

        jacket_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        jacket_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        jacket.setImageResource(R.drawable.jacket_illustration_2_svgrepo_com);
        jacket.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shoas_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shoas_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shoas.setImageResource(R.drawable.shoe_store_svgrepo_com);
        shoas.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shorts.setImageResource(R.drawable.short_jeans_clothing_wear_cloth_fashion_svgrepo_com);
        shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        //set the selected one colors
        backpack_frame.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));
        backpack_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_orange_background));
        backpack.setImageResource(R.drawable.backpack_bag_filled);
        backpack.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));

        productList.clear();
        //loop on the all products list
        for (Product product1 : AllproductList){
            if (product1.getType().equals("backpack")){
                productList.add(product1);
            }
            // Notify the adapter about the data change
            productCardManager.notifyDataSetChanged();
        }
    }
    public void onshoasClick(){
        //set the non selected colors
        watch_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        watch_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        wacthes.setImageResource(R.drawable.watch_svgrepo_com);
        wacthes.setBackgroundColor(Color.parseColor("#ECEBEB"));

        T_shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        T_shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        T_shorts.setImageResource(R.drawable.short_sleeve_svgrepo_com__1_);
        T_shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        caps_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        caps_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        caps.setImageResource(R.drawable.cap_svgrepo_com);
        caps.setBackgroundColor(Color.parseColor("#ECEBEB"));

        backpack_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        backpack_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        backpack.setImageResource(R.drawable.backpack_bag_holidays_svgrepo_com);
        backpack.setBackgroundColor(Color.parseColor("#ECEBEB"));

        jacket_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        jacket_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        jacket.setImageResource(R.drawable.jacket_illustration_2_svgrepo_com);
        jacket.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shorts.setImageResource(R.drawable.short_jeans_clothing_wear_cloth_fashion_svgrepo_com);
        shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        //set the selected one colors
        shoas_frame.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));
        shoas_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_orange_background));
        shoas.setImageResource(R.drawable.shoe_filled);
        shoas.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));

        productList.clear();
        //loop on the all products list
        for (Product product1 : AllproductList){
            if (product1.getType().equals("shoas")){
                productList.add(product1);
            }
            // Notify the adapter about the data change
            productCardManager.notifyDataSetChanged();
        }
    }
    public void onshortsClick(){
        //set the non selected colors
        watch_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        watch_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        wacthes.setImageResource(R.drawable.watch_svgrepo_com);
        wacthes.setBackgroundColor(Color.parseColor("#ECEBEB"));

        T_shorts_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        T_shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        T_shorts.setImageResource(R.drawable.short_sleeve_svgrepo_com__1_);
        T_shorts.setBackgroundColor(Color.parseColor("#ECEBEB"));

        caps_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        caps_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        caps.setImageResource(R.drawable.cap_svgrepo_com);
        caps.setBackgroundColor(Color.parseColor("#ECEBEB"));

        backpack_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        backpack_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        backpack.setImageResource(R.drawable.backpack_bag_holidays_svgrepo_com);
        backpack.setBackgroundColor(Color.parseColor("#ECEBEB"));

        shoas_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        shoas_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        shoas.setImageResource(R.drawable.shoe_store_svgrepo_com);
        shoas.setBackgroundColor(Color.parseColor("#ECEBEB"));

        jacket_frame.setBackgroundColor(Color.parseColor("#ECEBEB"));
        jacket_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_gray_background));
        jacket.setImageResource(R.drawable.jacket_illustration_2_svgrepo_com);
        jacket.setBackgroundColor(Color.parseColor("#ECEBEB"));

        //set the selected one colors
        shorts_frame.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));
        shorts_frame.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.rounded_orange_background));
        shorts.setImageResource(R.drawable.short_pants_filled);
        shorts.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.orange));

        productList.clear();
        //loop on the all products list
        for (Product product1 : AllproductList){
            if (product1.getType().equals("shorts")){
                productList.add(product1);
            }
            // Notify the adapter about the data change
            productCardManager.notifyDataSetChanged();
        }
    }



}