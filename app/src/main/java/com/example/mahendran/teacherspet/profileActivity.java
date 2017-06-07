package com.example.mahendran.teacherspet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mahendran.teacherspet.firebase.StudentValues;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class profileActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private DatabaseReference studentCloudEndPoint;
    StudentValues note=new StudentValues();
    String g="Initial";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent=getIntent();
        String s=intent.getStringExtra("student");
        String id = intent.getStringExtra("id");
        EditText name1=(EditText) findViewById(R.id.name);
        EditText ph=(EditText) findViewById(R.id.number);
        EditText parentph=(EditText) findViewById(R.id.parentnumber);
        EditText address=(EditText) findViewById(R.id.address);
        EditText cgpa=(EditText) findViewById(R.id.name);
        EditText rank=(EditText) findViewById(R.id.name);
        if(s!=null)
        {
            ph.setFocusableInTouchMode(true);
            parentph.setFocusableInTouchMode(true);
            address.setFocusableInTouchMode(true);
        }
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        studentCloudEndPoint = mDatabase.child("Students");

       /* studentCloudEndPoint.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                note = dataSnapshot.getValue(StudentValues.class);
                g=note.studentName;
                Log.v("noted", note.studentName);
            }

            @Override
            public void onCan celled(DatabaseError databaseError) {
                Log.v("bbb", databaseError.getMessage());
            }
        });*/




        name1.setText(id);

    }




}
