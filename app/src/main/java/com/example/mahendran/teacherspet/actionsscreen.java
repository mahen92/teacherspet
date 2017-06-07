package com.example.mahendran.teacherspet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class actionsscreen extends AppCompatActivity {
    private ImageButton button1;
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionsscreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Intent intent = getIntent();
        String id = intent.getStringExtra("Extra");

        button1 = (ImageButton) findViewById(R.id.studentdb);
        button2 = (ImageButton) findViewById(R.id.examandassignments);

        if(id.equals("teacher")) {
            button3 = (ImageButton) findViewById(R.id.discussionroom);
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            Log.v("Test1","Alohamora");
            button1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), studentDatabase.class);
                    startActivity(intent);
                    finish();
                }
            });

            button3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), discussionroom.class);
                    startActivity(intent);
                    finish();
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), testsandassignments.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
        if(id.equals("student"))
        {
            button3 = (ImageButton) findViewById(R.id.discussionroom);
            button4 = (ImageButton) findViewById(R.id.profiles);
            button4.setVisibility(View.VISIBLE);
            button4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), profileActivity.class);
                    intent.putExtra("student","s");
                    startActivity(intent);
                    finish();
                }
            });

        }


    }

}
