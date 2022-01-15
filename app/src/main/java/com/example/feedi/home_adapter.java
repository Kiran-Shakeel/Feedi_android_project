package com.example.feedi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class home_adapter extends ArrayAdapter<request_info> {
    Context mcontext;
    int mresourse;


    public home_adapter(@NonNull Context context, int resource, ArrayList<request_info> list) {
        super(context, resource, list);
        mcontext = context;
        mresourse = resource;


    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // View view= LayoutInflater.from(context).inflate(R.layout.leftover_list_row,parent,false);

        String ins = getItem(position).getIns();
        String id = getItem(position).getUser_key();


        request_info req = new request_info(id, ins);
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        convertView = layoutInflater.inflate(mresourse, parent, false);


        ImageView imageView = convertView.findViewById(R.id.image);
        TextView get_name = convertView.findViewById(R.id.first_name);
        TextView get_last = convertView.findViewById(R.id.last);
        TextView get_ins = convertView.findViewById(R.id.info);
        TextView get_status = convertView.findViewById(R.id.status);
        get_ins.setText(ins);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("profile");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String first = snapshot.child("first_name").getValue(String.class);
                    String last = snapshot.child("last_name").getValue(String.class);
                    String pic = snapshot.child("profile_pic").getValue(String.class);
                    String status = snapshot.child("status").getValue(String.class);

                    get_name.setText(first);
                    get_last.setText(last);
                    get_status.setText(status);
                    Picasso.get().load(pic).into(imageView);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        return convertView;

    }

}
