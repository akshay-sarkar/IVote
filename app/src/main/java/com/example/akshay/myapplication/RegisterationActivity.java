package com.example.akshay.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Akshay on 2/8/2017.
 */

public class RegisterationActivity extends Activity{

    private EditText FName, LName, EmailId, UTAID, Phone, PasswordReg;
    private Button btnSubmitRegister, btnClearRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        FName = (EditText)findViewById(R.id.editTextStudentFName);
        LName = (EditText) findViewById(R.id.editTextStudentLName);
        EmailId = (EditText) findViewById(R.id.editTextStudentEmailId);
        UTAID = (EditText) findViewById(R.id.editTextStudentUTAID);
        Phone = (EditText) findViewById(R.id.editTextStudentPhone);
        PasswordReg = (EditText) findViewById(R.id.editTextStudentPasswordReg);
        btnSubmitRegister = (Button) findViewById(R.id.btnSubmitRegister);

        btnSubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Collect All the Date and Send to Server*/
            }
        });

        btnClearRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Clear all the fields*/
            }
        });
    }

}
