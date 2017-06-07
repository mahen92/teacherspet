package com.example.mahendran.teacherspet.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mahendran.teacherspet.R;

import java.util.ArrayList;

/**
 * Created by Mahendran on 28-05-2017.
 */

public class testsAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    ArrayList<String> imageUrls1;
    String[] images;

    public testsAdapter(Context context, ArrayList<String> imageUrls) {
        super(context, R.layout.student_list_content, imageUrls);

        this.context=context;
        imageUrls1=imageUrls;

        inflater=LayoutInflater.from(context);


    }
    public long getItemId(int position)
    {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.v("Test","Alohamora");
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.testassignmentdesign, parent, false);
        }
        if((imageUrls1.size()!=0)&&(imageUrls1!=null)) {

            TextView name = (TextView)convertView.findViewById(R.id.name);
            TextView credit = (TextView)convertView.findViewById(R.id.credit);
            TextView syllabus = (TextView)convertView.findViewById(R.id.syllabus);
            TextView highestScore = (TextView)convertView.findViewById(R.id.highestScore);
            TextView lowestScore = (TextView)convertView.findViewById(R.id.lowestScore);
            Log.v("Tesalt",imageUrls1.get(position));
            name.setText(imageUrls1.get(position));
            credit.setText("2");
            syllabus.setText("Instagram");
            highestScore.setText("87");
            lowestScore.setText("44");
            //Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+images[position]).fit().into((ImageView) convertView);


        }


        return convertView;
    }
}
