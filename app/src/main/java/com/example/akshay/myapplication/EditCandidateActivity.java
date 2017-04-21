package com.example.akshay.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.akshay.myapplication.configuration.ConfigurationFile.base_url;

public class EditCandidateActivity extends AppCompatActivity {
    Spinner spinner1,spinner2;
    HttpURLConnection connection;
    String communityHour, department,Gender;
    Context context;
    public android.widget.EditText FirstName;
    public android.widget.EditText LastName;
    public android.widget.EditText Emailid;
    Button edit;
    ArrayList<String> list ;
    CheckBox chk1,chk2,chk3,chk4,chk5,chk6,chk7,chk8,chk9,chk10,chk11,chk12,chk13,chk14,chk15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_candidate);
        context = this;
        addItemsOnSpinner1();
        addItemsOnSpinner2();

        addListenerOnButton();
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
        chk11=(CheckBox)findViewById(R.id.chk11);
        chk12=(CheckBox)findViewById(R.id.chk12);
        chk13=(CheckBox)findViewById(R.id.chk13);
        chk14=(CheckBox)findViewById(R.id.chk14);
        chk15=(CheckBox)findViewById(R.id.chk15);



        edit=(Button) findViewById(R.id.add);
        this.add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!FirstName.getText().toString().isEmpty() && !LastName.getText().toString().isEmpty()
                        && ! Emailid.getText().toString().isEmpty() && communityHour.isEmpty() || department.isEmpty()){

                    //Preparing Paramaneters to pass in Async Thread
                    String url ="/createcandidate?FirstName="+ FirstName.getText().toString()
                            + "&LastName="+  LastName.getText().toString()
                            + "&Emailid="+  Emailid.getText().toString()
                            + "&Gender" + Gender
                            + "&Department" + spinner1.getSelectedItem().toString()
                            + "&Community_service_hours" + spinner2.getSelectedItem().toString()
                            ;
                    //Async Runner
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
                    list.add(chk1.getTag().toString());
                else
                    list.remove(chk1.getTag().toString());


                break;
            case R.id.chk2:
                if (checked)
                    list.add(chk2.getTag().toString());
                else
                    list.remove(chk2.getTag().toString());

                break;

            case R.id.chk3:
                if (checked)
                    list.add(chk3.getTag().toString());
                else list.remove(chk3.getTag().toString());

                break;
            case R.id.chk4:
                if (checked)
                    list.add(chk4.getTag().toString());
                else list.remove(chk4.getTag().toString());

                break;
            case R.id.chk5:
                if (checked)
                    list.add(chk5.getTag().toString());
                else list.remove(chk5.getTag().toString());

                break;
            case R.id.chk6:
                if (checked)

                    list.add(chk6.getTag().toString());
                else list.remove(chk6.getTag().toString());

                break;
            case R.id.chk7:

                if (checked)
                    list.add(chk7.getTag().toString());
                else list.remove(chk7.getTag().toString());

                break;
            case R.id.chk8:
                if (checked)
                    list.add(chk8.getTag().toString());
                else list.remove(chk8.getTag().toString());

                break;
            case R.id.chk9:
                if (checked)
                    list.add(chk9.getTag().toString());
                else list.remove(chk9.getTag().toString());

                break;
            case R.id.chk10:
                if (checked)
                    list.add(chk10.getTag().toString());
                else list.remove(chk10.getTag().toString());

            case R.id.chk11:
                if (checked)

                    list.add(chk6.getTag().toString());
                else list.remove(chk6.getTag().toString());

                break;
            case R.id.chk12:

                if (checked)
                    list.add(chk7.getTag().toString());
                else list.remove(chk7.getTag().toString());

                break;
            case R.id.chk13:
                if (checked)
                    list.add(chk8.getTag().toString());
                else list.remove(chk8.getTag().toString());

                break;
            case R.id.chk14:
                if (checked)
                    list.add(chk9.getTag().toString());
                else list.remove(chk9.getTag().toString());

                break;
            case R.id.chk15:
                if (checked)
                    list.add(chk10.getTag().toString());
                else list.remove(chk10.getTag().toString());

                break;

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


            @Override
            protected void onPostExecute(String result) {

                if (resp.equals("Not Created")) {
                    Toast.makeText(context, "Not Successfull!!", Toast.LENGTH_LONG).show();
                } else if (resp.equals("Created")) {
                    Toast.makeText(EditCandidateActivity.this, "Candidate Added", Toast.LENGTH_SHORT).show();
                }

            }
        }


    }



}

