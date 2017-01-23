package com.example.akshay.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final String base_url = "http://192.168.187.1:8080/myWebService/rest/testWS";
    EditText usernameText;
    EditText passwordText;
    ProgressDialog progressDialog;
    Context context;
    HttpURLConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        context = this;
    }

    public void loginTriggered(View view) {
        //Setting Progress Dialog
        progressDialog = ProgressDialog.show(context, "UTA Ambassador", "Checking Credentails", true, false);
        //Async Runner
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute();
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            try {
                StringBuffer responseString = null;
                String inputLine;
                HttpURLConnection connection = null;

                URL dataUrl = new URL(base_url + "/login?emailId=" + usernameText.getText().toString() + "&pwd=" + passwordText.getText().toString());
                connection = (HttpURLConnection) dataUrl.openConnection();
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

            if (resp.equals("Unsuccessfull")) {
                Toast.makeText(context, "Not Successfull!!", Toast.LENGTH_LONG).show();
            } else if (resp.equals("Successfull")) {
                Toast.makeText(context, "Successfull!!", Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(context, result , Toast.LENGTH_LONG).show();
        }
    }
}
