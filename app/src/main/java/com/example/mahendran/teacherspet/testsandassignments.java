package com.example.mahendran.teacherspet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.mahendran.teacherspet.adapters.testsAdapter;
import com.example.mahendran.teacherspet.onClickClasses.testandassignmentcreate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class testsandassignments extends AppCompatActivity {
    ArrayList<String> arrList=new ArrayList<String>();

    private DatabaseReference dr;
    testsAdapter ts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testsandassignments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), testandassignmentcreate.class);
                startActivity(intent);
            }
        });
        dr =  FirebaseDatabase.getInstance().getReference();
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Log.v("children name",postSnapshot.getKey());
                    if(!postSnapshot.getKey().equals("Students")) {
                        arrList.add(postSnapshot.getKey());
                        ts=new testsAdapter(getBaseContext(),arrList);
                        ListView gridView=(ListView)findViewById(R.id.testslistview);

                        Log.v("Test","gridAlohamora");
                        gridView.setAdapter(ts);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }


        });



        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long l)
            {

                // Intent intent = new Intent(getActivity(), onClickActivity.class);
                //intent.putExtra(Intent.EXTRA_TEXT, resultS.get(position));
                //startActivity(intent);

            }
        });*/

    }

}
