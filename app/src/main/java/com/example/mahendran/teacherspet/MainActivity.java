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

import com.example.mahendran.teacherspet.firebase.Teachervalues;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;
    private DatabaseReference teacherCloudEndPoint;


    private EditText et_Username;
    // Password
    private EditText et_Password;
    private Button bt_SignIn;
    private Button bt_SignUp;
    private FirebaseAuth auth;
    private RadioGroup radioRoleGroup;
    private RadioButton radioRoleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_Username = (EditText) findViewById(R.id.et_Username);
        et_Password = (EditText) findViewById(R.id.et_Password);
        bt_SignIn = (Button) findViewById(R.id.button1);
        bt_SignUp = (Button) findViewById(R.id.sign_up);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        teacherCloudEndPoint = mDatabase.child("Teachers");
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
                    String key = teacherCloudEndPoint.push().getKey();
                    teacherCloudEndPoint.child(key).setValue(teachValues);
                    DatabaseReference tempCloudEndPoint;
                    tempCloudEndPoint = teacherCloudEndPoint.child(email.split("@")[0]);
                    String key1 = tempCloudEndPoint.push().getKey();
                    tempCloudEndPoint.child(key).setValue(email);

                }
                Toast.makeText(getBaseContext(), "Done",
                        Toast.LENGTH_LONG).show();


            }
        });


        bt_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(et_Username.getText());
                String password = String.valueOf(et_Password.getText());
                int selectedId = radioRoleGroup.getCheckedRadioButtonId();
                radioRoleButton = (RadioButton) findViewById(selectedId);
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    Toast.makeText(getBaseContext(), "Try Again",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getBaseContext(), "Gall",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getBaseContext(),actionsscreen.class);
                                    intent.putExtra("String",radioRoleButton.getText());
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        });

            }
        });

    }

}
