package com.quintus.labs.firebasechat.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.quintus.labs.firebasechat.R;
import com.quintus.labs.firebasechat.adapter.RecyclerviewAdapter;
import com.quintus.labs.firebasechat.model.User;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity==>";
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<User> list;
    RecyclerView recyclerview;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        userId = firebaseAuth.getUid();
        recyclerview = findViewById(R.id.rview);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        progressDialog = new ProgressDialog(AdminActivity.this);
        progressDialog.setMessage("Fetching Data....");
        progressDialog.show();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                // StringBuffer stringbuffer = new StringBuffer();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    User userdetails = dataSnapshot1.getValue(User.class);
                    User user1 = new User();
                    String user_Id = dataSnapshot1.getKey();
                    String name = userdetails.getName();
                    String email = userdetails.getEmail();
                    String profileImage = userdetails.getProfileImage();

                    if (!userId.equalsIgnoreCase(user_Id)) {
                        user1.setuId(user_Id);
                        user1.setName(name);
                        user1.setEmail(email);
                        user1.setProfileImage(profileImage);
                        list.add(user1);
                    }

                    // Toast.makeText(MainActivity.this,""+name,Toast.LENGTH_LONG).show();

                }

                RecyclerviewAdapter recycler = new RecyclerviewAdapter(AdminActivity.this, list);
                RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(AdminActivity.this);
                recyclerview.setLayoutManager(layoutmanager);
                recyclerview.setItemAnimator(new DefaultItemAnimator());
                recyclerview.setAdapter(recycler);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}
