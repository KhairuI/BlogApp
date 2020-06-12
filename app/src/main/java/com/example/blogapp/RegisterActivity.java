package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    private AppCompatEditText registerEmailEditText,registerPasswordEditText,registerConfirmPasswordEditText;
    private AppCompatButton registerButton,haveAccountButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findSection();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String registerEmail= registerEmailEditText.getEditableText().toString();
                String registerPassword= registerPasswordEditText.getEditableText().toString();
                String registerConfirmPassword= registerConfirmPasswordEditText.getEditableText().toString();

                if(!TextUtils.isEmpty(registerEmail) && !TextUtils.isEmpty(registerPassword) && !TextUtils.isEmpty(registerConfirmPassword)){

                    if(registerPassword.equals(registerConfirmPassword)){

                        //show spots dialogue here.....
                        final AlertDialog dialogue= new SpotsDialog.Builder().setContext(RegisterActivity.this).setTheme(R.style.Custom).setCancelable(true).build();
                        dialogue.show();

                        //create new user
                        mAuth.createUserWithEmailAndPassword(registerEmail,registerPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                dialogue.dismiss();
                                goToMainActivity();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogue.dismiss();
                                Toast.makeText(RegisterActivity.this, "Error: "+e, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Password don't match", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Please Enter Email & Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        haveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();

        //checked the user is login or not
        FirebaseUser currentUser= mAuth.getCurrentUser();
        if(currentUser != null){
            goToMainActivity();
        }
    }

    private void goToMainActivity() {
        Intent intent= new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void findSection() {
        mAuth= FirebaseAuth.getInstance();
        registerEmailEditText= findViewById(R.id.registerEmailId);
        registerPasswordEditText= findViewById(R.id.registerPasswordId);
        registerConfirmPasswordEditText= findViewById(R.id.registerConfirmPasswordId);
        registerButton= findViewById(R.id.registerAccountButtonId);
        haveAccountButton= findViewById(R.id.haveAccountButtonId);
    }
}