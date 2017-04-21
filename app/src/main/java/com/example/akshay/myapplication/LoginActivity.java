package com.example.akshay.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.akshay.myapplication.configuration.ConfigurationFile;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private final String base_url = ConfigurationFile.base_url;
    EditText usernameText, passwordText;
    ProgressDialog progressDialog;
    Context context;
    HttpURLConnection connection;
    int backButtonCount =0;

    /**
     * Back button listener.
     * Will close the application if the back button pressed twice.
     */
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        context = this;
        // FIREBASE TOKEN Collector
        System.out.println("TOKEN : "+ FirebaseInstanceId.getInstance().getToken());
        // TODO: Send Token to the Server
    }

    /* Onclick Login */
    public void loginTriggered(View view) {
        //Setting Progress Dialog
        progressDialog = ProgressDialog.show(context, "iVote", "Checking Credentails", true, false);
        //Preparing Paramaneters to pass in Async Thread
        String url ="/login?emailId="+ usernameText.getText().toString() + "&pwd="+  passwordText.getText().toString();
        //Async Runner
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(url);
    }

    public void RegisterTriggered(View view){
        Intent intent = new Intent(context, RegisterationActivity.class);
        startActivity(intent);
    }

    public void  forgotPasswordTriggered(View view){
        Intent intent = new Intent(context, ForgotAcitvity.class);
        startActivity(intent);
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

            if (resp.equalsIgnoreCase("No Login")) {
                Toast.makeText(context, "Login Unsuccessfull!!", Toast.LENGTH_LONG).show();
            } else if(resp.equalsIgnoreCase("Admin")){
                Intent intent = new Intent(context, PollManagementActivity.class);
                startActivity(intent);
            } else if (resp.contains("ActivePoll")) {
                String[] str =  resp.split(ConfigurationFile.columentSeperator);
                ConfigurationFile.pollId = Integer.parseInt(str[1]);
                ConfigurationFile.pollName = str[2];
                Intent intent = new Intent(context, SurveyActivity.class);
                startActivity(intent);
                Toast.makeText(context, "Login Successfull", Toast.LENGTH_LONG).show();
            } else if (resp.contains("ResultPoll")) {
                // Move to other page
                String[] str =  resp.split(ConfigurationFile.columentSeperator);
                ConfigurationFile.pollId = Integer.parseInt(str[1]);
                ConfigurationFile.pollName = str[2];
                Intent intent = new Intent(context, ViewResultActivity.class);
                startActivity(intent);
                Toast.makeText(context, "Login Successfull", Toast.LENGTH_LONG).show();
            }
            /* Only to be allowed at success case */
            //Intent intent = new Intent(context, PollManagementActivity.class);
            //startActivity(intent);
        }
    }
}
