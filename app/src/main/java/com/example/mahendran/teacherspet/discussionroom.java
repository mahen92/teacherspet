package com.example.mahendran.teacherspet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mahendran.teacherspet.adapters.discussionAdapter;

import java.util.ArrayList;

public class discussionroom extends AppCompatActivity {

    ArrayList<String> arrList=new ArrayList<String>();
    discussionAdapter ds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussionroom);
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
        arrList.add("Student 1");
        arrList.add("Student 2");
        ds=new discussionAdapter(getBaseContext(),arrList);
        ListView gridView=(ListView)findViewById(R.id.discussionlistview);

        Log.v("Test","gridAlohamora");
        gridView.setAdapter(ds);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long l)
            {

                // Intent intent = new Intent(getActivity(), onClickActivity.class);
                //intent.putExtra(Intent.EXTRA_TEXT, resultS.get(position));
                //startActivity(intent);

            }
        });
    }

}
