package com.example.bsa_01;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Admin_addBook extends AppCompatActivity {

    ImageView uploadImages;
    Button addbook;
    EditText bookTitle, bookAuthor, bookisbn, bookprice, bookquantity, bookdescription;
    Spinner bookCategorie;
    String imgeURL;
    Uri uri;
    TextView Backtodashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_book);

        uploadImages = findViewById(R.id.uploadimage);
        bookCategorie = findViewById(R.id.rolecategories);
        bookTitle = findViewById(R.id.booktitle);
        bookAuthor = findViewById(R.id.bookauthor);
        bookisbn = findViewById(R.id.bookisbn);
        bookprice = findViewById(R.id.bookprice);
        bookquantity = findViewById(R.id.bookquantity);
        bookdescription = findViewById(R.id.bookdescription);
        addbook = findViewById(R.id.btnaddbook);
        Backtodashboard = findViewById(R.id.backtodashboard);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImages.setImageURI(uri);
                        } else {
                            Toast.makeText(Admin_addBook.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        Backtodashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_addBook.this, Admin_Home.class);
                startActivity(intent);
            }
        });
    }

    public void saveData() {
        if (uri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedCategory = bookCategorie.getSelectedItem().toString();
        String bookIsbn = bookisbn.getText().toString();
        String bookTitleText = bookTitle.getText().toString();
        String bookAuthorText = bookAuthor.getText().toString();
        String bookPriceText = bookprice.getText().toString();
        String bookQuantityText = bookquantity.getText().toString();
        String bookDescriptionText = bookdescription.getText().toString();

        if (TextUtils.isEmpty(bookIsbn) || TextUtils.isEmpty(bookTitleText) ||
                TextUtils.isEmpty(bookAuthorText) || TextUtils.isEmpty(bookPriceText) ||
                TextUtils.isEmpty(bookQuantityText) || TextUtils.isEmpty(bookDescriptionText)) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate ISBN format (you can customize this regex)
        if (!isValidISBN(bookIsbn)) {
            Toast.makeText(this, "Invalid ISBN format Please Enter 13 Digit ISBN", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate price and quantity as numbers
        try {
            double price = Double.parseDouble(bookPriceText);
            int quantity = Integer.parseInt(bookQuantityText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Price and quantity must be valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Book Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_addBook.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imgeURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    private boolean isValidISBN(String isbn) {
        // Remove any hyphens or spaces
        isbn = isbn.replaceAll("[\\s-]+", "");

        if (isbn.length() != 13) {
            return false; // ISBN-13 should be exactly 13 characters long
        }

        try {
            Long.parseLong(isbn); // Check if it's a valid number
        } catch (NumberFormatException e) {
            return false; // Not a valid number
        }

        return true;
    }


    public void uploadData() {
        String selectedCategory = bookCategorie.getSelectedItem().toString();
        String bookIsbn = bookisbn.getText().toString();
        String bookTitleText = bookTitle.getText().toString();
        String bookAuthorText = bookAuthor.getText().toString();
        String bookPriceText = bookprice.getText().toString();
        String bookQuantityText = bookquantity.getText().toString();
        String bookDescriptionText = bookdescription.getText().toString();

        DataClass dataClass = new DataClass(bookIsbn, bookTitleText, selectedCategory, bookAuthorText, bookPriceText, bookQuantityText, bookDescriptionText, imgeURL);

        String databasePath = "Books/" + selectedCategory + "/" + bookIsbn;

        FirebaseDatabase.getInstance().getReference(databasePath)
                .setValue(dataClass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Admin_addBook.this, "Saved", Toast.LENGTH_SHORT).show();
                            // finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Admin_addBook.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
