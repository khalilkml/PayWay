package com.example.payway.activities_and_fragments.Activities;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.payway.Data_Managers.MyAdapterListener;
import com.example.payway.Data_Managers.Product;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.fragments.Favorit;
import com.example.payway.activities_and_fragments.fragments.Home;
import com.example.payway.activities_and_fragments.fragments.My_cart;
import com.example.payway.activities_and_fragments.fragments.Product_fragment;
import com.example.payway.activities_and_fragments.fragments.Profile;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity implements MyAdapterListener {

    SmoothBottomBar smoothBottomBar;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.orange));
        }

        smoothBottomBar=findViewById(R.id.bottomBar);

        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout , new Home());
        fragmentTransaction.commit();
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        FragmentManager fragmentManager =getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout , new Home());
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        fragmentManager =getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout , new Favorit());
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        fragmentManager =getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout , new My_cart());
                        fragmentTransaction.commit();
                        break;
                    case 3:
                        fragmentManager =getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout , new Profile());
                        fragmentTransaction.commit();
                        break;
                    default:
                        fragmentManager =getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayout , new Home());
                        fragmentTransaction.commit();
                        break;
                }
                return false;
            }
        });

    }


    public void changeFromHomeToProductWithProductdata(Product product) {
            // Create a new Bundle to pass data to the fragment
            Bundle bundle = new Bundle();
            bundle.putString("productId",product.getProductId());
            // Add the task data as arguments to the Bundle
            bundle.putString("productName", product.getProductName());
            bundle.putString("productDescription", product.getProductDescription());
            bundle.putString("productPrice", product.getProductPrice());
        // Add the taskDate and isDone properties to the Bundle
            bundle.putString("productPastPrice", product.getProductPastPrice()); // Assuming you have a getTaskDate() method in your Task class
            bundle.putBoolean("isFavorite", product.isFavorite()); // Assuming you have an isDone() method in your Task class

            // Add the additional string data to the Bundle
            bundle.putString("imageUrl", product.getImageUrl());
            // Inside your MainActivity class
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Create a new instance of the ProductFragment
            Product_fragment newProductFragment = new Product_fragment();

            // Set the bundle to the new fragment
            newProductFragment.setArguments(bundle);

            // Replace the fragment in the FrameLayout with the new ProductFragment
        transaction.replace(R.id.framelayout, newProductFragment);
            transaction.addToBackStack(null); // Optional: Add transaction to back stack
            transaction.commit();
    }

    @Override
    public void onPlaceClick(Product product) {
        // This method already handles fragment replacement, no need to call the method again
        // Just use it directly to navigate to the Product_fragment
        changeFromHomeToProductWithProductdata(product);
    }

    public void hideBottomAppBar() {
        // Access your bottom app bar view
        SmoothBottomBar bottomAppBar = findViewById(R.id.bottomBar);

        // Hide the bottom app bar
        bottomAppBar.setVisibility(View.GONE);
    }

    public void showBottomAppBar() {
        // Access your bottom app bar view
        SmoothBottomBar bottomAppBar = findViewById(R.id.bottomBar);

        // Hide the bottom app bar
        bottomAppBar.setVisibility(View.VISIBLE);
    }
}
