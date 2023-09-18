package com.example.bsa_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bsa_01.databinding.ActivityCustomerHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Customer_Home extends AppCompatActivity {

    ActivityCustomerHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Replace the fragment with the HomeFragment initially
        replaceFragment(new HomeFragment());

        // Set a listener for the BottomNavigationView
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    replaceFragment(new HomeFragment());
                } else if (item.getItemId() == R.id.shorts) {
                    replaceFragment(new ShortsFragment());
                } else if (item.getItemId() == R.id.subscriptions) {
                    replaceFragment(new SubscriptionFragment());
                } else if (item.getItemId() == R.id.library) {
                    replaceFragment(new LibraryFragment());
                }
                return true;
            }
        });

        binding.mathsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Customermathsview.class);
                startActivity(intent);
                finish();
            }
        });
        binding.scienceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Customerscienceview.class);
                startActivity(intent);
                finish();
            }
        });
        binding.historyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Customerhistorybooks.class);
                startActivity(intent);
                finish();
            }
        });
        binding.techCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Customertechniclebooks.class);
                startActivity(intent);
                finish();
            }
        });
        binding.storyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Customerstorybooks.class);
                startActivity(intent);
                finish();
            }
        });
        binding.languageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Customerlanguageview.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // Method to replace fragments
    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}

