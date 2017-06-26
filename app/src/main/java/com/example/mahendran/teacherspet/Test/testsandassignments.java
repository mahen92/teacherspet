package com.example.mahendran.teacherspet.Test;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.mahendran.teacherspet.ClassesAndTeachers.ClassValues;


import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.StudentDatabase.FireBaseAdapter;
import com.example.mahendran.teacherspet.StudentDatabase.FireBaseAdapterHolder;
import com.example.mahendran.teacherspet.StudentDatabase.StudentValues;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class testsandassignments extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    EditText testName;
    Button classAdd;
    LinearLayout linearLayout;
    private DatabaseReference mDatabase;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference classTeacherCloudEndPoint;
    private DatabaseReference classCloudEndPoint;
    private DatabaseReference testCloudEndPoint;
    private LinearLayoutManager linearLayoutManager;
    private testsAdapter testAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testsandassignments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout= (LinearLayout) findViewById(R.id.add_test_layout);
        testName=(EditText)findViewById(R.id.add_test);
        classAdd=(Button)findViewById(R.id.add_test_button);
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email=pref.getString("Email", null);
        String className=pref.getString("class", null);
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        teacherCloudEndPoint = mDatabase.child("Teachers");
        classTeacherCloudEndPoint = teacherCloudEndPoint.child(email);
        classCloudEndPoint = classTeacherCloudEndPoint.child(className);
        testCloudEndPoint = classCloudEndPoint.child("Tests");
        classAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestValues cv= new TestValues();
                String name=(String.valueOf(testName.getText()));
                cv.testName=name;
                String key = testCloudEndPoint.push().getKey();
                testCloudEndPoint.child(key).setValue(cv);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        testAdapter = new testsAdapter(TestValues.class, R.layout.testassignmentdesign, TestValueAdapterHolder.class, testCloudEndPoint, this);
        RecyclerView gridView=(RecyclerView) findViewById(R.id.list_test_view);
        gridView.setLayoutManager(linearLayoutManager);
        gridView.setAdapter(testAdapter);
    }

}
