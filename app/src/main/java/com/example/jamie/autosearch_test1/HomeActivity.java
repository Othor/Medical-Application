package com.example.jamie.autosearch_test1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Jamie on 8/31/2017.
 */
public class HomeActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    /**
     * insert history to patient
     * @param view
     */
    public void appenedHistory(View view)
    {
        startActivity(new Intent(this,MainActivity.class));
    }

    /**
     * call ambulance
     * @param view
     */
    public void ambulance(View view)
    {
        Toast.makeText(HomeActivity.this, "Ambulance Request Sent", Toast.LENGTH_LONG).show();
        String phoneNumber = "tel:"+"994";
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse(phoneNumber));
        startActivity(callIntent);
    }

    /**
     * redirects to nutrition reference site. -for now
     * @param view
     */
    public void neutrition(View view)
    {
        String webPage = "http://www.info.com";
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(webPage));
        startActivity(webIntent);
    }


    public void healthScore(View view){
        startActivity(new Intent(this,HealthScoreActivity.class));
    }






}
