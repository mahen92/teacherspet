package com.example.mahendran.teacherspet.StudentDatabase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mahendran.teacherspet.Connectivity.ConnectivityReceiver;
import com.example.mahendran.teacherspet.Connectivity.MyApplication;
import com.example.mahendran.teacherspet.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addStudent extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
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

                stname = (EditText) findViewById(R.id.studentName);
                pwd = (EditText) findViewById(R.id.Password);
                String studname=String.valueOf(stname.getText());
                String mailID=String.valueOf(pwd.getText());

                if((mailID==null)||(studname==null)||(mailID.equals(""))||(studname.equals("")))
                {
                    Toast.makeText(getBaseContext(), R.string.enter_name_and_email,
                            Toast.LENGTH_LONG).show();

                }
                else
                {
                    StudentValues sv=new StudentValues();
                    sv.setStudentName(String.valueOf(stname.getText()));
                    sv.setEmailID(String.valueOf(pwd.getText()));
                    String key = studentCloudEndPoint.push().getKey();
                    sv.setId(key);
                    studentCloudEndPoint.child(key).setValue(sv);
                    Toast.makeText(getBaseContext(),R.string.student_added,
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        statusDisplay(isConnected);
    }

    private void statusDisplay(boolean isConnected) {
        if(!(isConnected)) {
            Toast.makeText(getApplication(), R.string.connectrivity_issue, Toast.LENGTH_SHORT).show();
        }
    }

}
