package com.example.mahendran.teacherspet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mahendran on 27-05-2017.
 */

public class customAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    ArrayList<String> imageUrls1;
    String[] images;

    public customAdapter(Context context, ArrayList<String> imageUrls) {
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
            convertView = inflater.inflate(R.layout.student_list_content, parent, false);
        }
        if((imageUrls1.size()!=0)&&(imageUrls1!=null)) {

            TextView title = (TextView)convertView.findViewById(R.id.student);
            Log.v("Tesalt",imageUrls1.get(position));
            title.setText("Patch");
            //Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+images[position]).fit().into((ImageView) convertView);


        }


        return convertView;
    }
}
