package com.example.akshay.myapplication;

import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.akshay.myapplication.configuration.ConfigurationFile;
import com.example.akshay.myapplication.dao.CandidateEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.akshay.myapplication.configuration.ConfigurationFile.base_url;

public class EditCandidateActivity extends AppCompatActivity {
    Spinner spinner1,spinner2, spinner3;
    HttpURLConnection connection;
    String communityHour, department, Gender ="Male", studentOrganization;
    Context context;
    ProgressDialog progressDialog;
    public android.widget.EditText FirstName;
    public android.widget.EditText LastName;
    public android.widget.EditText Emailid;
    Button edit;
    private final String base_url = ConfigurationFile.base_url;
    ArrayList<String> qualitiesList ;
    ArrayList<String> interestList ;
    ArrayList<String> list ;
    int candidateID;
    CheckBox chk1,chk2,chk3,chk4,chk5,chk6,chk7,chk8,chk9,chk10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_candidate);
        FirstName = (EditText) findViewById(R.id.FirstName);
        LastName = (EditText) findViewById(R.id.LastName);
        Emailid = (EditText) findViewById(R.id.Emailid);
        context = this;
        //CandidateEntity candidateEntity =  (CandidateEntity) getIntent().getSerializableExtra("DATA");
        candidateID = getIntent().getExtras().getInt("candidateID");
        addItemsOnSpinner1();
        addItemsOnSpinner2();
        addItemsOnSpinner3();

        addListenerOnButton();

        qualitiesList = new ArrayList<String>();
        interestList = new ArrayList<String>();
        list = new ArrayList<String>();


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



        edit =(Button) findViewById(R.id.edit);
        this.edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!FirstName.getText().toString().isEmpty() && !LastName.getText().toString().isEmpty()
                        && ! Emailid.getText().toString().isEmpty()){
                    //Setting Progress Dialog
                    progressDialog = ProgressDialog.show(context, "iVote", "Edit Candidate", true, false);
                    //Preparing Paramaneters to pass in Async Thread
                    String url ="/editCandidate?candidateFname="+ FirstName.getText().toString()
                            + "&candidateLname="+  LastName.getText().toString()
                            + "&candidateEmailId="+  Emailid.getText().toString()
                            + "&candidateGender=" + Gender
                            + "&candidateCourse=" + spinner1.getSelectedItem().toString() // Deparrtement <-> Course
                            + "&candidateCommunityServiceHours=" + spinner2.getSelectedItem().toString()
                            + "&candidatesStudentOrganization="+studentOrganization
                            + "&candidateInterests="+interestList.toString()
                            + "&candidateQualities="+qualitiesList.toString()
                            + "&votePostionID="+ConfigurationFile.pollId
                            + "&candidateDOB=01/11/1992"
                            + "&candidateID="+candidateID;

                    //Async Runner

                    EditCandidateActivity.AsyncTaskRunner runner = new EditCandidateActivity.AsyncTaskRunner();
                    runner.execute(url);
                }else{
                    Toast.makeText(context, "Please fill all the details!!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Male:
                if (checked)
                    Gender="Male";
                break;
            case R.id.Female:
                if (checked)
                    Gender="Female";
                break;
        }
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
                else qualitiesList.remove(chk3.getText().toString());

                break;
            case R.id.chk4:
                if (checked)
                    qualitiesList.add(chk4.getText().toString());
                else qualitiesList.remove(chk4.getText().toString());

                break;
            case R.id.chk5:
                if (checked)
                    qualitiesList.add(chk5.getText().toString());
                else qualitiesList.remove(chk5.getText().toString());

                break;
            case R.id.chk6:
                if (checked)

                    interestList.add(chk6.getText().toString());
                else interestList.remove(chk6.getText().toString());

                break;
            case R.id.chk7:

                if (checked)
                    interestList.add(chk7.getText().toString());
                else interestList.remove(chk7.getText().toString());

                break;
            case R.id.chk8:
                if (checked)
                    interestList.add(chk8.getText().toString());
                else interestList.remove(chk8.getText().toString());

                break;
            case R.id.chk9:
                if (checked)
                    interestList.add(chk9.getText().toString());
                else interestList.remove(chk9.getText().toString());

                break;
            case R.id.chk10:
                if (checked)
                    interestList.add(chk10.getText().toString());
                else interestList.remove(chk10.getText().toString());
        }
    }
    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Medical Department");
        list.add("Business Department");
        list.add("Department of Social Work");
        list.add("School of Art");
        list.add("Engineering Department");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("0-25");
        list.add("26-50");
        list.add("51-75");
        list.add("76-100");
        list.add("101 and more");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner3() {

        spinner3 = (Spinner) findViewById(R.id.sp1);
        List<String> list = new ArrayList<String>();
        list.add("Sports Club");
        list.add("Health Care Community");
        list.add("Art,History Student Union");
        list.add("International Student Organization");
        list.add("Engineering Student Association");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter);
    }
    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                communityHour = spinner2.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                department = spinner1.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                studentOrganization = spinner3.getSelectedItem().toString();
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
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
            if (resp.equals("Not Updated")) {
                progressDialog.dismiss();
                Toast.makeText(context, "Not Successfull!!", Toast.LENGTH_LONG).show();
            } else if (resp.equals("Candidate Updated")) {
                progressDialog.dismiss();
                Toast.makeText(EditCandidateActivity.this, "Candidate Details Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void Logout(View view){

        Intent i=new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}




