package com.example.jamie.autosearch_test1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Jamie on 9/29/2017.
 */
public class ScodeDetailsActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_details);


    }

    public void gotoScore(View _view){
        startActivity(new Intent(this,HealthScoreActivity.class));
    }
}
