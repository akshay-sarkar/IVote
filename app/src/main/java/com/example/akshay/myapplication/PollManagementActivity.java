package com.example.akshay.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.akshay.myapplication.adapter.PollAdapter;
import com.example.akshay.myapplication.dao.PollEntity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class PollManagementActivity extends ListActivity {
    //String[] assignmentArray = {"Poll Name 1", "Poll Name 2", "Poll Name 3", "Poll Name 4", "Poll Name 5"};
    ArrayList<PollEntity> pollObjects;
    Context ctx;
    ListView list;
    PollAdapter pollAdapter;
    // isAnyPollActive
    boolean isAnyPollActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        setContentView(R.layout.activity_poll_management);

        pollObjects = new ArrayList<>();
        pollObjects.add(new PollEntity("Poll Name 1", "Start Date: 02/04/2017", "End Date: 02/06/2017" ));
        pollObjects.add(new PollEntity("Poll Name 2", "Start Date: "+"02/07/2017", "End Date: "+"02/09/2017" ));
        pollObjects.add(new PollEntity("Poll Name 3", "Start Date: "+"02/11/2017", "End Date: "+"02/13/2017" ));
        pollObjects.add(new PollEntity("Poll Name 4", "Start Date: "+"02/17/2017", "End Date: "+"02/19/2017" ));
        pollObjects.add(new PollEntity("Poll Name 5", "Start Date: "+"02/21/2017", "End Date: "+"02/23/2017" ));

        // FIREBASE TOKEN Collector
        System.out.println("TOKEN : "+ FirebaseInstanceId.getInstance().getToken());

        pollAdapter = new PollAdapter(this, R.layout.activity_listview, pollObjects);
        pollAdapter.setNotifyOnChange(true);
        setListAdapter(pollAdapter);
        list = getListView();

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatingButtonAddPollScreen);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ctx, AddPollActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onListItemClick(ListView l, View v, int itemPosition, long id) {
        super.onListItemClick(l, v, itemPosition, id);

        ImageView imageViewNotification = (ImageView)v.findViewById(R.id.imageViewNotification);
        imageViewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = list.getPositionForView((View) v.getParent());
                Toast.makeText(ctx, "Reminder Sent", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView imageViewPublishResult = (ImageView)v.findViewById(R.id.imageViewPublish);
        imageViewPublishResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = list.getPositionForView((View) v.getParent());
                Toast.makeText(ctx, "Poll Result Published", Toast.LENGTH_SHORT).show();
            }
        });

        ImageView imageViewDelete = (ImageView)v.findViewById(R.id.imageViewDelete);
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = list.getPositionForView((View) v.getParent());
                Toast.makeText(ctx, "Poll Deleted", Toast.LENGTH_SHORT).show();
            }
        });


        TextView textViewPollName = (TextView) v.findViewById(R.id.textLabelPollName);
        textViewPollName.setClickable(true);
        textViewPollName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = list.getPositionForView((View) v.getParent());
                Toast.makeText(ctx, "Poll Named Clicked - "+ position, Toast.LENGTH_SHORT).show();
            }
        });

        ToggleButton toggleButtonActivatePoll = (ToggleButton) v.findViewById(R.id.toggleButtonActivatePoll);
        toggleButtonActivatePoll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                    Toast.makeText(ctx, "Poll Activated", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(ctx, "Should not allow to activate..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
