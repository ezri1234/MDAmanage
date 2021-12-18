package com.ezrimo.mdamanage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public RecycleAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder holder, int position) {

        User user = userArrayList.get(position);

        holder.fullName.setText(user.fullName);
        holder.userEmail.setText(user.userEmail);
        holder.isAdmin.setText(String.valueOf(user.isAdmin));

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fullName, userEmail, isAdmin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName);
            userEmail = itemView.findViewById(R.id.userEmail);
            isAdmin = itemView.findViewById(R.id.isAdmin);
        }
    }

}
