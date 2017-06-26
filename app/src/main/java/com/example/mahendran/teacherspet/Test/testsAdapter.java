package com.example.mahendran.teacherspet.Test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.example.mahendran.teacherspet.DiscussionRoom.StudentDiscussionBoard;
import com.example.mahendran.teacherspet.StudentDatabase.FireBaseAdapter;
import com.example.mahendran.teacherspet.StudentDatabase.studentDatabase;
import com.example.mahendran.teacherspet.Test.TestValueAdapterHolder;
import com.example.mahendran.teacherspet.Test.TestValues;
import com.example.mahendran.teacherspet.Test.testandassignmentcreate;
import com.example.mahendran.teacherspet.actionsscreen;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Mahendran on 19-06-2017.
 */

public class testsAdapter extends FirebaseRecyclerAdapter<TestValues, TestValueAdapterHolder> {
    private static final String TAG = testsAdapter.class.getSimpleName();
    private Context context;
    SharedPreferences sharedpreferences;

    public testsAdapter(Class<TestValues> modelClass, int modelLayout, Class<TestValueAdapterHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        Log.v("cheepers","cheepers");
        this.context = context;
        Log.v("cheepers1","cheepers1");
    }


    @Override
    protected void populateViewHolder(final TestValueAdapterHolder viewHolder, TestValues model, int position) {
        viewHolder.className.setText(model.testName);
        final String className=model.testName;
        viewHolder.className.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,testandassignmentcreate.class);
                intent.putExtra("Class",className);
                context.startActivity(intent);

            }
        });
    }
}

