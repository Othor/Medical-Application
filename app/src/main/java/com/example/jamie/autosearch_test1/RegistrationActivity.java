package com.example.jamie.autosearch_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {


    public EditText etFirstName, etLastName, etPassword, etPass2, etPhone, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPass2 = (EditText) findViewById(R.id.etPassword2);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etFirstName.equals("");
        etLastName.equals("");
        etPassword.equals("");
        etPass2.equals("");
        etPhone.equals("");
        etEmail.equals("");
    }
    /**
     * validates and passes parameters to be inserted
     * @param v
     */
    public void signup(View v) {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String passWord = etPassword.getText().toString();
        String pass2 = etPassword.getText().toString();
        String phoneNumber = etPhone.getText().toString();
        String emailAddress = etEmail.getText().toString();

        if(!(etPassword.getText().toString().equals(etPass2.getText().toString()))){
            Toast.makeText(this,"passwords not identical.", Toast.LENGTH_SHORT).show();
            etPassword.setText("");
            etPass2.setText("");
        }
        if( etFirstName.equals("") ||etLastName.equals("") ||  etPassword.equals("") || etEmail.equals("") || etPhone.equals("") ){
            Toast.makeText(this, "credentials can not be empty!", Toast.LENGTH_SHORT).show();
        }
        if( etFirstName.length()<=1 || etLastName.length()<=1 || etPassword.length()<=1 || etEmail.length()<=1 || etPhone.length()<=1 ){
            Toast.makeText(this,"please complete the form.", Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();
            new SignupActivity(this).execute(firstName,lastName, passWord, phoneNumber, emailAddress);
            startActivity(new Intent(this,Login.class));
        }
    }
}
