package com.example.akshay.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akshay.myapplication.configuration.ConfigurationFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SurveyActivity extends AppCompatActivity {
    Button btn1,btn2;
    ProgressDialog progressDialog;
    Context context;
    HttpURLConnection connection;
    private final String base_url = ConfigurationFile.base_url;
    TextView textViewAlreadyVoted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        textViewAlreadyVoted=(TextView)findViewById(R.id.textViewAlreadyVoted);
        textViewAlreadyVoted.setVisibility(View.INVISIBLE);
        btn1=(Button)findViewById(R.id.btnskip);
        btn2=(Button) findViewById(R.id.btncont);
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        context = this;
        //Setting Progress Dialog
        progressDialog = ProgressDialog.show(context, "iVote", "Checking Is Vote for Poll!!", true, false);
        String url ="/isAlreadyVoted?utaID="+ ConfigurationFile.student_UTA_ID+ "&pollId="+  ConfigurationFile.pollId;
        //Async Runner
        SurveyActivity.AsyncTaskRunner runner = new SurveyActivity.AsyncTaskRunner();
        runner.execute(url);

    }

    public void skip(View v)
    { Intent i=new Intent(this,VoteScreenActivity.class);
        startActivity(i);
    }
    public void cont(View v){
        Intent i=new Intent(this,SurveyQuestionsActivity.class);
        startActivity(i);
    }


    /* Thread for Server Interation - Pass paramenter and URL */
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            try {
                StringBuffer responseString = null;
                String inputLine;
                URL dataUrl = new URL(base_url + params[0]);
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.setConnectTimeout(ConfigurationFile.connectionTimeout); //'Connection Timeout' is only called at the beginning to test if the server is up or not.
                connection.setReadTimeout(ConfigurationFile.connectionTimeout); //'Read Timeout' is to test a bad network all along the transfer.
                // optional default is GET
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    responseString = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        responseString.append(inputLine);
                    }
                    in.close();
                }
                resp = responseString.toString();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            } finally {
                try {
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace(); //If you want further info on failure...
                }
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();

            if (resp.equalsIgnoreCase("Vote Already Casted")) {
                //Toast.makeText(context, "Vote Already Casted", Toast.LENGTH_LONG).show();
                textViewAlreadyVoted.setVisibility(View.VISIBLE);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, VoteAcknowledgementActivity.class);
                        startActivity(intent);
                    }
                }, 5000);

            } else if(resp.equalsIgnoreCase("Vote Not Casted")){
                btn1.setEnabled(true);
                btn2.setEnabled(true);
            }
        }
    }
}


