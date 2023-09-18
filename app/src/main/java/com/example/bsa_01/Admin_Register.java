package com.example.bsa_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bsa_01.databinding.ActivityAdminRegisterBinding;

public class Admin_Register extends AppCompatActivity {
    ActivityAdminRegisterBinding binding;
    DBHelper_SQLliteDBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Retrieve the "Name" value from the Intent
        String Email = getIntent().getStringExtra("Email");

        databaseHelper = new DBHelper_SQLliteDBHelper(this);
        binding.btnARSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmployeeID = binding.AREmployeeID.getText().toString();
                String EmployeeRole = binding.ARERoleSpinner.getSelectedItem().toString();
                String city = binding.ARCitySpinner.getSelectedItem().toString();

                if( EmployeeID.equals("") || EmployeeRole.equals("") || city.equals("") )
                {
                    Toast.makeText(Admin_Register.this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean InsertAdminResult = databaseHelper.insertAdminData(Email,EmployeeID,EmployeeRole,city);
                    if(InsertAdminResult == true)
                    {

                        Toast.makeText(Admin_Register.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Admin_Register.this, "Please try again later", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(Admin_Register.this, Login.class);
                    startActivity(intent);
                }

            }
        });


    }
}