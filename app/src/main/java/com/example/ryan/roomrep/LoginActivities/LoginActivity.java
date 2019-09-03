package com.example.ryan.roomrep.LoginActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryan.roomrep.Classes.Landlord;
import com.example.ryan.roomrep.Classes.Login;
import com.example.ryan.roomrep.MainActivityLandlord;
import com.example.ryan.roomrep.MainActivityTenant;
import com.example.ryan.roomrep.ProfileActivity;
import com.example.ryan.roomrep.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    Button login;
    Button listings;
    TextView signup;
    EditText password;
    EditText userName;

    private ArrayList<String> landlords;
    private ArrayList<String> houses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(onLogin);
        listings = findViewById(R.id.btnViewListings);
        listings.setOnClickListener(onViewListings);
        signup = findViewById(R.id.txtSignUp);
        signup.setOnTouchListener(onSignUp);
        password = findViewById(R.id.edtPassword);
        userName = findViewById(R.id.edtUsername);

        landlords = new ArrayList<>();


    }


    View.OnTouchListener onSignUp = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {



            Toast.makeText(LoginActivity.this, "Sign Up", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, ChooseAccountActivity.class);
            startActivity(intent);
            return false;
        }
    };

    View.OnClickListener onViewListings = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    };


    View.OnClickListener onLogin = new OnClickListener() {
        @Override
        public void onClick(View v) {

            //final Login login = new Login(userName.getText().toString(), password.getText().toString());

            // LoginLandlord(login);
            //LoginTenant(login);
            Intent intent = new Intent(LoginActivity.this, MainActivityTenant.class);
            startActivity(intent);




        }
    };


    private void LoginLandlord(Login l){
        final Login login = l;
        Task<QuerySnapshot> landlordAccountInfo = login.GetLandlordAccountInfo();
        landlordAccountInfo.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        landlords.add(document.get("Email").toString());
                        if (document.get("Password").equals(login.getPassword()) && document.get("Email").equals(login.getUserName())){

                            Intent intent = new Intent(LoginActivity.this, MainActivityLandlord.class);
                            intent.putExtra("LandlordFirstName", document.get("FirstName").toString());
                            intent.putExtra("LandlordLastName", document.get("LastName").toString());
                            intent.putExtra("LandlordEmail", document.get("Email").toString());
                            startActivity(intent);
                            break;
                        }
                    }
                    LoginTenant(login, landlords);

                }
            }
        });
    }

    private void LoginTenant(Login l, ArrayList<String> landlords){
        final Login login = l;
        for (String landlord : landlords){
            Task<QuerySnapshot> result = login.GetTenantAccountInfo(landlord);
            result.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()){
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.get("Password").equals(login.getPassword()) && document.get("Email").equals(login.getUserName())){
                                Intent intent = new Intent(LoginActivity.this, MainActivityTenant.class);

                                startActivity(intent);
                                break;
                            }
                        }
                    }
                }
            });
        }





    }


    private boolean ValidateInputs(){
        boolean valid = true;
        if (userName.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (password.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }
}

