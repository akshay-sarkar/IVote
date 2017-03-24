package com.example.akshay.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.akshay.myapplication.configuration.ConfigurationFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VerifyUserActivity extends AppCompatActivity {

    private android.widget.EditText editTextEmailVerify;
    private android.widget.EditText editTextOTPVerify;
    private android.widget.Button buttonVerify;
    private Context context;
    private final String base_url = ConfigurationFile.base_url;
    ProgressDialog progressDialog;
    HttpURLConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);
        context = this;
        buttonVerify = (Button) findViewById(R.id.buttonVerify);
        editTextOTPVerify = (EditText) findViewById(R.id.editTextOTPVerify);
        editTextEmailVerify = (EditText) findViewById(R.id.editTextEmailVerify);

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Collect All the Data and Send to Server */
                if(!editTextOTPVerify.getText().toString().isEmpty() && !editTextEmailVerify.getText().toString().isEmpty()){
                    //Setting Progress Dialog
                    progressDialog = ProgressDialog.show(context, "iVote", "Checking Credentials", true, false);

                    String url ="/registerVerification?emailID="+ editTextEmailVerify.getText().toString()
                            +"&otp="+  editTextOTPVerify.getText().toString();
                    //Async Runner
                    VerifyUserActivity.AsyncTaskRunner runner = new VerifyUserActivity.AsyncTaskRunner();
                    runner.execute(url);

                }else{
                    Toast.makeText(context, "Please fill all the details!!", Toast.LENGTH_LONG).show();
                }

                /* one request complete- call finish() to move back to login screen */
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
            if (resp.equals("Successfull")) {
                Toast.makeText(context, "Verified !!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else if (resp.equals("Unsuccessfull")) {
                Toast.makeText(context, "Enter Correct Verification Details!!", Toast.LENGTH_SHORT).show();
                //finish();
            }
        }
    }
}
