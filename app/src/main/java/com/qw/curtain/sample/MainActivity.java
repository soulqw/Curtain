package com.qw.curtain.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showViewGuide(View view) {
        startActivity(new Intent(this, SimpleGuideActivity.class));
    }

    public void showAdapterViewGuide(View view) {
        startActivity(new Intent(this, AdapterViewActivity.class));
    }

}
