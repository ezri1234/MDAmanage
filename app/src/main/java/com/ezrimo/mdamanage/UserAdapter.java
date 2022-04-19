package com.ezrimo.mdamanage;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class UserAdapter extends FirestoreRecyclerAdapter<User, UserAdapter.UserHolder>{


    public UserAdapter(@NonNull FirestoreRecyclerOptions<User> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull User model) {
        holder.tvUserName.setText(model.getFullName());
        holder.tvIsAdmin.setText(String.valueOf((int) model.getIsAdmin()));
        holder.tvEmail.setText(model.getEmail());
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent, false);

        return new UserHolder(v);
    }

    class UserHolder extends RecyclerView.ViewHolder{
        TextView tvUserName, tvEmail, tvIsAdmin;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.fullName);
            tvEmail = itemView.findViewById(R.id.email);
            tvIsAdmin = itemView.findViewById(R.id.isAdmin);
        }
    }

}
