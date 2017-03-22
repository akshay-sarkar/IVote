package com.example.akshay.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class VerifyUserActivity extends AppCompatActivity {

    private android.widget.EditText editTextEmailVerify;
    private android.widget.EditText editTextOTPVerify;
    private android.widget.Button buttonVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);
        this.buttonVerify = (Button) findViewById(R.id.buttonVerify);
        this.editTextOTPVerify = (EditText) findViewById(R.id.editTextOTPVerify);
        this.editTextEmailVerify = (EditText) findViewById(R.id.editTextEmailVerify);

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Collect All the Data and Send to Server*/

                /* one request complete- call finish() to move back to login screen */
            }
        });
    }
}
