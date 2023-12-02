package com.example.payway.activities_and_fragments.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.payway.Data_Managers.Client;
import com.example.payway.Data_Managers.Firebase;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.Activities.number_login;

public class Profile extends Fragment {

    private Button logoutButton;
    private Button gotorepo;

    Client client;

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
        gotorepo = view.findViewById(R.id.gethubrepo);
        ImageView developer2 = view.findViewById(R.id.developer);

        gotorepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace "username/repo" with your GitHub username and repository name
                String githubUrl = "https://github.com/khalilkml/PayWay/";

                // Create an intent with ACTION_VIEW and the GitHub URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
                Toast.makeText(requireContext(), ""+Uri.parse(githubUrl), Toast.LENGTH_SHORT).show();

                // Check if there's an app to handle this intent before starting it
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // Handle if no app can handle the intent (optional)
                    Toast.makeText(requireContext(), "No app found to handle this action", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
