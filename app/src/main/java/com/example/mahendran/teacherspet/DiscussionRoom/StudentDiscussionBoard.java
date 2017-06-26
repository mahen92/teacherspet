package com.example.mahendran.teacherspet.DiscussionRoom;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.Test.TestValues;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentDiscussionBoard extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference DiscussionboardCloudEndPoint;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference teacherClassCloudEndPoint;
    private DatabaseReference referenceClassCloudEndPoint;
    private EditText question;
    private EditText answer;
    private String from;
    private Button dltButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_discussion_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email=pref.getString("Email", null);
        String className=pref.getString("class", null);
        dltButton=(Button)findViewById(R.id.delete_button);
        mDatabase =  FirebaseDatabase.getInstance().getReference();

        mDatabase =  FirebaseDatabase.getInstance().getReference();
        referenceClassCloudEndPoint = mDatabase.child("Teachers");
        teacherCloudEndPoint = referenceClassCloudEndPoint.child(email);
        teacherClassCloudEndPoint = teacherCloudEndPoint.child(className);
        DiscussionboardCloudEndPoint = teacherClassCloudEndPoint.child("DiscussionBoard");
        from=getIntent().getStringExtra("from");
        final String quest=getIntent().getStringExtra("question");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        answer = (EditText) findViewById(R.id.answer_by_teacher);
        question = (EditText) findViewById(R.id.Question_from_student);
        String teacherAnswer="";
        String studentQuestion="";

        teacherAnswer=getIntent().getStringExtra("answer");
        studentQuestion=getIntent().getStringExtra("question");
        final String id=getIntent().getStringExtra("id");
        answer.setText(teacherAnswer);
        question.setText(studentQuestion);

        if((from==null)){
            answer.setEnabled(false);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("StringPlease","In the onClick");
                Toast.makeText(getBaseContext(), "Clicked",
                        Toast.LENGTH_LONG).show();
                DiscussionboardValues dv=new DiscussionboardValues();
                dv.answer=("Answer"+String.valueOf(answer.getText()));
                dv.question=("Question"+String.valueOf(question.getText()));
                String key="";
                if(id==null) {
                    key = DiscussionboardCloudEndPoint.push().getKey();
                }
                else
                {
                    key=id;
                }
                dv.id=key;
                DiscussionboardCloudEndPoint.child(key).setValue(dv);
                Snackbar.make(view, "Replace with your own fucking action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        dltButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiscussionboardCloudEndPoint.child(id).removeValue();
            }
        });
    }

}
