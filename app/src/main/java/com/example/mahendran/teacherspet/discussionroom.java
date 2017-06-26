package com.example.mahendran.teacherspet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mahendran.teacherspet.adapters.DiscussionValuesAdapterHolder;
import com.example.mahendran.teacherspet.adapters.FireBaseAdapter;
import com.example.mahendran.teacherspet.adapters.FireBaseAdapterHolder;
import com.example.mahendran.teacherspet.adapters.discussionAdapter;
import com.example.mahendran.teacherspet.firebase.DiscussionboardValues;
import com.example.mahendran.teacherspet.firebase.StudentValues;
import com.example.mahendran.teacherspet.onClickClasses.StudentDiscussionBoard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class discussionroom extends AppCompatActivity {

    ArrayList<DiscussionboardValues> discussionValues=new ArrayList<DiscussionboardValues>();
    discussionAdapter ds;
    private DatabaseReference mDatabase;
    private DatabaseReference DiscussionboardCloudEndPoint;
    private LinearLayoutManager linearLayoutManager;
    String from="lash";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussionroom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        DiscussionboardCloudEndPoint = mDatabase.child("DiscussionBoard");



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
