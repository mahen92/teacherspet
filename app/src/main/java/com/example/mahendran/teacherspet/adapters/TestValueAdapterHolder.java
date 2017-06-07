package com.example.mahendran.teacherspet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mahendran.teacherspet.R;

/**
 * Created by Mahendran on 05-06-2017.
 */

public class TestValueAdapterHolder extends RecyclerView.ViewHolder{
    private static final String TAG = FireBaseAdapterHolder.class.getSimpleName();
    public TextView name;
    public EditText marks;

    public TestValueAdapterHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.testname);
        marks=(EditText)itemView.findViewById(R.id.marks);


    }
}