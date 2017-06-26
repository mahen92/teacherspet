package com.example.mahendran.teacherspet.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.firebase.StudentValues;
import com.example.mahendran.teacherspet.firebase.TestValues;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mahendran on 07-06-2017.
 */

public class GetTestAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    StudentValues[] studVals;

    String testName;
    DatabaseReference studentCloudEndPoint;
    TestValueAdapterHolder holder;
    ArrayList<String> arr=new ArrayList<>();


    public GetTestAdapter(Context context, StudentValues[] studVals, String testName, DatabaseReference ref) {
        super(context, R.layout.student_list_content, studVals);

        this.context=context;
        this.studVals=studVals;
        this.testName=testName;
        inflater=LayoutInflater.from(context);
        studentCloudEndPoint=ref;


    }
    public long getItemId(int position)
    {
        return position;
    }
    public View getView(final int position, View convertView, ViewGroup parent){
        Log.v("fuck","fuck");
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.student_and_marks, parent, false);
        }
        //holder=new TestValueAdapterHolder(convertView);
        convertView.setTag(holder);
        final int pos=position;
        if((studVals.length!=0)&&(studVals!=null)) {
            TextView name=(TextView)convertView.findViewById(R.id.testname);
            name.setText(studVals[position].studentName);
            EditText marks=(EditText)convertView.findViewById(R.id.marks);

           // holder.marks.setText(arr.get(pos));

            marks.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {


                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String key=studVals[pos].id;
                    Log.v("pleasekey",key);



                    StudentValues stud=new StudentValues();
                    stud=studVals[pos];
                    stud.map.put(testName,s.toString());
                    studentCloudEndPoint.child(key).setValue(stud);
                    //arr.add(position,s.toString());
                }

            });
            /*if((arr!=null)&&(arr.size()!=0)&&(pos<=arr.size())) {
                holder.marks.setText(arr.get(pos));
            }*/
        }
        return convertView;
    }
}
