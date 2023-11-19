package com.example.payway.Data_Managers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payway.R;

import java.util.List;

public class ProductCardAdapter extends RecyclerView.Adapter<ProductCardAdapter.ProductViewHolder> {

    private List<Product> productList;

    public ProductCardAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        // Set the data to views in the ViewHolder
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView;
        private TextView productPriceTextView;
        // Add other views for product details (image, description, etc.)

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views from the product_card_layout
            productNameTextView = itemView.findViewById(R.id.product_name_text_view);
            productPriceTextView = itemView.findViewById(R.id.product_price_text_view);
            // Initialize other views
        }

        public void bind(Product product) {
            productNameTextView.setText(product.getProductName());
            productPriceTextView.setText(String.valueOf(product.getProductPrice()));
            // Bind other data to respective views
        }
    }
}

