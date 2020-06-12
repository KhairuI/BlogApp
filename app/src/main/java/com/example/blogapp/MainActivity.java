package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findSection();


    }

    private void findSection() {
        toolbar= findViewById(R.id.mainActivityToolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Blog App");
        mAuth= FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //starting the app it check if the user is login or not. if not then sent the login Activity
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null){
           goToLoginActivity();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logoutId:
                logout();
                return true;
            case R.id.settingId:
                Toast.makeText(this, "Account Setting", Toast.LENGTH_SHORT).show();
                return true;
                
            default:
                return false;
        }
  
    }

    private void logout() {

        mAuth.signOut();
        goToLoginActivity();
        
    }
    private void goToLoginActivity() {
        Intent intent= new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
