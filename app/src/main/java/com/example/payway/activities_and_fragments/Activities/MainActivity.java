package com.example.payway.activities_and_fragments.Activities;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.payway.R;
import com.example.payway.activities_and_fragments.fragments.Favorit;
import com.example.payway.activities_and_fragments.fragments.Home;
import com.example.payway.activities_and_fragments.fragments.My_cart;
import com.example.payway.activities_and_fragments.fragments.Profile;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity {

    SmoothBottomBar smoothBottomBar;

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
}
