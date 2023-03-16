package com.example.trynaroomdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserWindow extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_window);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        Intent okIntent = getIntent();
        String current_user = okIntent.getStringExtra("Login");

        setFragment(new BalanceFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.balance:
                        setFragment(new BalanceFragment());
                        return true;
                    case R.id.history:
                        setFragment(new HistoryFragment());
                        return true;
                }
                return false;
            }
        });
    }
    public void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, fragment, null)
                .commit();
    }
    public String getCurrentUser(){
        Intent okIntent = getIntent();
        String current_user = okIntent.getStringExtra("Login");
        return current_user;
    }
}
