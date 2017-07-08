package com.example.mahendran.teacherspet.Widget;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.mahendran.teacherspet.DiscussionRoom.DiscussionboardValues;
import com.example.mahendran.teacherspet.R;
import com.example.mahendran.teacherspet.StudentDatabase.StudentValues;
import com.example.mahendran.teacherspet.firebase.Teachervalues;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Mahendran on 01-07-2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private DatabaseReference mDatabase;
    private DatabaseReference DiscussionboardCloudEndPoint;
    private DatabaseReference teacherCloudEndPoint;
    private DatabaseReference teacherClassCloudEndPoint;
    private DatabaseReference referenceClassCloudEndPoint;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ListItem> listItemList = new ArrayList();
    private ArrayList<String> stringList = new ArrayList();
    private Context context = null;
    private int appWidgetId;
    Callback callback;
    ArrayList<DiscussionboardValues> discussionList;

    public ListProvider(Context context, Intent intent,ArrayList<DiscussionboardValues> dv) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        //populateListItem();
    }

    private void populateListItem() {
        /*for (int i = 0; i < 10; i++) {
            ListItem listItem = new ListItem();
            stringList.add("Check");
            listItem.heading = "Heading" + i;
            listItem.content = i
                    + " This is the content of the app widget listview.Nice content though";
            listItemList.add(listItem);
        }*/

      mDatabase =  FirebaseDatabase.getInstance().getReference();
        SharedPreferences pref = context.getSharedPreferences("MyPrefs", context.MODE_PRIVATE);
        String email=pref.getString("Email", null);
        String className=pref.getString("class", null);
        mDatabase =  FirebaseDatabase.getInstance().getReference();

        mDatabase =  FirebaseDatabase.getInstance().getReference();
        referenceClassCloudEndPoint = mDatabase.child("Teachers");
        teacherCloudEndPoint = referenceClassCloudEndPoint.child(email);
        teacherClassCloudEndPoint = teacherCloudEndPoint.child(className);
        DiscussionboardCloudEndPoint = teacherClassCloudEndPoint.child("DiscussionBoard");
        discussionList=new ArrayList<>();


        DiscussionboardCloudEndPoint.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){

                    noteSnapshot.getKey();
                    DiscussionboardValues note = noteSnapshot.getValue(DiscussionboardValues.class);
                    discussionList.add(note);

                }
                callback.act(discussionList);
            } @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, R.string.database_issue,
                        Toast.LENGTH_LONG).show();
            }


        });

        callback=new Callback() {
            @Override
            public void act(ArrayList<DiscussionboardValues> models) {
                //listItemList.clear();
                for(DiscussionboardValues dv:models)
                {

                    ListItem lst=new ListItem();
                    lst.content=dv.answer;
                    lst.heading=dv.question;
                    listItemList.add(lst);
                }
            }
        };


    }

    @Override
    public void onCreate() {
       // populateListItem();

    }

    @Override
    public void onDataSetChanged() {
       // listItemList.clear();
        populateListItem();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_row);
        ListItem listItem = listItemList.get(position);
        remoteView.setTextViewText(R.id.answer, "Question: "+listItem.content);
        remoteView.setTextViewText(R.id.question, "Answer: "+listItem.heading);

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}

interface Callback {
    void act(ArrayList<DiscussionboardValues> models);
}
