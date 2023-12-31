package com.example.payway.Data_Managers;

import static com.example.payway.Data_Managers.Firebase.currentUserId;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.Activities.MainActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCardManager extends RecyclerView.Adapter<ProductCardManager.ProductViewHolder> {

    private List<Product> productList;
    private List<Product> favproductList =new ArrayList<>();
    private Context context;
    private MainActivity mainActivity; // Add MainActivity reference
    private MyAdapterListener listener;
    CollectionReference productListRef;
    String userId = currentUserId();
    // Assume db is an instance of FirebaseFirestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public ProductCardManager(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }
    public void setListener(MyAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.productNameTextView.setText(product.getProductName());
        holder.productPriceTextView.setText("$"+product.getProductPrice());
        holder.productPastPriceTextView.setText("$"+product.getProductPastPrice());
        Glide.with(context).load(product.getImageUrl()).into(holder.productImageView);

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
                Product favproduct = new Product(productId, productName, productDescription, productPrice, productPastPrice, isFavorite, imageUrl , Type);
                try {
                    favproductList.add(favproduct);
                } catch (NullPointerException e) {
                    // Handle the potential null pointer exception here
                    e.printStackTrace(); // or log the exception or take necessary action
                    Toast.makeText(context, "nofavorite product", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(e -> {
            // Handle any errors in retrieving data from Firebase
            Toast.makeText(context, "An Error occurred! check your Network", Toast.LENGTH_SHORT).show();
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPlaceClick(product);
                }else {
                    Toast.makeText(context, "problem in passing the product", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /// Replace "products" with your collection name and "productId" with the ID of the document you want to update
        CollectionReference productRef = db.collection("users").document(Firebase.currentUserId()).collection("favoritesproductList");
        DocumentReference productToRemoveRef = db.collection("users")
                .document(Firebase.currentUserId().toString())
                .collection("favoritesproductList")
                .document(product.getProductId());



        // Check if the product is already in favorites
        if (product.isFavorite()) {
            holder.favorite.setImageResource(R.drawable.ic_favorit_filld); // Change to your filled favorite icon
        } else {
            holder.favorite.setImageResource(R.drawable.ic_favorit2); // Change to your empty favorite icon
        }

        // Set onClickListener for the favorite button
        holder.favorite.setOnClickListener(view -> {

            // Toggle the favorite status
            product.setFavorite(!product.isFavorite());

            if (product.isFavorite()){
                // Create a Map to hold the fields you want to update
                Map<String, Object> updates = new HashMap<>();
                updates.put("Product name", product.getProductName());
                updates.put("product description",  product.getProductDescription());
                updates.put("product price",  product.getProductPrice());
                updates.put("past price",  product.getProductPastPrice());
                updates.put("image url",  product.getImageUrl());
                updates.put("Type",  product.getType());
                updates.put("is favorite",  product.isFavorite());

                // Perform the update
                productRef.document(product.getProductId())
                        .set(updates)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "not added", Toast.LENGTH_SHORT).show();
                        });
            }else {
                // Delete the specific document
                productToRemoveRef.delete()
                        .addOnSuccessListener(aVoid -> {
                            // Document successfully deleted
                            Toast.makeText(context, "removed from favorites", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Handle failure
                            Toast.makeText(context, "product not removed from favorites", Toast.LENGTH_SHORT).show();
                        });
            }

            // Update the UI based on the favorite status change
            if (product.isFavorite()) {
                holder.favorite.setImageResource(R.drawable.ic_favorit_filld); // Change to your filled favorite icon
                // Add the product to the favorites list or perform other actions
                // For example: mainActivity.addToFavorites(product);
            } else {
                holder.favorite.setImageResource(R.drawable.ic_favorit2); // Change to your empty favorite icon
                // Remove the product from the favorites list or perform other actions
                // For example: mainActivity.removeFromFavorites(product);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView productPastPriceTextView;
        ImageButton favorite;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image_view);
            productNameTextView = itemView.findViewById(R.id.product_name);
            productPriceTextView = itemView.findViewById(R.id.product_price);
            productPastPriceTextView = itemView.findViewById(R.id.product_past_price);
            favorite = itemView.findViewById(R.id.favorite_icon);

        }
    }
}

