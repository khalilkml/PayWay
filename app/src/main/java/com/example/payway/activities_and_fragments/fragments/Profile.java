package com.example.payway.activities_and_fragments.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.payway.Data_Managers.Firebase;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.Activities.number_login;

public class Profile extends Fragment {

    private Button logoutButton;

    public Profile() {
        // Required empty public constructor
    }

    public static Profile newInstance() {
        return new Profile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logoutButton = view.findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase.logout(); // Logout using Firebase method
                // Redirect to SignInActivity or any other destination after logout
                Intent intent = new Intent(getActivity(), number_login.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
