package com.example.mahendran.teacherspet.ClassesAndTeachers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.mahendran.teacherspet.DiscussionRoom.DiscussionValuesAdapterHolder;
import com.example.mahendran.teacherspet.DiscussionRoom.DiscussionboardValues;
import com.example.mahendran.teacherspet.DiscussionRoom.discussionAdapter;
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

public class ClassAndTeachers extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference classCloudEndPoint;
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
        golinearLayout= (LinearLayout) findViewById(R.id.get_class_student);
        getClass=(EditText)findViewById(R.id.get_class);
        className=(EditText)findViewById(R.id.class_name);
        teacherName=(EditText)findViewById(R.id.teacher_name);
        classAdd=(Button)findViewById(R.id.add_class_button);
        goButton=(Button)findViewById(R.id.go_button);
        logout=(Button)findViewById(R.id.log_out);
        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        teacherCloudEndPoint = mDatabase.child("Teachers");
        classCloudEndPoint=teacherCloudEndPoint.child(auth.getCurrentUser().getEmail().split("@")[0]);
        Intent intent = getIntent();
        String id = intent.getStringExtra("String");

        if((id!=null)&&(id.equals("Teacher")))
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("Email",auth.getCurrentUser().getEmail().split("@")[0] );
            editor.commit();
            linearLayout.setVisibility(View.VISIBLE);
        }
        if((id!=null)&&(id.equals("Student")))
        {
            golinearLayout.setVisibility(View.VISIBLE);
        }

        logout.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                auth.signOut();
                Toast.makeText(getBaseContext(), "You have been logged out.",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();

                String nameClass=String.valueOf(className.getText());
                String nameTeacher=(String.valueOf(teacherName.getText()).split("@")[0]);
                editor.putString("Email",nameTeacher);
                editor.putString("class",nameClass);
                editor.commit();
                ArrayList<StudentValues> studentObjectList=getStudentObjects(nameClass,nameTeacher);
                if(checkInClass(studentObjectList)) {
                    editor.putString("Email", (String.valueOf(teacherName.getText()).split("@")[0]));
                    editor.putString("class", (String.valueOf(className.getText())));
                    Intent intent = new Intent(getBaseContext(), actionsscreen.class);
                    intent.putExtra("String", "Student");
                    intent.putExtra("id",studentObjectList.get(0).studentName);
                    startActivity(intent);
                }
            }
        });


        classAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClassValues cv= new ClassValues();
                ClassValues dv= new ClassValues();
                String classname=(String.valueOf(getClass.getText()));
                dv.ClassId=classname;
                cv.ClassId=classname;
                DatabaseReference tempCloudEndPoint;
                tempCloudEndPoint=classCloudEndPoint.child(classname);
                String key = tempCloudEndPoint.push().getKey();
                tempCloudEndPoint.child(key).setValue(cv);
                DatabaseReference tempClassCloudEndPoint;
                tempClassCloudEndPoint=teacherCloudEndPoint.child(auth.getCurrentUser().getEmail().split("@")[0]);
                String key1 = tempClassCloudEndPoint.push().getKey();
                tempClassCloudEndPoint.child(key1).setValue(cv);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView gridView=(RecyclerView)findViewById(R.id.class_list_view);

        DatabaseReference tempClassCloudEndPoint;
        tempClassCloudEndPoint=teacherCloudEndPoint.child(auth.getCurrentUser().getEmail().split("@")[0]);
        ca= new ClassAdapter(ClassValues.class, R.layout.teachers_and_classes, ClassValueAdapterHolder.class, tempClassCloudEndPoint, this);
        gridView.setLayoutManager(linearLayoutManager);
        gridView.setAdapter(ca);



    }
    private boolean inClass(String className,String teacher)
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
        studentCloudEndPoint.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                            Log.v("Cheker","Running11");
                            noteSnapshot.getKey();
                            StudentValues note = noteSnapshot.getValue(StudentValues.class);
                            Log.v("Chekerd",""+note.studentName);
                            studentList.add(note.studentName);


                        }
                    }
                     @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Checkless", databaseError.getMessage());
                    }
                });
        if(studentList.contains(auth.getCurrentUser().getEmail()))
        {
            return true;
        }
        Toast.makeText(getBaseContext(), "You are not allowed in this class.",
                Toast.LENGTH_LONG).show();


        return false;
    }

    private ArrayList<StudentValues> getStudentObjects(String className,String teacher)
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
        studentCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    Log.v("Cheker","Running11");
                    noteSnapshot.getKey();
                    StudentValues note = noteSnapshot.getValue(StudentValues.class);
                    Log.v("Chekerd",""+note.studentName);
                    if(note.studentName.equals(auth.getCurrentUser().getEmail())) {
                        studentObjectList.add(note);
                    }


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Checkless", databaseError.getMessage());
            }
        });
        return studentObjectList;
    }

    private boolean checkInClass(ArrayList<StudentValues> sv)
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

}
