package com.example.vasu.projectdrag;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Donor extends AppCompatActivity {

     private EditText msearchbar;
     //ImageButton msearchbutton;
     private RecyclerView recyclerView;
     private ArrayList<String> ssonameList;
     private ArrayList<String> addressList;
     private ArrayList<String> profilepicList;

     private SearchAdapter searchAdapter;
     DatabaseReference myref;
     private StorageReference storageReference;

    Button pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);

        pro=(Button)findViewById(R.id.id_profile);

        msearchbar=(EditText)findViewById(R.id.id_searchbar);
        recyclerView=(RecyclerView)findViewById(R.id.id_recyclerview);

        myref=FirebaseDatabase.getInstance().getReference("SSO");
        storageReference= FirebaseStorage.getInstance().getReference();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


        ssonameList=new ArrayList<>();
        addressList=new ArrayList<>();
        profilepicList=new ArrayList<>();

        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DProfileEdit.class));
            }
        });

        msearchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    if(!s.toString().isEmpty())
                    {
                        setAdapter(s.toString());
                    }
                    else
                    {
                        ssonameList.clear();
                        addressList.clear();
                        profilepicList.clear();
                        recyclerView.removeAllViews();
                    }
            }
        });


      /*  msearchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUserSearch();
            }
        });*/
    }


    private void setAdapter(final String searchedstring)
    {
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ssonameList.clear();
                addressList.clear();
                profilepicList.clear();
                recyclerView.removeAllViews();

                int counter=0;

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    String uid=snapshot.getKey();
                    String ssoname=snapshot.child("ssoname").getValue(String.class);
                    String email=snapshot.child("email").getValue(String.class);
                    String address=snapshot.child("address").getValue(String.class);
                    String profilepic=uid;

                    Log.d("vasuu",ssoname);

                    if(ssoname.toLowerCase().contains(searchedstring.toLowerCase()))
                    {
                        ssonameList.add(ssoname);
                        addressList.add(address);
                        profilepicList.add(uid);
                        counter++;
                    }
                    else if(email.toLowerCase().contains(searchedstring.toLowerCase()))
                    {
                        ssonameList.add(ssoname);
                        addressList.add(address);
                        profilepicList.add(uid);
                        counter++;
                    }

                    if(counter==20)
                        break;
                }

                searchAdapter=new SearchAdapter(getApplicationContext(),ssonameList,addressList,profilepicList);
                recyclerView.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
