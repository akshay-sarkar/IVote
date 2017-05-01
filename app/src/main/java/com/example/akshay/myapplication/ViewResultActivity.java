package com.example.akshay.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import com.example.akshay.myapplication.configuration.ConfigurationFile;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ViewResultActivity extends AppCompatActivity {
    private final String base_url = ConfigurationFile.base_url+"/viewResult?pollId="+ConfigurationFile.pollId;
    Context context;
    HttpURLConnection connection;
    String resp = "";
    TextView winner2, winner1, viewResultPollName;
    String winner_name1, winner_name2;
    int winner_vote1, winner_vote2;
    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);
        winner2 = (TextView) findViewById(R.id.winner2);
        winner1 = (TextView) findViewById(R.id.winner1);
        viewResultPollName = (TextView) findViewById(R.id.viewResultPollName);
        viewResultPollName.setText("Winner's for Poll - "+ConfigurationFile.pollName);

        graph = (GraphView) findViewById(R.id.graph);

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(base_url);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            try {
                StringBuffer responseString = null;
                String inputLine;
                URL dataUrl = new URL(base_url);
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
                        System.out.println(responseString);
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
            System.out.print("Result :"+result);

//            "candidateFname")+columentSeperator+rs_winner.getString("candidateLname")+columentSeperator+
//                    candidate_id+columentSeperator+max_vote
            if(!result.isEmpty()){
                String rows[] = result.split(ConfigurationFile.lineSeperator);
                for(int i=0; i<2; i++){
                    String col[] = rows[i].split(ConfigurationFile.columentSeperator);
                    if(i==0) {
                        winner_name1 = col[0] + " " + col[1];
                        winner_vote1 = Integer.parseInt(col[3]);
                        winner1.setText("Winner 1 : " +winner_name1 +" with "+winner_vote1+" votes");
                    }else{
                        winner_name2 = col[0] + " " + col[1];
                        winner_vote2 = Integer.parseInt(col[3]);
                        winner2.setText("Winner 2 : "+winner_name2 +" with "+winner_vote2+" votes");
                    }
                }

                BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                        new DataPoint(0, winner_vote1),
                        new DataPoint(1, winner_vote2),
                        new DataPoint(2, 0)
                });
                graph.addSeries(series);
                // styling
                series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
                    }
                });

                series.setSpacing(50);
                // draw values on top
                series.setDrawValuesOnTop(true);
                series.setValuesOnTopColor(Color.RED);


            }else{
                Toast.makeText(context, "Something went wrong.", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void Logout(View view){

        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }
    }
