package com.example.mahendran.teacherspet.Test;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.StudentDatabase.FireBaseAdapterHolder;
import com.example.mahendran.teacherspet.StudentDatabase.StudentValues;
import com.example.mahendran.teacherspet.Test.TestValueAdapterHolder;
import com.example.mahendran.teacherspet.profileActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Mahendran on 07-06-2017.
 */

public class GetTestAdapter extends FirebaseRecyclerAdapter<StudentValues, GetTestAdapterHolder> {
    private static final String TAG = com.example.mahendran.teacherspet.StudentDatabase.FireBaseAdapter.class.getSimpleName();
    private Context context;
    public String testName;

    public GetTestAdapter(Class<StudentValues> modelClass, int modelLayout, Class<GetTestAdapterHolder> viewHolderClass, DatabaseReference ref, Context context,String className) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        testName=className;
        this.context = context;
    }

    @Override
    protected void populateViewHolder(GetTestAdapterHolder viewHolder, final StudentValues model, int position) {
        Log.v("Firebaser","adapeter");
        viewHolder.name.setText(model.studentName);
        final String stname=model.studentName;
        if(model.map.containsKey(testName)) {
            viewHolder.marks.setText(model.map.get(testName));
        }
        final String key=model.id;

        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AddTest.class);
                intent.putExtra("TestName",testName);
                intent.putExtra("StudentName",stname);
                intent.putExtra("Key",key);
                context.startActivity(intent);

            }
        });
    }
}