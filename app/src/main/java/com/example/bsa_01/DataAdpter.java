package com.example.bsa_01;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataAdpter extends RecyclerView.Adapter<DataAdpter.MyViewHolder> {

    Context context;
    ArrayList<DataClass> list;

    public DataAdpter(Context context, ArrayList<DataClass> list) {
        this.context = context;
        this.list = list;
    }

    @androidx.annotation.NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
      //View v1 = LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull MyViewHolder holder, int position) {
        DataClass dc=list.get(position);

        holder.title.setText((dc.getBookTitle()));
        holder.isbn.setText((dc.getBookIsbn()));
        holder.author.setText((dc.getBookAuthor()));
        holder.price.setText((dc.getBookprice()));
        holder.quantity.setText((dc.getBookquanitiy()));
        holder.description.setText((dc.getDescription()));
        Glide.with(context).load(dc.getBookImage()).into(holder.image);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.isbn.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1500)
                        .create();
//                dialogPlus.show();

                View view =dialogPlus.getHolderView();
                EditText price=view.findViewById(R.id.txtprice);
                EditText quantity=view.findViewById(R.id.txtquantity);
                EditText description=view.findViewById(R.id.txtdescription);
                Button btnUpdate=view.findViewById(R.id.btnupdate);

                price.setText(dc.getBookprice());
                quantity.setText(dc.getBookquanitiy());
                description.setText(dc.getDescription());
                dialogPlus.show();
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String updatedPrice = price.getText().toString();
                        String updatedQuantity = quantity.getText().toString();
                        String updatedDescription = description.getText().toString();

                        // Validate input data
                        if (TextUtils.isEmpty(updatedPrice) || TextUtils.isEmpty(updatedQuantity) || TextUtils.isEmpty(updatedDescription)) {
                            Toast.makeText(holder.title.getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        Map<String, Object> map = new HashMap<>();
                        map.put("bookprice", updatedPrice);
                        map.put("bookquanitiy", updatedQuantity);
                        map.put("description", updatedDescription);

                        FirebaseDatabase.getInstance().getReference().child("Books").child(dc.getBookCategories())
                                .child(dc.getKey())
                                .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.title.getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.title.getContext(), "Error While Updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });


            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.isbn.getContext());
                builder.setTitle("Are You Sure");
                builder.setMessage("Deleted data cant be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Books").child(dc.getBookCategories()).child(dc.getKey());
                        databaseReference.removeValue();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.isbn.getContext(),"Cancelled",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView title,isbn,author,price,quantity,description;
        ImageView image;

        Button btnEdit,btnDelete;
        public  MyViewHolder(@org.checkerframework.checker.nullness.qual.NonNull View itemView){
            super(itemView);
            isbn=itemView.findViewById(R.id.recisbn);
            title=itemView.findViewById(R.id.recTitle);
            author=itemView.findViewById(R.id.recauthor);
            price=itemView.findViewById(R.id.price);
            quantity=itemView.findViewById(R.id.quantity);
            description=itemView.findViewById(R.id.description);
            image=itemView.findViewById(R.id.recImage);

            btnEdit=itemView.findViewById(R.id.btnedit);
            btnDelete=itemView.findViewById(R.id.btndelete);


        }
    }
}

