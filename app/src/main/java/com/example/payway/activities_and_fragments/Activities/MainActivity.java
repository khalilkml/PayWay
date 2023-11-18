package com.example.payway.activities_and_fragments.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

        smoothBottomBar=findViewById(R.id.bottomBar);
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                if(i==0){
                    FragmentManager fragmentManager =getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout , new Home());
                    fragmentTransaction.commit();
                }
                if(i==1){
                    FragmentManager fragmentManager =getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout , new Favorit());
                    fragmentTransaction.commit();
                }
                if(i==2){
                    FragmentManager fragmentManager =getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout , new My_cart());
                    fragmentTransaction.commit();
                }
                if(i==3){
                    FragmentManager fragmentManager =getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framelayout , new Profile());
                    fragmentTransaction.commit();
                }
                return false;
            }
        });
    }
}
