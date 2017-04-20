package com.example.akshay.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyActivity extends AppCompatActivity {
    Button a,b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        a=(Button)findViewById(R.id.btnskip);
        b=(Button) findViewById(R.id.btncont);

    }

    public void skip(View v)
    { Intent i=new Intent(this,VoteScreenActivity.class);
        startActivity(i);
    }
    public void cont(View v){
        Intent i=new Intent(this,SurveyQuestionsActivity.class);
        startActivity(i);
    }
}


