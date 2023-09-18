package com.example.bsa_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bsa_01.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {

    ActivityRegisterBinding binding;
    DBHelper_SQLliteDBHelper databaseHelper;
    String Name,userRole,Email,Password,CPassword;
    String Error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DBHelper_SQLliteDBHelper(this);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = binding.SName.getText().toString();
                userRole = binding.roleSpinner.getSelectedItem().toString();
                Email = binding.SEmail.getText().toString();
                Password = binding.SPassword.getText().toString();
                CPassword = binding.SCPassword.getText().toString();

                if( Name.equals("") || userRole.equals("") || Email.equals("") || Password.equals("") || CPassword.equals(""))
                {
                    Toast.makeText(Register.this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(Password.equals(CPassword))
                    {
                        Boolean checkEmailResult = databaseHelper.checkUserEmail(Email);
                        if(checkEmailResult == false)
                        {
                            Boolean InsertResult = databaseHelper.insertUserData(Email,Name,Password,userRole);
                            if(InsertResult == true)
                            {
                                Toast.makeText(Register.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
                                // Create an Intent to start the next activity
                                Intent intent;
                                if(userRole.equals("Admin"))
                                {
                                    intent = new Intent(Register.this, Admin_Register.class);
                                    // Pass the "Email" value as an extra to the next activity
                                    intent.putExtra("Email", Email);
                                }
                                else if (userRole.equals("Customer"))
                                {
                                    intent = new Intent(Register.this, Login.class);
                                }
                                else if (userRole.equals("Courier"))
                                {
                                    intent = new Intent(Register.this, Courier_Register.class);
                                    intent.putExtra("Email", Email);
                                }
                                else
                                {
                                    intent = new Intent(Register.this, Login.class);
                                    // Handle the case when the role is not recognized
                                    //return;
                                }
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Register.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Register.this, "User Already Exists.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Register.this, "Invalid Password.Please Enter Correct Password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            private boolean validateInput() {
                boolean isValid = true;


                if (Name.isEmpty())
                {
                    Error = "Name is required";
                    isValid = false;
                }

                if (Email.isEmpty())
                {
                    Error="Email is required";
                    isValid = false;
                }
                else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    Error="Invalid email address";
                    isValid = false;
                }

                if (Password.isEmpty()) {
                    Error="Password is required";
                    isValid = false;
                }
                else if (Password.length() < 8)
                {
                    Error="Password must be at least 6 characters long";
                    isValid = false;
                }

                if (CPassword.isEmpty()) {
                    Error="Confirm Password is required";
                    isValid = false;
                }
                else if (!CPassword.equals(Password))
                {
                    Error="Passwords do not match";
                    isValid = false;
                }

                return isValid;
            }
        });

        binding.loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
    }


}