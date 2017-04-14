package com.example.akshay.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.akshay.myapplication.adapter.VoteScreenAdapter;
import com.example.akshay.myapplication.dao.CandidateEntity;

import java.util.ArrayList;

public class VoteScreenActivity extends ListActivity {

    Context ctx;
    ImageView imgViewDownArrow;
    VoteScreenAdapter voteScreenAdapter;
    ArrayList<CandidateEntity> candidateEntities;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        candidateEntities = new ArrayList<>();
        candidateEntities.add(new CandidateEntity(1, "akshay","sarkar", "aa@a.c","12.12.12", "Male", "CSE", "captain",
                "tech", "NA","4"));
        candidateEntities.add(new CandidateEntity(2, "shayam","gopal", "xyz@a.c","12.12.12", "Male", "CSE", "captain",
                "tech", "NA","4"));
        candidateEntities.add(new CandidateEntity(3, "dsdsak","sa", "aqwq@a.c","121212", "Male", "CSE", "captain",
                "tech", "NA","4"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_screen);
        ctx = getApplicationContext();
        voteScreenAdapter = new VoteScreenAdapter(ctx, R.layout.list_vote_candidates, candidateEntities);
        setListAdapter(voteScreenAdapter);
        list = getListView();
    }
}
