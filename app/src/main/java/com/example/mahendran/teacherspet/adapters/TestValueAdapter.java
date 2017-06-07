package com.example.mahendran.teacherspet.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mahendran.teacherspet.firebase.StudentValues;
import com.example.mahendran.teacherspet.firebase.TestValues;
import com.example.mahendran.teacherspet.profileActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

import static com.example.mahendran.teacherspet.R.id.marks;

/**
 * Created by Mahendran on 05-06-2017.
 */

public class TestValueAdapter extends FirebaseRecyclerAdapter<StudentValues, TestValueAdapterHolder> {
    public String stri;
    private static final String TAG = FireBaseAdapter.class.getSimpleName();
    private Context context;
    DatabaseReference testCloudEndPoint;
    DatabaseReference studentCloudEndPoint;
    public TestValueAdapter(Class<StudentValues> modelClass, int modelLayout, Class<TestValueAdapterHolder> viewHolderClass, DatabaseReference ref, Context context,DatabaseReference testref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        testCloudEndPoint=testref;
        this.context = context;
        studentCloudEndPoint=ref;
    }



    @Override
    protected void populateViewHolder(TestValueAdapterHolder viewHolder, final StudentValues model, int position) {
        Log.v("Firebaser","adapeter");
        viewHolder.name.setText(model.studentName);

        String key1= model.id;
        //final String name= model.studentName;

        viewHolder.marks.addTextChangedListener(new TextWatcher() {
            String key= testCloudEndPoint.push().getKey();
           TestValues tv=new TestValues();
            StudentValues sv= new StudentValues();
            String key1= model.id;
            public void afterTextChanged(Editable s) {
               /* sv.map.put("test",s.toString());
                sv.studentName=model.studentName;
                sv.id=model.id;
                studentCloudEndPoint.child(key1).setValue(sv);*/

                tv.id=key;
                tv.marks=s.toString();
                tv.studentname=model.studentName;
                testCloudEndPoint.child(key).setValue(tv);
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {


            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                model.map.put("test",""+s.toString());
            }

        });

    }


}
