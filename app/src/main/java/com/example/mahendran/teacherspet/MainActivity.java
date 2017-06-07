package com.example.mahendran.teacherspet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;

    private DatabaseReference studentCloudEndPoint;
    private DatabaseReference discussionCloudEndPoint;

    private EditText et_Username;
    // Password
    private EditText et_Password;
    private Button bt_SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_Username = (EditText) findViewById(R.id.et_Username);
        et_Password = (EditText) findViewById(R.id.et_Password);
        bt_SignIn = (Button) findViewById(R.id.button1);
        mDatabase =  FirebaseDatabase.getInstance().getReference();
        studentCloudEndPoint = mDatabase.child("Students");
        //discussionCloudEndPoint = mDatabase.child("Discussion");


        bt_SignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Stores User name
                String username = String.valueOf(et_Username.getText());
                // Stores Password
                String password = String.valueOf(et_Password.getText());

                // Validates the User name and Password for admin, admin
                if (username.equals("") && password.equals("")) {
                    Log.v("Test0","Alohamora");
                    String t="teacher";
                    Intent intent = new Intent(getBaseContext(), actionsscreen.class);
                    intent.putExtra("Extra",t);
                    startActivity(intent);
                    finish();
                }
                if(username.equals("s") && password.equals("s")){
                    String s="student";
                    Intent intent = new Intent(getBaseContext(), actionsscreen.class);
                    intent.putExtra("Extra",s);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "fail",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        
    }

}
