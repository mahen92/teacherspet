package com.example.mahendran.teacherspet.ClassesAndTeachers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mahendran.teacherspet.Connectivity.ConnectivityReceiver;
import com.example.mahendran.teacherspet.Connectivity.MyApplication;
import com.example.mahendran.teacherspet.DiscussionRoom.DiscussionValuesAdapterHolder;
import com.example.mahendran.teacherspet.DiscussionRoom.DiscussionboardValues;
import com.example.mahendran.teacherspet.DiscussionRoom.discussionAdapter;
import com.example.mahendran.teacherspet.MainActivity;
import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.StudentDatabase.StudentValues;
import com.example.mahendran.teacherspet.actionsscreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ClassAndTeachers extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    public DatabaseReference mDatabase;
    public DatabaseReference teacherCloudEndPoint;
    public DatabaseReference classCloudEndPoint;
    private FirebaseAuth auth;
    private LinearLayoutManager linearLayoutManager;
    SharedPreferences sharedpreferences;
    EditText getClass;
    EditText className;
    EditText teacherName;
    Button classAdd;
    Button goButton;
    Button logout;
    ClassAdapter ca;
    LinearLayout linearLayout;
    LinearLayout golinearLayout;
    ArrayList studentList= new ArrayList();
    ArrayList<StudentValues> studentObjectList= new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_and_teachers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        linearLayout= (LinearLayout) findViewById(R.id.add_class_layout);

        getClass=(EditText)findViewById(R.id.get_class);
        className=(EditText)findViewById(R.id.class_name);
        teacherName=(EditText)findViewById(R.id.teacher_name);
        classAdd=(Button)findViewById(R.id.add_class_button);
        logout=(Button)findViewById(R.id.log_out);
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email=sharedpreferences.getString("Email", null);
        Intent intent = getIntent();
        String id = intent.getStringExtra("String");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        teacherCloudEndPoint = mDatabase.child("Teachers");
        classCloudEndPoint=teacherCloudEndPoint.child(auth.getCurrentUser().getEmail().split("@")[0]);


        if(((auth.getCurrentUser().getEmail().split("@")[0]).equals(email)))
        {

            linearLayout.setVisibility(View.VISIBLE);
        }

        logout.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                auth.signOut();
                Toast.makeText(getBaseContext(), "You have been logged out.",
                        Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                finish();
            }
        });


        classAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassValues cv= new ClassValues();
                //ClassValues dv= new ClassValues();
                String classname=(String.valueOf(getClass.getText()));
                //dv.ClassId=classname;
                cv.setClassName(classname);
                DatabaseReference tempCloudEndPoint;
                tempCloudEndPoint=classCloudEndPoint.child(classname);
                String key = tempCloudEndPoint.push().getKey();
                tempCloudEndPoint.child(key).setValue(cv);
                DatabaseReference tempClassCloudEndPoint;
                DatabaseReference tempClassCloudEndPoint1;
                tempClassCloudEndPoint=teacherCloudEndPoint.child(auth.getCurrentUser().getEmail().split("@")[0]);
                tempClassCloudEndPoint1=tempClassCloudEndPoint.child("Classes");
                String key1 = tempClassCloudEndPoint1.push().getKey();
                tempClassCloudEndPoint1.child(key1).setValue(cv);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView gridView=(RecyclerView)findViewById(R.id.class_list_view);

        DatabaseReference tempClassCloudEndPoint;
        DatabaseReference tempClassCloudEndPoint1;

        tempClassCloudEndPoint=teacherCloudEndPoint.child(email);
        tempClassCloudEndPoint1=tempClassCloudEndPoint.child("Classes");
        ca= new ClassAdapter(ClassValues.class, R.layout.teachers_and_classes, ClassValueAdapterHolder.class, tempClassCloudEndPoint1, this);
        gridView.setLayoutManager(linearLayoutManager);
        gridView.setAdapter(ca);
    }


    public ArrayList<StudentValues> getStudentObjects(String className,String teacher,String studName)
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

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityReceiver receiver=new ConnectivityReceiver();
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

