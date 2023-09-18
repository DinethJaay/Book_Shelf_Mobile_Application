package com.example.bsa_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bsa_01.databinding.ActivityCourierRegisterBinding;

public class Courier_Register extends AppCompatActivity {

    ActivityCourierRegisterBinding binding;
    DBHelper_SQLliteDBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourierRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Retrieve the "Name" value from the Intent
        String Email = getIntent().getStringExtra("Email");

        databaseHelper = new DBHelper_SQLliteDBHelper(this);

        binding.btnCRSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmployeeID = binding.CREmployeeID.getText().toString();
                String EmployeeRole = binding.CRERoleSpinner.getSelectedItem().toString();
                String city = binding.CRCitySpinner.getSelectedItem().toString();

                if( EmployeeID.equals("") || EmployeeRole.equals("") || city.equals("") )
                {
                    Toast.makeText(Courier_Register.this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean InsertCourierResult = databaseHelper.insertCourierData(Email,EmployeeID,EmployeeRole,city);
                    if(InsertCourierResult == true)
                    {

                        Toast.makeText(Courier_Register.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Courier_Register.this, "Please try again later", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(Courier_Register.this, Login.class);
                    startActivity(intent);
                }

            }
        });
    }
}