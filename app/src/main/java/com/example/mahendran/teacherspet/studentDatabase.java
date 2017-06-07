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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mahendran.teacherspet.adapters.FireBaseAdapter;
import com.example.mahendran.teacherspet.adapters.FireBaseAdapterHolder;
import com.example.mahendran.teacherspet.firebase.StudentValues;
import com.example.mahendran.teacherspet.onClickClasses.addStudent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class studentDatabase extends AppCompatActivity {

    String[] resultStrs;
    ArrayList<String> resultS=new ArrayList<String>();
    ArrayList<String> arrList=new ArrayList<String>();
    private DatabaseReference mDatabase;
    private DatabaseReference studentCloudEndPoint;
    List<StudentValues> mStudentValues = new ArrayList<>();
    private FireBaseAdapter mFireAdapter;
    private LinearLayoutManager linearLayoutManager;
    customAdapter cs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase =  FirebaseDatabase.getInstance().getReference();
        studentCloudEndPoint = mDatabase.child("Students");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), addStudent.class);
                startActivity(intent);
            }
        });
        Log.v("Cheker","Running1");
        studentCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    Log.v("Cheker","Running11");
                    noteSnapshot.getKey();
                    StudentValues note = noteSnapshot.getValue(StudentValues.class);
                    Log.v("Chekerd",""+note.studentName);
                    mStudentValues.add(note);
                    arrList.add(note.studentName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Checkless", databaseError.getMessage());
            }
        });


        linearLayoutManager = new LinearLayoutManager(this);
        mFireAdapter = new FireBaseAdapter(StudentValues.class, R.layout.student_list_content, FireBaseAdapterHolder.class, studentCloudEndPoint, this);

        //cs=new customAdapter(getBaseContext(),arrList);
        RecyclerView gridView=(RecyclerView) findViewById(R.id.list_view);

        Log.v("Test","gridAlohamora");
        gridView.setLayoutManager(linearLayoutManager);
        gridView.setAdapter(mFireAdapter);

        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long l)
            {

               Intent intent = new Intent(getApplicationContext(), profileActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, arrList.get(position));
                startActivity(intent);

            }
        });*/
    }
}

