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
import android.widget.CheckBox;
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
    ArrayList<String> qualitiesList ;
    ArrayList<String> interestList ;
    CheckBox chk1,chk2,chk3,chk4,chk5,chk6,chk7,chk8,chk9,chk10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_questions);
        context = getApplicationContext();
        addItemsOnSpinner1();
        addItemsOnSpinner2();
        addItemsOnSpinner3();
        addListenerOnButton();
        qualitiesList = new ArrayList<String>();
        interestList = new ArrayList<String>();

        chk1=(CheckBox)findViewById(R.id.chk1);
        chk2=(CheckBox)findViewById(R.id.chk2);
        chk3=(CheckBox)findViewById(R.id.chk3);
        chk4=(CheckBox)findViewById(R.id.chk4);
        chk5=(CheckBox)findViewById(R.id.chk5);
        chk6=(CheckBox)findViewById(R.id.chk6);
        chk7=(CheckBox)findViewById(R.id.chk7);
        chk8=(CheckBox)findViewById(R.id.chk8);
        chk9=(CheckBox)findViewById(R.id.chk9);
        chk10=(CheckBox)findViewById(R.id.chk10);


        c=(Button) findViewById(R.id.submit);
        d=(Button) findViewById(R.id.logout);
    }
    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.chk1:
                if (checked)
                    qualitiesList.add(chk1.getText().toString());
                else
                    qualitiesList.remove(chk1.getText().toString());


                break;
            case R.id.chk2:
                if (checked)
                    qualitiesList.add(chk2.getText().toString());
                else
                    qualitiesList.remove(chk2.getText().toString());

                break;

            case R.id.chk3:
                if (checked)
                    qualitiesList.add(chk3.getText().toString());
                else
                    qualitiesList.remove(chk3.getText().toString());

                break;
            case R.id.chk4:
                if (checked)
                    qualitiesList.add(chk4.getText().toString());
                else
                    qualitiesList.remove(chk4.getText().toString());

                break;
            case R.id.chk5:
                if (checked)
                    qualitiesList.add(chk5.getText().toString());
                else
                    qualitiesList.remove(chk5.getText().toString());

                break;
            case R.id.chk6:
                if (checked)
                    interestList.add(chk6.getText().toString());
                else
                    interestList.remove(chk6.getText().toString());

                break;
            case R.id.chk7:

                if (checked)
                    interestList.add(chk7.getText().toString());
                else
                    interestList.remove(chk7.getText().toString());

                break;
            case R.id.chk8:
                if (checked)
                    interestList.add(chk8.getText().toString());
                else
                    interestList.remove(chk8.getText().toString());

                break;
            case R.id.chk9:
                if (checked)
                    interestList.add(chk9.getText().toString());
                else
                    interestList.remove(chk9.getText().toString());

                break;
            case R.id.chk10:
                if (checked)
                    interestList.add(chk10.getText().toString());
                else
                    interestList.remove(chk10.getText().toString());

                break;

        }
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

        if(studentOrganization.isEmpty() || communityHour.isEmpty() || department.isEmpty()
                && qualitiesList.size() > 0 && interestList.size() >0){
            Toast.makeText(context, "Fill in all the details!!", Toast.LENGTH_SHORT).show();
        }else{

            //Preparing Parameters to pass in Async Thread
            String url ="/surveyData?studentOrganization="+studentOrganization+ "&communityHour="+communityHour
                    +"&department="+department+"&qualities="+qualitiesList.toString()+"&interest="+interestList.toString() ;
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

        @Override
        protected void onPostExecute(String result) {

            if(resp.equalsIgnoreCase("")){
                Intent intent = new Intent(context, VoteScreenActivity.class);
                startActivity(intent);
            }
            /* Only to be allowed at success case */
            //Intent intent = new Intent(context, PollManagementActivity.class);
            //startActivity(intent);
        }
    }

    public void Logout(View view){

        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}
