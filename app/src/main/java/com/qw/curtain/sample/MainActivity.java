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

    public void showRecyclerViewGuide(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    /**
     * 在复杂多个引导的情况下推荐使用可有效减少方法嵌套
     *
     */
    public void curtainFlow(View view) {
        startActivity(new Intent(this, CurtainFlowGuideActivity.class));
    }

}
