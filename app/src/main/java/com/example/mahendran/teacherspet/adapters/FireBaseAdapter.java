package com.example.mahendran.teacherspet.adapters;

import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.firebase.StudentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mahendran.teacherspet.profileActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Mahendran on 04-06-2017.
 */


public class FireBaseAdapter extends FirebaseRecyclerAdapter<StudentValues, FireBaseAdapterHolder>{
    private static final String TAG = FireBaseAdapter.class.getSimpleName();
    private Context context;

    public FireBaseAdapter(Class<StudentValues> modelClass, int modelLayout, Class<FireBaseAdapterHolder> viewHolderClass, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);

        this.context = context;
    }

    @Override
    protected void populateViewHolder(FireBaseAdapterHolder viewHolder, final StudentValues model, int position) {
        Log.v("Firebaser","adapeter");
        viewHolder.name.setText(model.studentName);
        final String id= model.id;
        final String name= model.studentName;
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,name,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, profileActivity.class);
                intent.putExtra("id",model.studentName);

                context.startActivity(intent);

            }
        });
    }
}