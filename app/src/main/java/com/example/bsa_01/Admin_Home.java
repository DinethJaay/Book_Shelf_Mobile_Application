package com.example.bsa_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bsa_01.databinding.ActivityAdminHomeBinding;

public class Admin_Home extends AppCompatActivity {

    ActivityAdminHomeBinding binding;
    Button logut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        logut=findViewById(R.id.btnlogout);
//addbook
        binding.addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Admin_addBook.class);
                startActivity(intent);
                finish();
            }
        });

//Categories
        binding.MathsBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Mathsbookview.class);
                startActivity(intent);
                finish();
            }
        });
//orders
        binding.ScienceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sciencebookview.class);
                startActivity(intent);
                finish();
            }
        });
////users
        binding.HistoryBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Historybookview.class);
                startActivity(intent);
                finish();
            }
        });
        binding.TecnicalBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Technicalbooksview.class);
                startActivity(intent);
                finish();
            }
        });
        binding.StoryBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Storybookview.class);
                startActivity(intent);
                finish();
            }
        });
        binding.LanguageBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Languagebookview.class);
                startActivity(intent);
                finish();
            }
        });
        logut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Home.this, Login.class);
                startActivity(intent);
            }
        });

    }
}

