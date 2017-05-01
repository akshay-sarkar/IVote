package com.example.akshay.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.akshay.myapplication.configuration.ConfigurationFile;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterationActivity extends Activity{

    private EditText FName, LName, EmailId, UTAID, Phone, PasswordReg;
    private Button btnSubmitRegister, btnClearRegister;
    private Context context;
    private final String base_url = ConfigurationFile.base_url;
    HttpURLConnection connection;
    ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        context = this;

        FName = (EditText)findViewById(R.id.editTextStudentFName);
        LName = (EditText) findViewById(R.id.editTextStudentLName);
        EmailId = (EditText) findViewById(R.id.editTextStudentEmailId);
        UTAID = (EditText) findViewById(R.id.editTextStudentUTAID);
        Phone = (EditText) findViewById(R.id.editTextStudentPhone);
        PasswordReg = (EditText) findViewById(R.id.editTextStudentPasswordReg);
        btnSubmitRegister = (Button) findViewById(R.id.btnSubmitRegister);
        btnClearRegister =  (Button) findViewById(R.id.btnClearRegister);

        btnSubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* TODO:Collect All the Data and Send to Server */
                //Toast.makeText(context, "Registration Successful", Toast.LENGTH_LONG).show();
                if( !FName.getText().toString().isEmpty() && !LName.getText().toString().isEmpty()
                        && !EmailId.getText().toString().isEmpty() && !UTAID.getText().toString().isEmpty()
                        && !UTAID.getText().toString().isEmpty() && !PasswordReg.getText().toString().isEmpty()
                        && !Phone.getText().toString().isEmpty())
                {
                    progressDialog = ProgressDialog.show(context, "iVote", "Registration in Progress..", true, false);
                    String url ="/register?fname="+ FName.getText().toString()+
                            "&lname="+  LName.getText().toString()+
                            "&emailID="+  EmailId.getText().toString()+
                            "&utaID="+  UTAID.getText().toString()+
                            "&phone="+  Phone.getText().toString()+
                            "&pwd="+  PasswordReg.getText().toString()+
                            "&fb_token="+FirebaseInstanceId.getInstance().getToken();

                    RegisterationActivity.AsyncTaskRunner runner = new RegisterationActivity.AsyncTaskRunner();
                    runner.execute(url);
                }else{
                    Toast.makeText(context, "Fill All Fields!!", Toast.LENGTH_LONG).show();
                }


            }
        });

        btnClearRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FName.setText("");
                LName.setText("");
                EmailId.setText("");
                UTAID.setText("");
                Phone.setText("");
                PasswordReg.setText("");
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
            //Toast.makeText(context, "Registration Successful. Check email for OTP.", Toast.LENGTH_SHORT).show();
            if (resp.equals("Registered")) {
                Toast.makeText(context, "Registration Successful. Check email for OTP.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, VerifyUserActivity.class);
                startActivity(intent);
            } else if (resp.equals("Not Registered")) {
                Toast.makeText(context, "You might be trying to re-register. Contact admin @ ivoteapp.edu@gmail.com", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
