package com.example.mahendran.teacherspet;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mahendran.teacherspet.Connectivity.ConnectivityReceiver;
import com.example.mahendran.teacherspet.Connectivity.MyApplication;
import com.example.mahendran.teacherspet.DiscussionRoom.discussionroom;
import com.example.mahendran.teacherspet.StudentDatabase.StudentValues;
import com.example.mahendran.teacherspet.StudentDatabase.studentDatabase;
import com.example.mahendran.teacherspet.Test.testsandassignments;
import com.example.mahendran.teacherspet.DiscussionRoom.DiscussionboardValues;
import com.example.mahendran.teacherspet.Widget.WidgetProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class actionsscreen extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;
    String studName="Fail";
    SharedPreferences sharedpreferences;
    private FirebaseAuth auth;

    ArrayList<StudentValues> studentObjectList= new ArrayList();
    ArrayList studentList= new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionsscreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String id = intent.getStringExtra("String");

        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email=sharedpreferences.getString("Email", null);
        String className=sharedpreferences.getString("class", null);
        studName=intent.getStringExtra("id");
        button1 = (ImageButton) findViewById(R.id.studentdb);
        button2 = (ImageButton) findViewById(R.id.examandassignments);




        if(((auth.getCurrentUser().getEmail().split("@")[0]).equals(email))) {

            button3 = (ImageButton) findViewById(R.id.discussionroom);
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            Log.v("Test1","Alohamora");
            button1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), studentDatabase.class);
                    startActivity(intent);
                }
            });

            button3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), discussionroom.class);
                    startActivity(intent);
                    intent.putExtra("teacher","t");
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), testsandassignments.class);
                    startActivity(intent);
                }
            });

        }

        if(!((auth.getCurrentUser().getEmail().split("@")[0]).equals(email)))
        {

            final ArrayList<StudentValues> studentObjectList=getStudentObjects(className,email,auth.getCurrentUser().getEmail());

            button3 = (ImageButton) findViewById(R.id.discussionroom);
            button4 = (ImageButton) findViewById(R.id.profiles);
            button4.setVisibility(View.VISIBLE);
            button4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(checkInClass(studentObjectList)) {
                        Intent intent = new Intent(getBaseContext(), profileActivity.class);
                        intent.putExtra("student", "s");
                        Bundle b= new Bundle();
                        b.putSerializable("Object",studentObjectList.get(0));
                        intent.putExtra("id", studName);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "You are not allowed in this class.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

            button3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(checkInClass(studentObjectList)) {
                        Intent intent = new Intent(getBaseContext(), discussionroom.class);
                        intent.putExtra("student", "s");
                        startActivity(intent);

                    }
                        else
                        {
                            Toast.makeText(getBaseContext(), "You are not allowed in this class.",
                                    Toast.LENGTH_LONG).show();
                        }
                }
            });

        }
    }
    public ArrayList<StudentValues> getStudentObjects(String className, String teacher, String studName)
    {
        DatabaseReference mDatabase;
        DatabaseReference studentCloudEndPoint;
        DatabaseReference teacherCloudEndPoint;
        DatabaseReference teacherClassCloudEndPoint;
        DatabaseReference referenceClassCloudEndPoint;
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        referenceClassCloudEndPoint = mDatabase.child("Teachers");
        teacherCloudEndPoint = referenceClassCloudEndPoint.child(teacher);
        teacherClassCloudEndPoint = teacherCloudEndPoint.child(className);
        studentCloudEndPoint = teacherClassCloudEndPoint.child("Students");
        final String studentname=studName;
        studentCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    noteSnapshot.getKey();
                    StudentValues note = noteSnapshot.getValue(StudentValues.class);
                    if(note.studentName.equals(studentname)) {
                        studentObjectList.add(note);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return studentObjectList;
    }

    public boolean checkInClass(ArrayList<StudentValues> sv)
    {
        if(sv.size()==0)
        {
            return false;
        }
        for(StudentValues s:sv)
        {
            studentList.add(s.studentName);
        }
        if(studentList.contains(auth.getCurrentUser().getEmail()))
        {
            return true;
        }
        Toast.makeText(getBaseContext(), "You are not allowed in this class.",
                Toast.LENGTH_LONG).show();


        return false;
    }


    protected void onResume() {
        super.onResume();
        // register connection status listener

        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        statusDisplay(isConnected);
    }

    private void statusDisplay(boolean isConnected) {
        if(!(isConnected)) {
            Toast.makeText(getApplication(), "There seems to be a connectivity issue. Please check your connectivity.", Toast.LENGTH_SHORT).show();
        }
    }

}
