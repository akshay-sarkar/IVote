package com.example.akshay.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotAcitvity extends AppCompatActivity {

    private Button buttonEmailForgot;
    private EditText editTextEmailForgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

         buttonEmailForgot = (Button) findViewById(R.id.buttonEmailForgot);
         editTextEmailForgot = (EditText) findViewById(R.id.editTextEmailForgot);

        buttonEmailForgot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Send Email Request */
            }
        });
    }
}
