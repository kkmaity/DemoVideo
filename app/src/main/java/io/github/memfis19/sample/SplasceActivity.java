package io.github.memfis19.sample;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplasceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splasce);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplasceActivity.this,MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
