package com.example.jamie.autosearch_test1;
/**
 * Created by Jamie on 9/8/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jamie.autosearch_test1.settings.Settings;

//import android.support.v7.app.AppCompatActivity;

public class Login extends Activity {

    EditText ET_NAME, ET_PASS;
    protected static String login_name, login_pass;
    Settings settings = new Settings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ET_NAME = (EditText)findViewById(R.id.user_name);
        ET_PASS = (EditText)findViewById(R.id.user_pass);

    }
    public void register(View view)
    {
        startActivity(new Intent(this,RegistrationActivity.class));
    }

    /**
     * validates user by passing parameters to BackgroundTask
     * @param view
     */
    public void userLogin(View view)
    {
        login_name = ET_NAME.getText().toString();
        login_pass = ET_PASS.getText().toString();
        String method = "login";

        if(login_name.equals("") || login_pass.equals("")){
            Toast.makeText(Login.this,"username and password must be filled",Toast.LENGTH_SHORT).show();
        }
        if(login_name.length()<=1 || login_pass.length()<=1 ){
            Toast.makeText(Login.this,"username and password characters must be greter than 1.",Toast.LENGTH_SHORT).show();
        }
        else {
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(method, login_name, login_pass);
        }
    }
}