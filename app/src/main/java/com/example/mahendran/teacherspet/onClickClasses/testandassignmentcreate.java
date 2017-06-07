package com.example.mahendran.teacherspet.onClickClasses;

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
import android.widget.Toast;

import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.adapters.FireBaseAdapter;
import com.example.mahendran.teacherspet.adapters.FireBaseAdapterHolder;
import com.example.mahendran.teacherspet.adapters.TestValueAdapter;
import com.example.mahendran.teacherspet.adapters.TestValueAdapterHolder;
import com.example.mahendran.teacherspet.firebase.StudentValues;
import com.example.mahendran.teacherspet.firebase.TestValues;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class testandassignmentcreate extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference studentCloudEndPoint;
    private DatabaseReference testCloudEndPoint;
    private TestValues tv=new TestValues();
    EditText testName;
    private LinearLayoutManager linearLayoutManager;
    private TestValueAdapter mTestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testandassignmentcreate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //TestValues tv=new TestValues();

        mDatabase =  FirebaseDatabase.getInstance().getReference();
        studentCloudEndPoint = mDatabase.child("Students");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                testName=(EditText)findViewById(R.id.testname);
                String testName1=(String.valueOf(testName.getText()));
                Toast.makeText(getBaseContext(), testName1+"1",
                        Toast.LENGTH_LONG).show();
                testCloudEndPoint=mDatabase.child(testName1);


                linearLayoutManager = new LinearLayoutManager(getBaseContext());
                mTestAdapter = new TestValueAdapter(StudentValues.class, R.layout.student_and_marks, TestValueAdapterHolder.class, studentCloudEndPoint, getBaseContext(),testCloudEndPoint);
                RecyclerView testlistview=(RecyclerView) findViewById(R.id.student_test_list_view);
                testlistview.setVisibility(View.VISIBLE);
                Log.v("Test","gridAlohamora");
                testlistview.setLayoutManager(linearLayoutManager);
                testlistview.setAdapter(mTestAdapter);

            }
        });
       linearLayoutManager = new LinearLayoutManager(this);
        mTestAdapter = new TestValueAdapter(StudentValues.class, R.layout.student_and_marks, TestValueAdapterHolder.class, studentCloudEndPoint, getBaseContext(),studentCloudEndPoint);

        //cs=new customAdapter(getBaseContext(),arrList);
        RecyclerView testlistview=(RecyclerView) findViewById(R.id.student_test_list_view);
        Log.v("Test","gridAlohamora");
        testlistview.setLayoutManager(linearLayoutManager);
        testlistview.setAdapter(mTestAdapter);

    }

}
