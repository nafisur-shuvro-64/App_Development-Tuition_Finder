package com.example.tuitionmanager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class AdapterClass extends FirebaseRecyclerAdapter<HelperClass2,AdapterClass.myviewholder>
{

    public AdapterClass(@NonNull FirebaseRecyclerOptions<HelperClass2> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull HelperClass2 model) {
        holder.username.setText(model.getUsername());
        holder.subject.setText(model.getSubject());
        holder.district.setText(model.getDistrict());
        holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(view.getContext(),TutorDetails.class);
                intent.putExtra("username",model.getUsername());
                intent.putExtra("subject",model.getSubject());
                intent.putExtra("district",model.getDistrict());
                intent.putExtra("email",model.getEmail());
                intent.putExtra("phone",model.getPhone());
                view.getContext().startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView username,district,subject,email,phone;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            username=(TextView)itemView.findViewById(R.id.username);
            district=(TextView)itemView.findViewById(R.id.district);
            subject=(TextView)itemView.findViewById(R.id.subject);
            email=(TextView)itemView.findViewById(R.id.email);
            phone=(TextView)itemView.findViewById(R.id.phn);
        }
    }

}
