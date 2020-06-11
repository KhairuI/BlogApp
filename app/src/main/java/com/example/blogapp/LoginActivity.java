package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.text.TextUtilsCompat;

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

public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText loginEmailEditText;
    private AppCompatEditText loginPasswordEditText;
    private AppCompatButton loginButton,needAccountButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findSection();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String loginEmail= loginEmailEditText.getEditableText().toString();
                String loginPassword= loginPasswordEditText.getEditableText().toString();

                if(!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPassword)){
                    //show spots dialogue here.....
                    final AlertDialog dialogue= new SpotsDialog.Builder().setContext(LoginActivity.this).setTheme(R.style.Custom).setCancelable(true).build();
                    dialogue.show();

                    mAuth.signInWithEmailAndPassword(loginEmail,loginPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            dialogue.dismiss();
                            goToMainActivity();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialogue.dismiss();
                            Toast.makeText(LoginActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                else {
                    Toast.makeText(LoginActivity.this, "Please Enter Email & Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //it check is the user have authentication in fire base. if have then sent into the MainActivity
        FirebaseUser currentUser= mAuth.getCurrentUser();
        if(currentUser != null){
            goToMainActivity();
        }

    }

    private void goToMainActivity() {
        Intent intent= new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void findSection() {
        mAuth= FirebaseAuth.getInstance();
        loginEmailEditText= findViewById(R.id.loginEmailId);
        loginPasswordEditText= findViewById(R.id.loginPasswordId);
        loginButton= findViewById(R.id.loginButtonId);
        needAccountButton= findViewById(R.id.newAccountButtonId);
    }


}