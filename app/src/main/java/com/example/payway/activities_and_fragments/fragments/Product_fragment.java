package com.example.payway.activities_and_fragments.fragments;

import static com.example.payway.Data_Managers.Firebase.currentUserId;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.payway.Data_Managers.Client;
import com.example.payway.Data_Managers.Product;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.Activities.MainActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product_fragment extends Fragment {

    private static final String ARG_PRODUCT = "product";
    private static Product product;
    public Context context;
    List<Product> productcartlsit = new ArrayList<>();
    List<Product> AllproductListofuser= new ArrayList<>();

    private ProductViewModel productViewModel;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Client client;

    // Get the current user's ID
    String userId = currentUserId();
    CollectionReference productListRef;
    Button adtocart;
    ImageView removeicon;
    FrameLayout removefromcart;


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
            adtocart =view.findViewById(R.id.Addtocart);
            removefromcart =view.findViewById(R.id.removefromtocart);
            removeicon = view.findViewById(R.id.removefromtocarticon);
            removeicon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange), PorterDuff.Mode.SRC_ATOP);


            FrameLayout back = view.findViewById(R.id.back);




            // Reference to the user's product list collection
            productListRef = db.collection("users")
                    .document(userId)
                    .collection("productList");




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
                String TypeString = args.getString("Type");

                Glide.with(context).load(imageUrlString).into(productImageView);
                productNameTextView.setText(productNameString);
                productPriceTextView.setText("$"+productPriceString);
                productPastPriceTextView.setText("$"+productPastPriceString);
                productdescription.setText(productDescriptionString);


                product = new Product(productIdString,productNameString,productDescriptionString,productPriceString,productPastPriceString,isFavorite,imageUrlString ,TypeString);

            }

//            checkIfProductExistsInCartforchangebutton(product);

            back.setOnClickListener(v -> requireActivity().onBackPressed());

            AllproductListofuser.clear();
            productListRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        // Access each document's data here
                        Product product = document.toObject(Product.class);
                        AllproductListofuser.add(product);
                    }

                    boolean isProductInCart = isProductInList(product);

                    if (isProductInCart) {
                        adtocart.setEnabled(false);
                        removeicon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange), PorterDuff.Mode.SRC_ATOP);
                    } else {
                        adtocart.setEnabled(true);
                        removeicon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.lightGray), PorterDuff.Mode.SRC_ATOP);
                    }
                } else {
                    // Handle errors while fetching data
                    Toast.makeText(context, "Failed to retrieve product list", Toast.LENGTH_SHORT).show();
                }
            });

            adtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkIfProductExistsInCart(product);
                }
            });

            removefromcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    removeProductFromCart(product.getProductId());
                }
            });
            removeicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    removeProductFromCart(product.getProductId());
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

    private void checkIfProductExistsInCart(Product product) {
        // Search in the AllproductListofuser list for the product by its ID
        boolean productExists = false;
        for (Product userProduct : AllproductListofuser) {
            if (userProduct.getProductId().equals(product.getProductId())) {
                // Product found in the user's list
                productExists = true;
                break;
            }

        }
        if (productExists) {
            Toast.makeText(requireContext(), "Product Already in Cart", Toast.LENGTH_SHORT).show();
        } else {
            // Product does not exist in cart; add it
            addProductToCart(product);
        }
    }

    private void addProductToCart(Product product) {
        Map<String, Object> productData = new HashMap<>();
        productData.put("productId", product.getProductId());
        productData.put("productName", product.getProductName());
        productData.put("productDescription", product.getProductDescription());
        productData.put("productPrice", product.getProductPrice());
        productData.put("productPastPrice", product.getProductPastPrice());
        productData.put("isFavorite", product.isFavorite());
        productData.put("imageUrl", product.getImageUrl());

        productListRef.add(productData)
                .addOnSuccessListener(documentReference -> {
                    AllproductListofuser.add(product);
                    removeicon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.orange), PorterDuff.Mode.SRC_ATOP);
                    adtocart.setEnabled(false);
                    Toast.makeText(context, "Product added to cart", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to add product", Toast.LENGTH_SHORT).show();
                    Log.e("Firestore", "Error adding product to cart", e);
                });
    }

    private void removeProductFromCart(String productIdToRemove) {
        // Reference to the specific document of the product in the user's cart
        productListRef.whereEqualTo("productId", productIdToRemove)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            document.getReference().delete()
                                    .addOnSuccessListener(aVoid -> {
                                        AllproductListofuser.remove(getProductById(productIdToRemove));
                                        adtocart.setEnabled(true);
                                        removeicon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.lightGray), PorterDuff.Mode.SRC_ATOP);
                                        Toast.makeText(context, "Product removed from cart", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(context, "Failed to remove product from cart", Toast.LENGTH_SHORT).show();
                                        Log.e("Firestore", "Error removing product from cart", e);
                                    });
                        }
                    } else {
                        Toast.makeText(context, "Failed to retrieve product list", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private Product getProductById(String productId) {
        for (Product product : AllproductListofuser) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null; // If the product with the given ID is not found
    }

    private boolean isProductInList(Product product) {
        for (Product userProduct : AllproductListofuser) {
            if (userProduct.getProductId().equals(product.getProductId())) {
                return true;
            }
        }
        return false;
    }

}
