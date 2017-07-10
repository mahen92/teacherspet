package com.example.mahendran.teacherspet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahendran.teacherspet.StudentDatabase.StudentValues;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profileActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference classTeacherCloudEndPoint;
    private DatabaseReference classCloudEndPoint;
    private DatabaseReference studentCloudEndPoint;
    private DatabaseReference tempCloudEndPoint;
    private DatabaseReference deleteCloudEndPoint;
    private FirebaseAuth auth;
    StudentValues note=new StudentValues();
    EditText name1;
    TextView mailID;
    EditText studentPh;
    EditText parentPh;
    EditText address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email=pref.getString("Email", null);
        String className=pref.getString("class", null);
        auth = FirebaseAuth.getInstance();
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        teacherCloudEndPoint = mDatabase.child("Teachers");
        classTeacherCloudEndPoint = teacherCloudEndPoint.child(email);
        classCloudEndPoint = classTeacherCloudEndPoint.child(className);
        studentCloudEndPoint = classCloudEndPoint.child("Students");
        name1=(EditText) findViewById(R.id.name);
        mailID=(TextView) findViewById(R.id.mail_id);
        studentPh=(EditText) findViewById(R.id.stud_ph);
        parentPh=(EditText) findViewById(R.id.parent_ph);
        address=(EditText) findViewById(R.id.address);
        Intent intent=getIntent();
        String s=intent.getStringExtra("student");
        String id = intent.getStringExtra("id");
        Bundle b = intent.getExtras();
        if (b != null)
        {
            note =(StudentValues) b.getSerializable("Object");
            mailID.setText(note.getStudentName());
            name1.setText(note.getEmailID());
            parentPh.setText(note.getParentph());
            studentPh.setText(note.getStudentph());
            address.setText(note.getAddress());

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempCloudEndPoint=studentCloudEndPoint.child(note.getId());
                note.setStudentName(String.valueOf(mailID.getText()));
                note.setEmailID(String.valueOf(name1.getText()));
                note.setParentph(String.valueOf(parentPh.getText()));
                note.setStudentph(String.valueOf(studentPh.getText()));
                note.setAddress(String.valueOf(address.getText()));
                tempCloudEndPoint.setValue(note);
                Toast.makeText(getApplication(),R.string.updated, Toast.LENGTH_SHORT).show();
            }
        });

        if(((auth.getCurrentUser().getEmail().split("@")[0]).equals(email))) {
            FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.delete);
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCloudEndPoint = studentCloudEndPoint.child(note.getId());
                    deleteCloudEndPoint.removeValue();
                    Toast.makeText(getApplication(), R.string.deleted, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }

        MyAdapter adapter = new MyAdapter(note.map);
        ListView profileTestView=(ListView)findViewById(R.id.profile_tests);
        profileTestView.setAdapter(adapter);
    }
}
