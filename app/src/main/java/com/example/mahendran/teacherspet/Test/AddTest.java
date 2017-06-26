package com.example.mahendran.teacherspet.Test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.StudentDatabase.StudentValues;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AddTest extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference classTeacherCloudEndPoint;
    private DatabaseReference classCloudEndPoint;
    private DatabaseReference studentCloudEndPoint;
    private DatabaseReference tempCloudEndPoint;
    private DatabaseReference tempCloudEndPoint1;
    private DatabaseReference tempCloudEndPoint2;
    public Map<String,String> testMap=new HashMap<String,String>();
    private StudentValues sv;
    private EditText marks;
    private EditText comments;
    String key;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent=getIntent();
        key= intent.getStringExtra("Key");
        test= intent.getStringExtra("TestName");
        setSupportActionBar(toolbar);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email=pref.getString("Email", null);
        String className=pref.getString("class", null);
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        teacherCloudEndPoint = mDatabase.child("Teachers");
        classTeacherCloudEndPoint = teacherCloudEndPoint.child(email);
        classCloudEndPoint = classTeacherCloudEndPoint.child(className);
        studentCloudEndPoint = classCloudEndPoint.child("Students");
        tempCloudEndPoint = studentCloudEndPoint.child(key);
        tempCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                sv=snapshot.getValue(StudentValues.class);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                marks = (EditText) findViewById(R.id.test_marks);
                comments = (EditText) findViewById(R.id.comments);

                sv.map.put(test,(String.valueOf(marks.getText())));
                tempCloudEndPoint.setValue(sv);

            }
        });
    }

}
