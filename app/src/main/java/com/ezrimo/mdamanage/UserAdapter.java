package com.ezrimo.mdamanage;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserAdapter extends FirestoreRecyclerAdapter<User, UserAdapter.UserHolder>{

    onItemClickListener listener;
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

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }
    //returns UId of this user
    public String getUid(int position){
        return getSnapshots().getSnapshot(position).getId();
    }

    class UserHolder extends RecyclerView.ViewHolder{
        TextView tvUserName, tvEmail, tvIsAdmin;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.fullName);
            tvEmail = itemView.findViewById(R.id.email);
            tvIsAdmin = itemView.findViewById(R.id.isAdmin);
            //When Specific User In RecyclerView Is Clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener!=null){
                        //calls onItemClick (in my case it's defined in ChooseUser)
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });

        }
    }

    //making interface so this method will be more global
    public interface onItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnclickItemListener(onItemClickListener listener){
        this.listener =listener;
    }
}
