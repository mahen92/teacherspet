package com.example.mahendran.teacherspet.DiscussionRoom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.mahendran.teacherspet.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class discussionroom extends AppCompatActivity {

    ArrayList<DiscussionboardValues> discussionValues=new ArrayList<DiscussionboardValues>();
    discussionAdapter ds;
    private DatabaseReference mDatabase;
    private DatabaseReference DiscussionboardCloudEndPoint;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference teacherClassCloudEndPoint;
    private DatabaseReference referenceClassCloudEndPoint;
    private LinearLayoutManager linearLayoutManager;
    String from="lash";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussionroom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email=pref.getString("Email", null);
        String className=pref.getString("class", null);
        mDatabase =  FirebaseDatabase.getInstance().getReference();

        mDatabase =  FirebaseDatabase.getInstance().getReference();
        referenceClassCloudEndPoint = mDatabase.child("Teachers");
        teacherCloudEndPoint = referenceClassCloudEndPoint.child(email);
        teacherClassCloudEndPoint = teacherCloudEndPoint.child(className);
        DiscussionboardCloudEndPoint = teacherClassCloudEndPoint.child("DiscussionBoard");



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Intent intent=getIntent();
        String check=intent.getStringExtra("student");


        if((check!=null)&&(check.equals("s")))
        {
            fab.setVisibility(View.VISIBLE);
        }
        else
        {
            from="t";
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), StudentDiscussionBoard.class);
                startActivity(intent);
            }
        });
        //DiscussionboardValues[] discussionArray=objects.toArray(new DiscussionboardValues[objects.size()]);

        //ds=new discussionAdapter(getBaseContext(),discussionArray);
        RecyclerView gridView=(RecyclerView)findViewById(R.id.discussionlistview);
        linearLayoutManager = new LinearLayoutManager(this);

        ds = new discussionAdapter(DiscussionboardValues.class, R.layout.discussiondesign, DiscussionValuesAdapterHolder.class, DiscussionboardCloudEndPoint, this,from);
        gridView.setLayoutManager(linearLayoutManager);
        gridView.setAdapter(ds);
    }

}
