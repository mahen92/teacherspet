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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.adapters.FireBaseAdapter;
import com.example.mahendran.teacherspet.adapters.FireBaseAdapterHolder;
import com.example.mahendran.teacherspet.adapters.GetTestAdapter;
import com.example.mahendran.teacherspet.adapters.TestValueAdapter;
import com.example.mahendran.teacherspet.adapters.TestValueAdapterHolder;
import com.example.mahendran.teacherspet.firebase.StudentValues;
import com.example.mahendran.teacherspet.firebase.TestValues;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class testandassignmentcreate extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference studentCloudEndPoint;
    private DatabaseReference testCloudEndPoint;
    private TestValues tv=new TestValues();
    EditText testName;
    private LinearLayoutManager linearLayoutManager;
    private TestValueAdapter mTestAdapter;
    ArrayList<StudentValues> studVals=new ArrayList<>();
    GetTestAdapter getTestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testandassignmentcreate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //TestValues tv=new TestValues();

        mDatabase =  FirebaseDatabase.getInstance().getReference();
        studentCloudEndPoint = mDatabase.child("Students");
        studentCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    //Log.v("Cheker","Running11");
                    //noteSnapshot.getKey();
                    StudentValues note = noteSnapshot.getValue(StudentValues.class);
                    //ArrayList<String> arr=new ArrayList<>();
                    //arr.add(note.studentName);
                    //arr.add(note.id);
                    studVals.add(note);
                   // Log.v("Chekerd",""+note.studentName);
                    //mStudentValues.add(note);
                    //arrList.add(note.studentName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Checkless", databaseError.getMessage());
            }
        });

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
                StudentValues[] st=studVals.toArray(new StudentValues[studVals.size()]);
                getTestAdapter=new GetTestAdapter(getBaseContext(),st,testName1,studentCloudEndPoint);
                getTestAdapter.setNotifyOnChange(false);
                ListView gridView=(ListView)findViewById(R.id.testslistview);
                gridView.setVisibility(View.VISIBLE);
                gridView.setAdapter(getTestAdapter);
                /*mTestAdapter = new TestValueAdapter(StudentValues.class, R.layout.student_and_marks, TestValueAdapterHolder.class, studentCloudEndPoint, getBaseContext(),testCloudEndPoint);
                RecyclerView testlistview=(RecyclerView) findViewById(R.id.student_test_list_view);
                testlistview.setVisibility(View.VISIBLE);
                Log.v("Test","gridAlohamora");
                testlistview.setLayoutManager(linearLayoutManager);
                testlistview.setAdapter(mTestAdapter);*/

            }
        });
       linearLayoutManager = new LinearLayoutManager(this);
        mTestAdapter = new TestValueAdapter(StudentValues.class, R.layout.student_and_marks, TestValueAdapterHolder.class, studentCloudEndPoint, getBaseContext(),studentCloudEndPoint);

        //cs=new customAdapter(getBaseContext(),arrList);
       // RecyclerView testlistview=(RecyclerView) findViewById(R.id.student_test_list_view);
        Log.v("Test","gridAlohamora");
        //testlistview.setLayoutManager(linearLayoutManager);
        //testlistview.setAdapter(mTestAdapter);

    }

}
