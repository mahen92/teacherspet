package com.example.mahendran.teacherspet.DiscussionRoom;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.mahendran.teacherspet.StudentDatabase.FireBaseAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Mahendran on 28-05-2017.
 */

public class discussionAdapter extends FirebaseRecyclerAdapter<DiscussionboardValues, DiscussionValuesAdapterHolder> {
    private static final String TAG = FireBaseAdapter.class.getSimpleName();
    private Context context;
    private String teacher;

    public discussionAdapter(Class<DiscussionboardValues> modelClass, int modelLayout, Class<DiscussionValuesAdapterHolder> viewHolderClass, DatabaseReference ref, Context context,String check) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        teacher=check;
        this.context = context;
    }

    @Override
    protected void populateViewHolder(DiscussionValuesAdapterHolder viewHolder, DiscussionboardValues model, int position) {
        viewHolder.answer.setText("Answer: "+model.answer);
        viewHolder.question.setText("Question: "+model.question);
        final String quest=model.question;
        final String ans=model.answer;
        final String id=model.id;
        if(teacher!=null) {
            Log.v("Checkers", teacher);
        }
        if(teacher=="t") {
        viewHolder.answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, StudentDiscussionBoard.class);
                intent.putExtra("question",quest);
                intent.putExtra("answer",ans);
                intent.putExtra("id",id);
                intent.putExtra("from","Teacher");
                context.startActivity(intent);

            }
        });
        }
    }
}
