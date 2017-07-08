package com.example.mahendran.teacherspet.Test;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahendran.teacherspet.Connectivity.ConnectivityReceiver;
import com.example.mahendran.teacherspet.Connectivity.MyApplication;
import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.StudentDatabase.FireBaseAdapter;
import com.example.mahendran.teacherspet.StudentDatabase.FireBaseAdapterHolder;
import com.example.mahendran.teacherspet.StudentDatabase.StudentValues;
import com.example.mahendran.teacherspet.StudentDatabase.addStudent;
import com.example.mahendran.teacherspet.customAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class testandassignmentcreate extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    private DatabaseReference mDatabase;
    private DatabaseReference studentCloudEndPoint;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference teacherClassCloudEndPoint;
    private DatabaseReference referenceClassCloudEndPoint;
    private GetTestAdapter getTestAdapter;
    private LinearLayoutManager linearLayoutManager;
    @BindView(R.id.highestScore) TextView highScore;
    @BindView(R.id.lowestScore) TextView lowScore;
    @BindView(R.id.average) TextView average;
    @BindView(R.id.topranker) TextView topper;
    customAdapter cs;
    ArrayList<StudentValues> studentList;
    ArrayList<Integer> mapList;
    Callback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testandassignmentcreate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        studentList=new ArrayList<>();
        final String testName=intent.getStringExtra("Test");
        callback=new Callback() {
            @Override
            public void act(ArrayList<StudentValues> models) {
                String toppStudent = "";
                int sum = 0;
                for (StudentValues note : models) {
                    if (note.map.get(testName) != null) {
                        int highestValue = 0;
                        int lowestvalue = 2147483647;

                        int value = Integer.parseInt(note.map.get(testName));
                        if (value > highestValue) {
                                highestValue = value;
                                toppStudent=note.emailID;
                        }
                        if (value < lowestvalue) {
                            lowestvalue = value;
                        }
                            sum = sum + value;

                        highScore.setText("Highest Mark :" + highestValue);
                        lowScore.setText("Lowest Mark :" + lowestvalue);
                        average.setText("Average :" + (sum / models.size()));
                        topper.setText("Top Student :" + toppStudent);

                    }
                }
            }

        };
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email=pref.getString("Email", null);
        String className=pref.getString("class", null);
        mapList=new ArrayList();
        mDatabase =  FirebaseDatabase.getInstance().getReference();

        mDatabase =  FirebaseDatabase.getInstance().getReference();
        referenceClassCloudEndPoint = mDatabase.child("Teachers");
        teacherCloudEndPoint = referenceClassCloudEndPoint.child(email);
        teacherClassCloudEndPoint = teacherCloudEndPoint.child(className);
        studentCloudEndPoint = teacherClassCloudEndPoint.child("Students");
        studentCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    noteSnapshot.getKey();
                    StudentValues note = noteSnapshot.getValue(StudentValues.class);
                    studentList.add(note);
                    if(note.map.get(testName)!=null) {
                        mapList.add(Integer.parseInt(note.map.get(testName)));
                    }
                    callback.act(studentList);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        linearLayoutManager = new LinearLayoutManager(this);
        getTestAdapter = new GetTestAdapter(StudentValues.class, R.layout.student_and_marks, GetTestAdapterHolder.class, studentCloudEndPoint, this,testName);
        RecyclerView gridView=(RecyclerView) findViewById(R.id.student_test_list_view);
        gridView.setLayoutManager(linearLayoutManager);
        gridView.setAdapter(getTestAdapter);

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

interface Callback {
    void act(ArrayList<StudentValues> models);
}
