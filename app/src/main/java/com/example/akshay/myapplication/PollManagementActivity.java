package com.example.akshay.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PollManagementActivity extends ListActivity {
    String[] assignmentArray = {"Poll Name 1", "Poll Name 2", "Poll Name 3", "Poll Name 4", "Poll Name 5"};
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_management);
        ctx = getApplicationContext();

        this.setListAdapter(new ArrayAdapter<String>(
                this, R.layout.activity_listview,
                R.id.textLabel, assignmentArray));

//        ArrayAdapter adapter = new ArrayAdapter<String>(this,
//                R.layout.activity_listview, assignmentArray);
//
//        ListView listView = (ListView) findViewById(R.id.mobile_list);
//        listView.setAdapter(adapter);
/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ctx, "Clicked", Toast.LENGTH_SHORT).show();
//                if (position == 0) {
//                    Intent intent = new Intent(view.getContext(), Assignment_1_Activity.class);
//                    startActivity(intent);
//                }
            }
        }); */

    }
}
