package com.example.mahendran.teacherspet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mahendran.teacherspet.R;

/**
 * Created by Mahendran on 17-06-2017.
 */

public class DiscussionValuesAdapterHolder extends RecyclerView.ViewHolder {

    private static final String TAG = FireBaseAdapterHolder.class.getSimpleName();
    public TextView answer;
    public TextView question;

    public DiscussionValuesAdapterHolder(View itemView) {
        super(itemView);
        answer = (TextView) itemView.findViewById(R.id.answer);
        question = (TextView) itemView.findViewById(R.id.question);

    }
}
