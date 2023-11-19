package com.example.payway.activities_and_fragments.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.payway.Data_Managers.Client;
import com.example.payway.Data_Managers.Firebase;
import com.example.payway.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
public class welcome extends AppCompatActivity {

    EditText usernameInput;
    Button letMeInBtn;
    ProgressBar progressBar;
    String phoneNumber;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        usernameInput = findViewById(R.id.login_username);
        letMeInBtn = findViewById(R.id.login_let_me_in_btn);
        progressBar =findViewById(R.id.login_progress_bar);

        phoneNumber = getIntent().getExtras().getString("phone");
        getUsername();

        letMeInBtn.setOnClickListener((v -> {
            setUsername();
        }));


    }

    void setUsername(){

        String username = usernameInput.getText().toString();
        if(username.isEmpty() || username.length()<3){
            usernameInput.setError("Username length should be at least 3 chars");
            return;
        }
        setInProgress(true);
        if(client!=null){
            client.setUsername(username);
        }else{
            client = new Client(username,phoneNumber, Firebase.currentUserId().toString());
        }

        Firebase.currentUserDetails().set(client).addOnCompleteListener(task -> {
            setInProgress(false);
            if(task.isSuccessful()){
                Intent intent = new Intent(welcome.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity(intent);
            }
        });

    }

    void getUsername(){
        setInProgress(true);
        Firebase.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    client =    task.getResult().toObject(Client.class);
                    if(client!=null){
                        usernameInput.setText(client.getUsername());
                    }
                }
            }
        });
    }

    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            letMeInBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            letMeInBtn.setVisibility(View.VISIBLE);
        }
    }
}