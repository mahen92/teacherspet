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

public class discussionAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    ArrayList<String> imageUrls1;
    String[] images;

    public discussionAdapter(Context context, ArrayList<String> imageUrls) {
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
            convertView = inflater.inflate(R.layout.discussiondesign, parent, false);
        }
        if((imageUrls1.size()!=0)&&(imageUrls1!=null)) {

            TextView title = (TextView)convertView.findViewById(R.id.question);
            TextView title1 = (TextView)convertView.findViewById(R.id.answer);
            Log.v("Tesalt",imageUrls1.get(position));
            title.setText("What are we doing?");
            title.setText("Android and stuff");
            //Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+images[position]).fit().into((ImageView) convertView);


        }


        return convertView;
    }
}
