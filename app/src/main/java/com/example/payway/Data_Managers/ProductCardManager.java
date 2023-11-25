package com.example.payway.Data_Managers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.Activities.MainActivity;

import java.util.List;

public class ProductCardManager extends RecyclerView.Adapter<ProductCardManager.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private MainActivity mainActivity; // Add MainActivity reference
    private MyAdapterListener listener;


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



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPlaceClick(product);
                    Log.d("log", "card  " + product.getProductPrice());

                }else {
                    Toast.makeText(context, "nothings hapen", Toast.LENGTH_SHORT).show();
                }
            }
        });



//        // handling click
//        //still the error of getting context
//        holder.itemView.setOnClickListener(view -> {
//            Context itemContext = view.getContext();
//            Log.d("ContextCheck", ""+itemContext.getClass());
//            if (itemContext instanceof MainActivity) {
//                MainActivity mainActivity = (MainActivity) itemContext;
//                mainActivity.changeFromHomeToProductWithProductdata(product);
//            } else {
//                Log.d("ContextCheck", "Not an instance of MainActivity");
//            }
//        });


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

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image_view);
            productNameTextView = itemView.findViewById(R.id.product_name);
            productPriceTextView = itemView.findViewById(R.id.product_price);
            productPastPriceTextView = itemView.findViewById(R.id.product_past_price);
        }
    }
}

