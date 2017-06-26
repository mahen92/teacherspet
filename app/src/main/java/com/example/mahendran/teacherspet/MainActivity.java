package com.example.mahendran.teacherspet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mahendran.teacherspet.ClassesAndTeachers.ClassAndTeachers;
import com.example.mahendran.teacherspet.ClassesAndTeachers.ClassValues;
import com.example.mahendran.teacherspet.StudentDatabase.StudentValues;
import com.example.mahendran.teacherspet.firebase.Teachervalues;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference listCloudEndPoint;

    private EditText et_Username;
    // Password
    private EditText et_Password;
    private Button bt_SignIn;
    private Button bt_SignUp;
    private Button resetButton;
    private FirebaseAuth auth;
    private RadioGroup radioRoleGroup;
    private RadioButton radioRoleButton;
    private ArrayList<String> teacherList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_Username = (EditText) findViewById(R.id.et_Username);
        et_Password = (EditText) findViewById(R.id.et_Password);
        bt_SignIn = (Button) findViewById(R.id.button1);
        bt_SignUp = (Button) findViewById(R.id.sign_up);
        resetButton=(Button) findViewById(R.id.btn_reset_password);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        teacherCloudEndPoint = mDatabase.child("Teachers");
        listCloudEndPoint = mDatabase.child("TeachersList");
        auth = FirebaseAuth.getInstance();
        radioRoleGroup = (RadioGroup) findViewById(R.id.radioRole);

        //discussionCloudEndPoint = mDatabase.child("Discussion");


        bt_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(et_Username.getText());
                String password = String.valueOf(et_Password.getText());
                int selectedId = radioRoleGroup.getCheckedRadioButtonId();
                radioRoleButton = (RadioButton) findViewById(selectedId);
                auth.createUserWithEmailAndPassword(email, password);


                //teacherCloudEndPoint.setValue(teachValues);
                if(radioRoleButton.getText().equals("Teacher"))
                {
                    Teachervalues teachValues=new Teachervalues();
                    teachValues.teacherId=email;
                    String key = listCloudEndPoint.push().getKey();
                    listCloudEndPoint.child(key).setValue(teachValues);
                    DatabaseReference tempCloudEndPoint;
                    tempCloudEndPoint = teacherCloudEndPoint.child(email.split("@")[0]);
                }
                Toast.makeText(getBaseContext(), "Done",
                        Toast.LENGTH_LONG).show();


            }
        });



        bt_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = String.valueOf(et_Username.getText());
                String password = String.valueOf(et_Password.getText());
                int selectedId = radioRoleGroup.getCheckedRadioButtonId();
                radioRoleButton = (RadioButton) findViewById(selectedId);
                final CharSequence role=radioRoleButton.getText();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    Toast.makeText(getBaseContext(), "Try Again",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    if((role.equals("Teacher")&&(checkTeacher(email,role)))||(radioRoleButton.getText().equals("Student")&&!(checkTeacher(email,role))))
                                    {
                                        Toast.makeText(getBaseContext(), "Gall",
                                                Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getBaseContext(),ClassAndTeachers.class);
                                        intent.putExtra("String",radioRoleButton.getText());
                                        startActivity(intent);
                                        finish();
                                    }

                                }

                            }
                        });

            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = String.valueOf(et_Username.getText()).toString().trim();

                if (email.equals("")) {
                    Toast.makeText(getApplication(), "Enter your registered email id to reset password", Toast.LENGTH_SHORT).show();
                    return;
                }


                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        });

    }

    private boolean checkTeacher(String email,CharSequence role)
    {
        DatabaseReference listCloudEndPoint;
        listCloudEndPoint = mDatabase.child("TeachersList");

        listCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){

                    noteSnapshot.getKey();
                    Teachervalues note = noteSnapshot.getValue(Teachervalues.class);
                    teacherList.add(note.teacherId);

                }
            } @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "There seems to be an issue with the database.Please try later",
                        Toast.LENGTH_LONG).show();
                Log.d("Checkless", databaseError.getMessage());
            }
        });
        Log.v("Please",role.toString());
        if(teacherList.contains(email))
        {
            Log.v("Please",role.toString());
            if(role.equals("Student"))
            {
                Toast.makeText(getBaseContext(), "You are a Teacher. Please login as a teacher",
                        Toast.LENGTH_LONG).show();

            }
            return true;
        }

        Toast.makeText(getBaseContext(), "You are not a Teacher.",
                Toast.LENGTH_LONG).show();
        return false;

    }

}
