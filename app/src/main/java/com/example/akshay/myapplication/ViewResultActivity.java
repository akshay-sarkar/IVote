package com.example.akshay.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import com.example.akshay.myapplication.configuration.ConfigurationFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ViewResultActivity extends AppCompatActivity {
    private final String base_url = ConfigurationFile.base_url+"/viewResult";
    Context context;
    HttpURLConnection connection;
    String resp = "";
    TextView winner2;
    TextView winner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);
        winner2 = (TextView) findViewById(R.id.winner2);
        winner1 = (TextView) findViewById(R.id.winner1);
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
            // execution of result of Long time consuming operation
            result = result.substring(1, result.length()-1);
            String [] parts = result.split(",");
            System.out.println("lalalalalalallalaala"+parts[0]);
            winner1.setText("Winner 1 :"+parts[0]) ;
            winner2.setText("Winner 2 :"+parts[1]);
            if (resp.equalsIgnoreCase("Unsuccessfull")) {
                Toast.makeText(context, "Something went wrong.", Toast.LENGTH_LONG).show();
            }
            /* Only to be allowed at success case */
            //Intent intent = new Intent(context, PollManagementActivity.class);
            //startActivity(intent);
        }
    }
    }
