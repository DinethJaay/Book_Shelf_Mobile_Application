package com.example.bsa_01;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class userAdapter extends  RecyclerView.Adapter<DataAdpter.MyViewHolder> {
    Context context;
    ArrayList<DataClass> list;

    public userAdapter(Context context, ArrayList<DataClass> list) {
        this.context = context;
        this.list = list;
    }

    @androidx.annotation.NonNull
    @Override
    public DataAdpter.MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item2, parent, false);
        return new DataAdpter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull DataAdpter.MyViewHolder holder, int position) {
        DataClass dc = list.get(position);

        holder.title.setText((dc.getBookTitle()));
        holder.isbn.setText((dc.getBookIsbn()));
        holder.author.setText(dc.getBookAuthor());
        holder.price.setText(dc.getBookprice());
        holder.quantity.setText(dc.getBookquanitiy());
        holder.description.setText(dc.getDescription());
        Glide.with(context).load(dc.getBookImage()).into(holder.image);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, isbn, author, price, quantity, description;
        ImageView image;

        Button btnbuy, btncart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            isbn = itemView.findViewById(R.id.recisbn);
            title = itemView.findViewById(R.id.recTitle);
            author = itemView.findViewById(R.id.recauthor);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.recImage);

            btnbuy = itemView.findViewById(R.id.btnbuynow);
            btncart = itemView.findViewById(R.id.btncart);

        }
    }

}