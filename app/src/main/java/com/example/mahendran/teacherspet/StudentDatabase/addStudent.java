package com.example.mahendran.teacherspet.StudentDatabase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.mahendran.teacherspet.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addStudent extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference classTeacherCloudEndPoint;
    private DatabaseReference classCloudEndPoint;
    private DatabaseReference studentCloudEndPoint;

    private EditText stname;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email=pref.getString("Email", null);
        String className=pref.getString("class", null);
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        teacherCloudEndPoint = mDatabase.child("Teachers");
        classTeacherCloudEndPoint = teacherCloudEndPoint.child(email);
        classCloudEndPoint = classTeacherCloudEndPoint.child(className);
        studentCloudEndPoint = classCloudEndPoint.child("Students");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                stname = (EditText) findViewById(R.id.studentName);
                pwd = (EditText) findViewById(R.id.Password);
                StudentValues sv=new StudentValues();
                sv.studentName=(String.valueOf(stname.getText()));
                sv.password=(String.valueOf(pwd.getText()));
                //sv.setStudentName(String.valueOf(stname.getText()));
                //sv.setPassword(String.valueOf(pwd.getText()));
                String key = studentCloudEndPoint.push().getKey();
                sv.setId(key);

                studentCloudEndPoint.child(key).setValue(sv);

            }
        });
    }

}
