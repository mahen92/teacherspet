package com.example.mahendran.teacherspet.StudentDatabase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.customAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class studentDatabase extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference studentCloudEndPoint;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference teacherClassCloudEndPoint;
    private DatabaseReference referenceClassCloudEndPoint;
    private FireBaseAdapter mFireAdapter;
    private LinearLayoutManager linearLayoutManager;
    customAdapter cs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email=pref.getString("Email", null);
        String className=pref.getString("class", null);
        mDatabase =  FirebaseDatabase.getInstance().getReference();

        mDatabase =  FirebaseDatabase.getInstance().getReference();
        referenceClassCloudEndPoint = mDatabase.child("Teachers");
        teacherCloudEndPoint = referenceClassCloudEndPoint.child(email);
        teacherClassCloudEndPoint = teacherCloudEndPoint.child(className);
        studentCloudEndPoint = teacherClassCloudEndPoint.child("Students");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), addStudent.class);
                startActivity(intent);
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        mFireAdapter = new FireBaseAdapter(StudentValues.class, R.layout.student_list_content, FireBaseAdapterHolder.class, studentCloudEndPoint, this);
        RecyclerView gridView=(RecyclerView) findViewById(R.id.list_view);
        gridView.setLayoutManager(linearLayoutManager);
        gridView.setAdapter(mFireAdapter);
    }
}

