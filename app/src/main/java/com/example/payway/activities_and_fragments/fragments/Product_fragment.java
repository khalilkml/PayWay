package com.example.payway.activities_and_fragments.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.payway.Data_Managers.Product;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.Activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Product_fragment extends Fragment {

    private static final String ARG_PRODUCT = "product";
    private static Product product;
    public Context context;
    List<Product> productcartlsit = new ArrayList<>();

    private ProductViewModel productViewModel;

    public Product_fragment() {
        // Required empty public constructor
    }

    public static Product_fragment newInstance(String param1, String param2) {
        Product_fragment fragment = new Product_fragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, (Parcelable) product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        // Populate productList and set it in ViewModel
        productViewModel.setProductList(productcartlsit);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        return view;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    //retrive data fro bundls
        @Override
        public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            // Initialize views and data structures
            ImageView productImageView = view.findViewById(R.id.product_image);
            TextView productNameTextView = view.findViewById(R.id.product_name_view);
            TextView productPriceTextView = view.findViewById(R.id.product_price_view);
            TextView productPastPriceTextView = view.findViewById(R.id.product_past_price_view);
            TextView productdescription = view.findViewById(R.id.product_description_view);
            ImageView back = view.findViewById(R.id.back);

            back.setOnClickListener(v -> requireActivity().onBackPressed());


            // Retrieve the task data from the arguments Bundle
            Bundle args = getArguments();
            if (args != null) {
                String productIdString = args.getString("productId");
                String productNameString = args.getString("productName");
                String productDescriptionString = args.getString("productDescription");
                String productPriceString = args.getString("productPrice");
                String productPastPriceString = args.getString("productPastPrice"); // Retrieve the taskDate property
                boolean isFavorite = args.getBoolean("isFavorite"); // Retrieve the isDone property
                String imageUrlString = args.getString("imageUrl");

                Glide.with(context).load(imageUrlString).into(productImageView);
                productNameTextView.setText(productNameString);
                productPriceTextView.setText("$"+productPriceString);
                productPastPriceTextView.setText("$"+productPastPriceString);
                productdescription.setText(productDescriptionString);


                product = new Product(productIdString,productNameString,productDescriptionString,productPriceString,productPastPriceString,isFavorite,imageUrlString);
            }

            Button adtocart =view.findViewById(R.id.Addtocart);

            adtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });

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
