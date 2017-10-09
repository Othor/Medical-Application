package com.example.jamie.autosearch_test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Jamie on 9/29/2017.
 */
public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
    }

    public void gotoScore(View _view){
        startActivity(new Intent(this,HealthScoreActivity.class));
    }
}
