package com.example.bsa_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bsa_01.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    DBHelper_SQLliteDBHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DBHelper_SQLliteDBHelper(this);

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = binding.LEmail.getText().toString();
                String Password = binding.LPassword.getText().toString();

                if(Email.equals("") || Password.equals(""))
                {
                    Toast.makeText(Login.this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(Email.equals("SuperAdmin") || Password.equals("SuperAdmin"))
                    {
                        Intent intentSuperAdmin = new Intent(getApplicationContext(), Super_Admin_Home.class);
                        startActivity(intentSuperAdmin);
                    }
                    else
                    {
                        Boolean CheckCredentialsResult = databaseHelper.CheckUserCredentials(Email,Password);

                        if(CheckCredentialsResult == true)
                        {
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            String UserRoleResult = databaseHelper.checkUserRole(Email);
                            if(UserRoleResult.equals("Admin"))
                            {
                                Intent intentAdmin = new Intent(getApplicationContext(), Admin_Home.class);
                                startActivity(intentAdmin);
                            }
                            else if (UserRoleResult.equals("Customer"))
                            {
                                Intent intentCustomer = new Intent(getApplicationContext(), Customer_Home.class);
                                startActivity(intentCustomer);
                            }
                            else if (UserRoleResult.equals("Courier"))
                            {
                                Intent intentCourier = new Intent(getApplicationContext(), Courier_Home.class);
                                startActivity(intentCourier);
                            }
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        binding.RegisterRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegister = new Intent(getApplicationContext(), Register.class);
                startActivity(intentRegister);
            }
        });
    }
}