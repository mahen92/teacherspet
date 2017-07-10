package com.example.mahendran.teacherspet.DiscussionRoom;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.mahendran.teacherspet.Connectivity.ConnectivityReceiver;
import com.example.mahendran.teacherspet.Connectivity.MyApplication;
import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.Widget.WidgetProvider;
import com.example.mahendran.teacherspet.actionsscreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class discussionroom extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    discussionAdapter ds;
    private DatabaseReference mDatabase;
    private DatabaseReference DiscussionboardCloudEndPoint;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference teacherClassCloudEndPoint;
    private DatabaseReference referenceClassCloudEndPoint;
    private LinearLayoutManager linearLayoutManager;
    ArrayList<DiscussionboardValues> discussionList;
    String from="";
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussionroom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
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
        discussionList=new ArrayList<>();
        Intent intent1 = new Intent(getApplicationContext(),WidgetProvider.class);
        intent1.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), WidgetProvider.class));
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        intent1.putExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS,discussionList);
        sendBroadcast(intent1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(!((auth.getCurrentUser().getEmail().split("@")[0]).equals(email)))
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
        RecyclerView gridView=(RecyclerView)findViewById(R.id.discussionlistview);
        linearLayoutManager = new LinearLayoutManager(this);

        ds = new discussionAdapter(DiscussionboardValues.class, R.layout.discussiondesign, DiscussionValuesAdapterHolder.class, DiscussionboardCloudEndPoint, this,from);
        gridView.setLayoutManager(linearLayoutManager);
        gridView.setAdapter(ds);
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


