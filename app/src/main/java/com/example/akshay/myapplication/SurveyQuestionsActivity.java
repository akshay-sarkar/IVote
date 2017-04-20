package com.example.akshay.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.akshay.myapplication.configuration.ConfigurationFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.akshay.myapplication.configuration.ConfigurationFile.base_url;

public class SurveyQuestionsActivity extends AppCompatActivity {
    Button c,d;
    Spinner sp1,sp2,sp3;
    HttpURLConnection connection;
    String studentOrganization, communityHour, department;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_questions);
        context = getApplicationContext();
        addItemsOnSpinner1();
        addItemsOnSpinner2();
        addItemsOnSpinner3();
        addListenerOnButton();

        c=(Button) findViewById(R.id.submit);
        d=(Button) findViewById(R.id.logout);
    }
    public void addItemsOnSpinner1() {

        sp3 = (Spinner) findViewById(R.id.sp3);
        List<String> list = new ArrayList<String>();
        list.add("Medical Department");
        list.add("Business Department");
        list.add("Department of Social Work");
        list.add("School of Art");
        list.add("Engineering Department");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner2() {

        sp2 = (Spinner) findViewById(R.id.sp2);
        List<String> list = new ArrayList<String>();
        list.add("0-25");
        list.add("26-50");
        list.add("51-75");
        list.add("76-100");
        list.add("101 and more");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner3() {

        sp1 = (Spinner) findViewById(R.id.sp1);
        List<String> list = new ArrayList<String>();
        list.add("Health Care Community");
        list.add("Art,History Student Union");
        list.add("International Student Organization");
        list.add("Engineering Student Association");
        list.add("Sports Club");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(dataAdapter);
    }
    // get the selected dropdown list value
    public void addListenerOnButton() {

        sp1 = (Spinner) findViewById(R.id.sp1);
        sp2 = (Spinner) findViewById(R.id.sp2);
        sp3 = (Spinner) findViewById(R.id.sp3);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                studentOrganization = sp1.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                communityHour = sp2.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                department = sp3.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void Submit(View v) {

        if(studentOrganization.isEmpty() || communityHour.isEmpty() || department.isEmpty()){
            Toast.makeText(context, "Fill in all the details!!", Toast.LENGTH_SHORT).show();
        }else{
            //Preparing Paramaneters to pass in Async Thread
            String url ="/surveyData?utaID="+1000;
            //Async Runner
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute(url);
        }
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
                        System.out.println("hey there!");
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

    }
    public void Logout(View view){

        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}
