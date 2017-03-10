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

import com.example.akshay.myapplication.messagingService.CloudMessagingServiceIDService;
import com.google.firebase.iid.FirebaseInstanceId;

public class PollManagementActivity extends ListActivity {
    String[] assignmentArray = {"Poll Name 1", "Poll Name 2", "Poll Name 3", "Poll Name 4", "Poll Name 5"};
    Context ctx;
    ListView list;
    // isAnyPollActive
    boolean isAnyPollActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = getApplicationContext();
        setContentView(R.layout.activity_poll_management);
        // FIREBASE TOKEN Collector
        System.out.println("TOKEN : "+ FirebaseInstanceId.getInstance().getToken());

        // initiate the listadapter
        ArrayAdapter<String> myAdapter = new ArrayAdapter <String>(this,
                R.layout.activity_listview, R.id.textLabelPollName, assignmentArray);

        setListAdapter(myAdapter);

        list = getListView();

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.floatingButtonAddPollScreen);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ctx, AddPoll.class);
                startActivity(intent);
            }
        });
/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ctx, "Clicked", Toast.LENGTH_SHORT).show();
               if (position == 0) {
                   Intent intent = new Intent(view.getContext(), Assignment_1_Activity.class);
                   startActivity(intent);
                }
            }
        });
  */

    }


    @Override
    protected void onListItemClick(ListView l, View v, int itemPosition, long id) {
        super.onListItemClick(l, v, itemPosition, id);

        ImageView imageViewNotification = (ImageView)v.findViewById(R.id.imageViewNotification);
        imageViewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = list.getPositionForView((View) v.getParent());
                Toast.makeText(ctx, "Notify for Poll - "+ position, Toast.LENGTH_SHORT).show();
            }
        });

        ImageView imageViewPublishResult = (ImageView)v.findViewById(R.id.imageViewPublish);
        imageViewPublishResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = list.getPositionForView((View) v.getParent());
                Toast.makeText(ctx, "Result published - "+ position, Toast.LENGTH_SHORT).show();
            }
        });

        ImageView imageViewDelete = (ImageView)v.findViewById(R.id.imageViewDelete);
        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = list.getPositionForView((View) v.getParent());
                Toast.makeText(ctx, "Deleted Successfully - " + position, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ctx, "Activated...", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(ctx, "Should not allow to activate..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
