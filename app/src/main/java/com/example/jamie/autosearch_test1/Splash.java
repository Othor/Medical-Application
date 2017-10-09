package com.example.jamie.autosearch_test1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv = (ImageView) findViewById(R.id.iv);

        Animation transition = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        iv.startAnimation(transition);

        final Intent intent = new Intent(this, Login.class);
        Thread timer = new Thread(){

            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent);
                    finish();
                }

                super.run();

            }
        };
        timer.start();
    }
}
