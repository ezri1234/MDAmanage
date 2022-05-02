package com.ezrimo.mdamanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ChooseUser extends AppCompatActivity implements UserAdapter.onItemClickListener {
    private FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private CollectionReference userRef = fStore.collection("User");

    private UserAdapter adapter;
    TextView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        Log.d("TAG", userRef.orderBy("isAdmin", Query.Direction.ASCENDING).toString());
        setUpRecView();
    }

    private void setUpRecView() {
        long date = getIntent().getLongExtra("date", 62419651056000L);
        Query query = userRef.orderBy("email", Query.Direction.ASCENDING);//query of users for the recyclerview
        Log.d("TAG", query.toString());
        boolean fromAdmin = getIntent().getBooleanExtra("fromAdmin", false);
        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .build();
        adapter = new UserAdapter(options);
        Log.d("TAG", "adapter");
        /*
         * set up for hte RecyclerView
         */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        //security zone for admin only- only admins have the access to assign
        if(fromAdmin) {
            Toast.makeText(getApplicationContext(), "to assign swipe left :)", Toast.LENGTH_SHORT).show();

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }


                /**
                 * when swiped assigns this user and goes back to Assign
                 * @param viewHolder on wich item it was swiped
                 * @param direction
                 */
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    Intent go = new Intent(getApplicationContext(), Assign.class);
                    go.putExtra("date", date);
                    Log.d("TAG", "chosen uid " + adapter.getUid(viewHolder.getAbsoluteAdapterPosition()));
                    go.putExtra("uid", adapter.getUid(viewHolder.getAbsoluteAdapterPosition()));
                    go.putExtra("chosenUid", true);
                    startActivity(go);
                    finish();
                }
            }).attachToRecyclerView(recyclerView);
        }
        adapter.setOnclickItemListener(new UserAdapter.onItemClickListener() {
            /**
             * when clicked goes to user info
             * @param documentSnapshot
             * @param position not using
             */
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                User user = documentSnapshot.toObject(User.class);
                Intent go = new Intent(ChooseUser.this, UserInfo.class);
                go.putExtra("user", user);
                go.putExtra("uid", documentSnapshot.getId());
                startActivity(go);
            }
        });

        search = findViewById(R.id.search_bar);

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().isEmpty()) {
                    Query query = userRef
                            .orderBy("fullName", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                            .setQuery(query, User.class)
                            .build();
                    adapter.updateOptions(options);
                } else {
                    Query query = userRef.whereGreaterThanOrEqualTo("fullName", editable.toString())
                            .whereLessThanOrEqualTo("fullName", editable.toString()+"\uF7FF")
                            .orderBy("fullName", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                            .setQuery(query, User.class)
                            .build();
                    adapter.updateOptions(options);
                }
            }
        });

    }

    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "meet a IOOBE in RecycleView");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

    }
}