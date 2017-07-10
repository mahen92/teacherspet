package com.example.mahendran.teacherspet.Test;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Mahendran on 19-06-2017.
 */

public class testsAdapter extends FirebaseRecyclerAdapter<TestValues, TestValueAdapterHolder> {
    private static final String TAG = testsAdapter.class.getSimpleName();
    private Context context;

    public testsAdapter(Class<TestValues> modelClass, int modelLayout, Class<TestValueAdapterHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }


    @Override
    protected void populateViewHolder(final TestValueAdapterHolder viewHolder, TestValues model, int position) {
        viewHolder.className.setText(model.getTestName());
        final String test=model.getTestName();
        viewHolder.className.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,testandassignmentcreate.class);
                intent.putExtra("Test",test);
                context.startActivity(intent);

            }
        });
    }
}

