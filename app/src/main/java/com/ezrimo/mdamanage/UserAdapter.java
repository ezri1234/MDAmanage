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
    /**
     * setting the data on an RecyclerView item
     * @param holder User holder that was created
     * @param position the position of the item that we want to change
     * @param model the Data that comes from FireStore comes as User object
     */
    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull User model) {
        holder.tvUserName.setText(model.getFullName());
        holder.tvIsAdmin.setText(String.valueOf((int) model.getIsAdmin()));
        holder.tvEmail.setText(model.getEmail());
    }

    /**
     * create an item on RecyclerView without the data form FireBase
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent, false);
        return new UserHolder(v);
    }
    //deletes User from database
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    //returns UID of this user
    public String getUid(int position){
        return getSnapshots().getSnapshot(position).getId();
    }

    //User Holder handles specific item
    class UserHolder extends RecyclerView.ViewHolder{
        TextView tvUserName, tvEmail, tvIsAdmin;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.fullName);
            tvEmail = itemView.findViewById(R.id.email);
            tvIsAdmin = itemView.findViewById(R.id.isAdmin);
            //When Specific Item In RecyclerView Is Clicked
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
